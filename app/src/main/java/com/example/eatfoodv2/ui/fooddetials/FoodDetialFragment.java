package com.example.eatfoodv2.ui.fooddetials;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.CommentModel;
import com.example.eatfoodv2.Model.FoodModel;
import com.example.eatfoodv2.Model.SizeModel;
import com.example.eatfoodv2.R;
import com.example.eatfoodv2.ui.comments.CommentFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class FoodDetialFragment extends Fragment {

    private FoodDetialViewModel foodDetialViewModel;

    private Unbinder unbinder;

    private android.app.AlertDialog waitingDialog;


    @BindView(R.id.img_food)
    ImageView img_food;

    @BindView(R.id.btnCart)
    CounterFab btnCart;

    @BindView(R.id.btn_rating)
    FloatingActionButton btn_rating;

    @BindView(R.id.food_name)
    TextView food_name;

    @BindView(R.id.food_description)
    TextView food_description;

    @BindView(R.id.food_price)
    TextView food_price;

    @BindView(R.id.number_button)
    ElegantNumberButton number_button;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.rdi_group_size)
    RadioGroup rdi_group_size;

    @BindView(R.id.btnShowComment)
    Button btnShowComment;


    @OnClick(R.id.btn_rating)
    void onRatingButtonClick() {
        showDialogRating();
    }

    @OnClick(R.id.btnShowComment)
    void onShowCommentButoonClick() {
        CommentFragment commentFragment = CommentFragment.getInstance();
        commentFragment.show(getActivity().getSupportFragmentManager(), "CommentFragment");

    }

    private void showDialogRating() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());

        builder.setTitle("Rating Food");
        builder.setMessage("Please Fill Information ");

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.rating_layout, null);

        RatingBar ratingBar = itemView.findViewById(R.id.rating_bar);
        EditText edt_comment = itemView.findViewById(R.id.edt_comment);

        builder.setView(itemView);

        builder.setNegativeButton("CANCAL", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            CommentModel commentModel = new CommentModel();
            commentModel.setName(common.currentUser.getName());
            commentModel.setUid(common.currentUser.getUid());
            commentModel.setComment(edt_comment.getText().toString());
            commentModel.setRatingValue(ratingBar.getRating());
            Map<String, Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            commentModel.setCommentTimeStamp(serverTimeStamp);

            foodDetialViewModel.setCommentModel(commentModel);


        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodDetialViewModel =
                ViewModelProviders.of(this).get(FoodDetialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_detail, container, false);

        unbinder = ButterKnife.bind(this, root);

        initViews();

        foodDetialViewModel.getFoodModelMutableLiveData().observe(getViewLifecycleOwner(), foodModel -> {

            displayInfo(foodModel);
        });

        foodDetialViewModel.getCommentModelMutableLiveData().observe(getViewLifecycleOwner(), commentModel -> {
            submitRatingToFirebase(commentModel);
        });

        return root;
    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();


    }

    private void submitRatingToFirebase(CommentModel commentModel) {

        waitingDialog.show();
        FirebaseDatabase.getInstance()
                .getReference(common.COMMENT_REF)
                .child(common.selectedFood.getId())
                .push()
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        addRatingToFood(commentModel.getRatingValue());

                    }
                    waitingDialog.dismiss();

                });
    }

    private void addRatingToFood(float ratingValue) {
        FirebaseDatabase.getInstance()
                .getReference(common.CATAGORY_KEY)
                .child(common.catagorySelected.getMenu_id())//selectCatagory
                .child("foods") //select array of 'foods' listof this catagory
                .child(common.selectedFood.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);
                            foodModel.setKey(common.selectedFood.getKey());

                            //Apply Rating

                            if (foodModel.getRatingValue() == null)
                                foodModel.setRatingValue(0d);
                            if (foodModel.getRatingCount() == null)
                                foodModel.setRatingCount(0l);

                            double sumRating = foodModel.getRatingValue() + ratingValue;
                            long ratingCount = foodModel.getRatingCount() + 1;

                            double result = sumRating / ratingCount;

                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("ratingValue", result);
                            updateData.put("ratingCount", ratingCount);


                            foodModel.setRatingValue(result);
                            foodModel.setRatingCount(ratingCount);

                            dataSnapshot.getRef()
                                    .updateChildren(updateData)
                                    .addOnCompleteListener(task -> {
                                        waitingDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Thank You..!", Toast.LENGTH_SHORT).show();
                                            common.selectedFood = foodModel;

                                            foodDetialViewModel.setFoodModel(foodModel);
                                        }

                                    });
                        } else
                            waitingDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        waitingDialog.dismiss();
                        Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayInfo(FoodModel foodModel) {

        Glide.with(getContext()).load(foodModel.getImage()).into(img_food);

        food_name.setText(new StringBuilder(foodModel.getName()));
        food_price.setText(new StringBuilder(foodModel.getPrice().toString()));
        food_description.setText(new StringBuilder(foodModel.getDescription()));

        if (foodModel.getRatingValue() != null)
            ratingBar.setRating(foodModel.getRatingValue().floatValue());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(common.selectedFood.getName());



            for (SizeModel sizeModel : common.selectedFood.getSize()) {
                RadioButton radioButton = new RadioButton(getContext());

                radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked)

                        common.selectedFood.setUserSelectedSize(sizeModel);

                    if (foodModel.getSize() != null)
                        calculateTotalPrice();

                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f);
                radioButton.setLayoutParams(params);
                radioButton.setText(sizeModel.getName());
                radioButton.setTag(sizeModel.getPrice());

                rdi_group_size.addView(radioButton);
            }
        if (rdi_group_size.getChildCount() > 0) {
            RadioButton radioButton = (RadioButton) rdi_group_size.getChildAt(0);
            radioButton.setChecked(true);
        }
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double totalPrice = Double.parseDouble(common.selectedFood.getPrice().toString()), displayPrice = 0.0;


        totalPrice += Double.parseDouble(common.selectedFood.getPrice().toString());
        displayPrice = totalPrice * (Integer.parseInt(number_button.getNumber()));
        displayPrice = Math.round(displayPrice * 100.0 / 100.0);

        food_price.setText(new StringBuilder("").append(common.formatPrice(displayPrice).toString()));



    }
}
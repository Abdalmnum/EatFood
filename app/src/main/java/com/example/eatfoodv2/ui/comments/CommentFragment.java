package com.example.eatfoodv2.ui.comments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatfoodv2.Adapter.MyCommentAdapter;
import com.example.eatfoodv2.Callback.ICommentCallbackListener;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.CommentModel;
import com.example.eatfoodv2.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class CommentFragment extends BottomSheetDialogFragment implements ICommentCallbackListener {
    private CommentViewModel commentViewModel;
    private Unbinder unbinder;

    @BindView(R.id.recycler_comment)
    RecyclerView recycler_comment;

    AlertDialog dialog;

    ICommentCallbackListener listener;

    private static CommentFragment instance;


    public CommentFragment() {
        listener = this;
    }

    public static CommentFragment getInstance() {
        if (instance == null)
            instance = new CommentFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = LayoutInflater.from(getContext())
                .inflate(R.layout.bottom_sheet_dialog_fragment, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        initView();
        loadCommentFromFirebase();

        commentViewModel.getMutableLiveDataCommentList().observe(getViewLifecycleOwner(),commentModels -> {

            MyCommentAdapter adapter = new MyCommentAdapter(getContext(),commentModels);
            recycler_comment.setAdapter(adapter);
        });
        return itemView;
    }

    private void loadCommentFromFirebase() {
        dialog.show();
        List<CommentModel> commentModels  = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(common.COMMENT_REF)
                .child(common.selectedFood.getId())
                .orderByChild("commentTimeStamp")
                .limitToLast(100)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot commentSnapshot:dataSnapshot.getChildren())
                        {
                            CommentModel commentModel = commentSnapshot.getValue(CommentModel.class);

                            commentModels.add(commentModel);


                        }
                        listener.onCommentLoadSuccess(commentModels);
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void initView() {

        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        recycler_comment.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        recycler_comment.setLayoutManager(layoutManager);
        recycler_comment.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        
    }

    @Override
    public void onCommentLoadSuccess(List<CommentModel> commentModels) {

        dialog.dismiss();
        commentViewModel.setCommentList(commentModels);
    }

    @Override
    public void onCommentLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }
}

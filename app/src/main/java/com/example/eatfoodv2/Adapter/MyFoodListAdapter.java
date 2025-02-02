package com.example.eatfoodv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatfoodv2.Callback.IRecyclerClickListener;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.EventBus.FoodItemClick;
import com.example.eatfoodv2.Model.FoodModel;
import com.example.eatfoodv2.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFoodListAdapter extends RecyclerView.Adapter<MyFoodListAdapter.MyViewHolder> {

    private Context context;
    private List<FoodModel> foodModelList;


    public MyFoodListAdapter(Context context, List<FoodModel> foodModelList) {
        this.context = context;
        this.foodModelList = foodModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Unbinder unbinder;

        @BindView(R.id.txt_food_name)
        TextView txt_food_name;


        @BindView(R.id.txt_food_price)
        TextView txt_food_price;

        @BindView(R.id.img_food_image)
        ImageView img_food_image;

        @BindView(R.id.img_fav)
        ImageView img_fav;

        @BindView(R.id.img_quick_cart)
        ImageView img_quick_cart;
;
        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            listener.onItemClickListener(v,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyFoodListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_food_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(foodModelList.get(position).getImage())
                .into(holder.img_food_image);

        holder.txt_food_price.setText(new StringBuilder("$").append(foodModelList.get(position).getPrice()));


        holder.txt_food_name.setText(new StringBuilder("").append(foodModelList.get(position).getName()));

        holder.setListener((view, pos) -> {
            common.selectedFood = foodModelList.get(pos);

            common.selectedFood.setKey(String.valueOf(pos));
            EventBus.getDefault().postSticky(new FoodItemClick(true,foodModelList.get(pos)));

        });
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }
}

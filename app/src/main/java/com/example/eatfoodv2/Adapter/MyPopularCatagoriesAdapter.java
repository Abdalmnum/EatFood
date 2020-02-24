package com.example.eatfoodv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatfoodv2.Model.PopularCatagoryModel;
import com.example.eatfoodv2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularCatagoriesAdapter extends RecyclerView.Adapter<MyPopularCatagoriesAdapter.MyViewHolder> {

    Context context;
    List<PopularCatagoryModel> popularCatagoryModelList;

    public MyPopularCatagoriesAdapter(Context context, List<PopularCatagoryModel> popularCatagoryModelList) {
        this.context = context;
        this.popularCatagoryModelList = popularCatagoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_popular_catagories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(popularCatagoryModelList.get(position).getImage())
                .into(holder.catagory_image);

        holder.txt_catagory_name.setText(popularCatagoryModelList.get(position).getName());
    }



    @Override
    public int getItemCount() {
        return popularCatagoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_catagory_name)
        TextView txt_catagory_name;

        @BindView(R.id.catagory_image)
        CircleImageView catagory_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

        }
    }

}

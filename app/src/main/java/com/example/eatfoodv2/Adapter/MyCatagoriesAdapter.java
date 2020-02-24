package com.example.eatfoodv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.CatagoryModel;
import com.example.eatfoodv2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyCatagoriesAdapter extends RecyclerView.Adapter<MyCatagoriesAdapter.MyViewHolder> {

    Context context;
    List<CatagoryModel> catagoryModels;

    public MyCatagoriesAdapter(Context context, List<CatagoryModel> catagoryModels) {
        this.context = context;
        this.catagoryModels = catagoryModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_catagory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(catagoryModels.get(position).getImage())
                .into(holder.catagory_image);

        holder.txt_catagory.setText(new StringBuilder(catagoryModels.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return catagoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_catagory)
        TextView txt_catagory;

        @BindView(R.id.img_catagory)
        CircleImageView catagory_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(catagoryModels.size() == 1)
            return common.DEFAULT_COULMN_COUNT;
        else
        {
            if(catagoryModels.size() % 2 == 0)
                return common.DEFAULT_COULMN_COUNT;
            else
                return (position > 1 && position == catagoryModels.size()-1) ? common.FULL_WIDTH_COULMN:common.DEFAULT_COULMN_COUNT;
        }

    }
}

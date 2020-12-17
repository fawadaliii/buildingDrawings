package com.SAR.buildingdrawing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.SAR.buildingdrawing.ThreeDModel.ModelActivity;
import com.SAR.buildingdrawing.models.ThreeDModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ThreeDAdapter extends RecyclerView.Adapter<ThreeDAdapter.MyViewHolder> {
    List<ThreeDModel> threeDModels;
    Context context;

    public ThreeDAdapter(List<ThreeDModel> threeDModels, Context context) {
        this.context = context;
        this.threeDModels = threeDModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_three_d, parent, false);

        return new ThreeDAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ThreeDModel model = threeDModels.get(position);

        holder.tv_floor.setText(model.getFloor()+"");
        holder.tv_garage.setText(model.getGarage()+"");
        holder.tv_area.setText(model.getArea()+" Marla");
        Glide.with(context)
                .load(model.getTwoDUrl()).into(holder.iv_two_d);

        holder.tv_three_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ModelActivity.class);
                intent.putExtra("uri", threeDModels.get(position).getThreeDUrl());
                intent.putExtra("immersiveMode", "true");
                context.startActivity(intent);
            }
        });

        holder.iv_two_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PreviewImageActivity.class);
                intent.putExtra("uri", threeDModels.get(position).getTwoDUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threeDModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_garage, tv_floor, tv_area,tv_three_d;
        CardView card;
        ImageView iv_two_d;

        public MyViewHolder(View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            tv_garage = itemView.findViewById(R.id.tv_garage);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_floor = itemView.findViewById(R.id.tv_floor);
            iv_two_d= itemView.findViewById(R.id.iv_two_d);
            tv_three_d= itemView.findViewById(R.id.tv_three_d);

        }
    }

}
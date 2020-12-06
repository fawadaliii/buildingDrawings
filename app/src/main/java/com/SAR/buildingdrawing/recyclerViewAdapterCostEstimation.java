package com.SAR.buildingdrawing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.SAR.buildingdrawing.models.common;
import com.SAR.buildingdrawing.models.user;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerViewAdapterCostEstimation extends RecyclerView.Adapter<recyclerViewAdapterCostEstimation.ViewHolder>  {

    private static final String TAG = "recycViewAdaptCost";

    private int storeys;
    private Context mContext;

    public recyclerViewAdapterCostEstimation(Context mContext, int storeys) {
        this.storeys = storeys;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_items_cost_estimation, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder : called.");

        if (i==1){
            viewHolder.floor_name.setText("First Floor");
        }
        else if (i==2){
            viewHolder.floor_name.setText("Second Floor");
        }
        else if (i==3){
            viewHolder.floor_name.setText("Third Floor");
        }

        viewHolder.selectBedrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prev = viewHolder.selectBedrooms.getSelectedItem().toString();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                common.noOfAllBedRooms = common.noOfAllBedRooms + Integer.parseInt(viewHolder.selectBedrooms.getSelectedItem().toString())
                        - Integer.parseInt(prev);
                prev = viewHolder.selectBedrooms.getSelectedItem().toString();
//                Toast.makeText(mContext, ""+common.noOfAllBedRooms,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.selectTVLounge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prev = viewHolder.selectTVLounge.getSelectedItem().toString();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                common.noOfAllTVLounges = common.noOfAllTVLounges + Integer.parseInt(viewHolder.selectTVLounge.getSelectedItem().toString())
                        - Integer.parseInt(prev);
                prev = viewHolder.selectTVLounge.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.selectDrawingRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prev = viewHolder.selectDrawingRooms.getSelectedItem().toString();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                common.noOfAllDrawingRooms = common.noOfAllDrawingRooms + Integer.parseInt(viewHolder.selectDrawingRooms.getSelectedItem().toString())
                        - Integer.parseInt(prev);
                prev = viewHolder.selectDrawingRooms.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.selectBathrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prev = viewHolder.selectBathrooms.getSelectedItem().toString();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                common.noOfAllBathrooms = common.noOfAllBathrooms + Integer.parseInt(viewHolder.selectBathrooms.getSelectedItem().toString())
                        - Integer.parseInt(prev);
                prev = viewHolder.selectBathrooms.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.selectKitchens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prev = viewHolder.selectKitchens.getSelectedItem().toString();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                common.noOfAllKitchens = common.noOfAllKitchens + Integer.parseInt(viewHolder.selectKitchens.getSelectedItem().toString())
                        - Integer.parseInt(prev);
                prev = viewHolder.selectKitchens.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(storeys==0){
                arr = 0;
            }
            else{
                arr=storeys;
            }
        }catch (Exception e){
        }

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView floor_name;
        AppCompatSpinner selectBedrooms;
        AppCompatSpinner selectTVLounge;
        AppCompatSpinner selectDrawingRooms;
        AppCompatSpinner selectBathrooms;
        AppCompatSpinner selectKitchens;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            floor_name = itemView.findViewById(R.id.floor_name);
            selectBedrooms = itemView.findViewById(R.id.selectBedrooms);
            selectTVLounge = itemView.findViewById(R.id.selectTVLounge);
            selectDrawingRooms = itemView.findViewById(R.id.selectDrawingRooms);
            selectBathrooms = itemView.findViewById(R.id.selectBathrooms);
            selectKitchens = itemView.findViewById(R.id.selectKitchens);
        }
    }
}

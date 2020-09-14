package com.SAR.buildingdrawing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.SAR.buildingdrawing.models.user;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerViewAdapterVendors extends RecyclerView.Adapter<recyclerViewAdapterVendors.ViewHolder>  {

    private static final String TAG = "recycViewAdaptHome";

    private List<user> list;
    private Context mContext;

    public recyclerViewAdapterVendors(Context mContext, List<user> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_items_vendors, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder : called.");
        final user USER = list.get(i);

        if(USER.getPhoto() != null){
            Glide.with(mContext)
                    .asBitmap()
                    .load(USER.getPhoto())
                    .into(viewHolder.vendor_photo);
        }

        viewHolder.vendor_name.setText(USER.getName());
        viewHolder.vendor_category.setText(USER.getVendor_category());

        viewHolder.dialer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+USER.getPhone()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{

                arr=list.size();
            }
        }catch (Exception e){
        }

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView vendor_photo;
        AppCompatTextView vendor_name;
        AppCompatTextView vendor_category;
        AppCompatImageView dialer_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vendor_photo = itemView.findViewById(R.id.vendor_photo);
            vendor_name = itemView.findViewById(R.id.vendor_name);
            vendor_category = itemView.findViewById(R.id.vendor_category);
            dialer_icon = itemView.findViewById(R.id.dialer_icon);
        }
    }
}

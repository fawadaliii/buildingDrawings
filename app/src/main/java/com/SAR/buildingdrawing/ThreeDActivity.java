package com.SAR.buildingdrawing;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SAR.buildingdrawing.models.ThreeDModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ThreeDActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<ThreeDModel> threeDModels;
    String mGarage = "Yes";
    int mFloor = 1;
    int mArea = 7;
    RecyclerView recyclerView;
    ThreeDAdapter adapter;
    LinearLayout ll_main;
    AppCompatSpinner house_garage, house_area, house_floor;
    Button btnCheck;
    SpotsDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d);
        threeDModels = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        ll_main = findViewById(R.id.ll_main);
        house_garage = findViewById(R.id.house_garage);
        house_area = findViewById(R.id.house_area);
        house_floor = findViewById(R.id.house_floor);
        btnCheck = findViewById(R.id.btn_check);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("3D Structure");


        dialog = new SpotsDialog(ThreeDActivity.this, "Loading Data...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                mGarage = house_garage.getSelectedItem().toString();
                mFloor = Integer.parseInt(house_floor.getSelectedItem().toString());
                mArea = Integer.parseInt(house_area.getSelectedItem().toString());
                get3dModelData();
            }
        });

//        get3dModelData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        adapter = new ThreeDAdapter(threeDModels, ThreeDActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ThreeDActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void get3dModelData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("3d_model");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.cancel();
                if (dataSnapshot.exists()) {
                    threeDModels.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String threeDUrl = dataSnapshot1.child("3dUrl").getValue(String.class);
                        String twoDUrl = dataSnapshot1.child("2dUrl").getValue(String.class);
                        int area = dataSnapshot1.child("area").getValue(Integer.class);
                        String garage = dataSnapshot1.child("garage").getValue(String.class);
                        int floor = dataSnapshot1.child("floor").getValue(Integer.class);
                        String id = dataSnapshot1.child("id").getValue(String.class);
                        if (mArea == area && mFloor == floor && mGarage.equals(garage)) {
                            threeDModels.add(new ThreeDModel(id, threeDUrl, twoDUrl, area, garage, floor));
                            recyclerView.setVisibility(View.VISIBLE);
                            ll_main.setVisibility(View.GONE);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (threeDModels.size() == 0) {
                        Toast.makeText(ThreeDActivity.this, "Data Not Found", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(ThreeDActivity.this, "Data Not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.cancel();
                Toast.makeText(ThreeDActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            ll_main.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
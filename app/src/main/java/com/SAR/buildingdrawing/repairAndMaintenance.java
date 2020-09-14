package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;
import com.SAR.buildingdrawing.models.user;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class repairAndMaintenance extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "repairAndMaintenance";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    List<user>  vendorsList;
    private ProgressDialog mDialog;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_and_maintenance);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.view.Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Repair and Maintenance");

        mDialog = new ProgressDialog(repairAndMaintenance.this);
        vendorsList = new ArrayList<user>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference users_table = FirebaseDatabase.getInstance().getReference("users");

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                vendorsList.clear();
                if (isOnline()){
                    mDialog.setMessage("Please wait...");
                    mDialog.show();
                    users_table.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                user USER = dataSnapshot1.getValue(user.class);
                                if (USER.getType().contentEquals("vendor")){
                                    if (USER.getVendor_category().toLowerCase().contains(query.toLowerCase()) ||
                                        USER.getName().toLowerCase().contains(query.toLowerCase())){
                                        vendorsList.add(USER);
                                    }
                                }
                            }
                            initRecyclerView();
                            users_table.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
                else{
                    Toast.makeText(repairAndMaintenance.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if(isOnline()) {
            mDialog.setMessage("Please wait...");
            mDialog.show();

            users_table.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        user USER = dataSnapshot1.getValue(user.class);
                        if (USER.getType().contentEquals("vendor")){
                            vendorsList.add(USER);
                        }
                    }
                    initRecyclerView();
                    users_table.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible){
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
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
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home_nav:
                Intent intent = new Intent(repairAndMaintenance.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.build_house_nav:
                //
//                Intent intent1 = new Intent(home.this, .class);
//                startActivity(intent1);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.estimation_nav:
                //
//                Intent intent3 = new Intent(home.this, .class);
//                startActivity(intent3);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id._3d_buildings_nav:
                //
//                Intent intent2 = new Intent(home.this, .class);
//                startActivity(intent2);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.account_nav:
                //
                return true;


        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerViewVendors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapterVendors adapter = new recyclerViewAdapterVendors(this, vendorsList);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        mDialog.dismiss();
    }
}

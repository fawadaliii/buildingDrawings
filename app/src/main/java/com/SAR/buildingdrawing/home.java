package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.SAR.buildingdrawing.estimates.floorTilesEstimate;
import com.SAR.buildingdrawing.models.common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int numStoreys = 1;

    private Spinner house_type;
    private AppCompatEditText plot_size;
    private AppCompatEditText courtyard_space;
    private Spinner marla_sqft;
    private Spinner storeys;

    private AppCompatButton calculate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.view.Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Building Drawings");

        house_type = findViewById(R.id.house_type);
        plot_size = findViewById(R.id.plot_size);
        courtyard_space = findViewById(R.id.courtyard_space);
        marla_sqft = findViewById(R.id.marla_sqft);
        storeys = findViewById(R.id.storeys);

        calculate_btn = findViewById(R.id.calculate_btn);

//        if (common.currentUser != null){
//            Toast.makeText(getApplicationContext(),""+ common.currentUser.getName()
//                    +common.currentUser.getPhone()+common.currentUser.getType() +common.currentUser.getEmail(),Toast.LENGTH_SHORT).show();
//        }

        initRecyclerView();
        common.noOfAllBedRooms = 1;
        common.noOfAllTVLounges = 1;
        common.noOfAllDrawingRooms = 1;
        common.noOfAllBathrooms = 1;
        common.noOfAllKitchens = 1;

        storeys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(storeys.getSelectedItem().toString().equals("Single")){
                    numStoreys = 1;
                    initRecyclerView();
                    common.noOfAllBedRooms = 1;
                    common.noOfAllTVLounges = 1;
                    common.noOfAllDrawingRooms = 1;
                    common.noOfAllBathrooms = 1;
                    common.noOfAllKitchens = 1;
                }
                else if(storeys.getSelectedItem().toString().equals("Double")){
                    numStoreys = 2;
                    initRecyclerView();
                    common.noOfAllBedRooms = 2;
                    common.noOfAllTVLounges = 2;
                    common.noOfAllDrawingRooms = 2;
                    common.noOfAllBathrooms = 2;
                    common.noOfAllKitchens = 2;
                }
                else if(storeys.getSelectedItem().toString().equals("Triple")){
                    numStoreys = 3;
                    initRecyclerView();
                    common.noOfAllBedRooms = 3;
                    common.noOfAllTVLounges = 3;
                    common.noOfAllDrawingRooms = 3;
                    common.noOfAllBathrooms = 3;
                    common.noOfAllKitchens = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(plot_size.getText().toString())){
                    Toast.makeText(home.this, "Plot size cannot be null",Toast.LENGTH_SHORT).show();
                    plot_size.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(courtyard_space.getText().toString())){
                    Toast.makeText(home.this, "Courtyard space cannot be null",Toast.LENGTH_SHORT).show();
                    courtyard_space.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(Integer.parseInt(plot_size.getText().toString()) > 100 || Integer.parseInt(plot_size.getText().toString()) < 3){
                    Toast.makeText(home.this, "Enter Plot size between 3 to 100 marla",Toast.LENGTH_SHORT).show();
                    plot_size.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(Integer.parseInt(courtyard_space.getText().toString()) > 99 ||
                        Integer.parseInt(courtyard_space.getText().toString()) > Integer.parseInt(plot_size.getText().toString())){
                    Toast.makeText(home.this, "Enter valid courtyard space",Toast.LENGTH_SHORT).show();
                    courtyard_space.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

//                String HT = house_type.getSelectedItem().toString();
//                String PS = plot_size.getText().toString();
//                String CS = courtyard_space.getText().toString();
//                String MS = marla_sqft.getSelectedItem().toString();
//                String S = storeys.getSelectedItem().toString();

                Intent intent = new Intent(home.this, costEstimation.class);
                intent.putExtra("house_type", house_type.getSelectedItem().toString());
                intent.putExtra("plot_size", plot_size.getText().toString());
                intent.putExtra("courtyard_space", courtyard_space.getText().toString());
                intent.putExtra("marla_sqft", marla_sqft.getSelectedItem().toString());
                intent.putExtra("storeys", storeys.getSelectedItem().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveTaskToBack(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home_nav:
                //
//                Intent intent2 = new Intent(home.this, home.class);
//                startActivity(intent2);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.build_house_nav:
                //
//                Intent intent1 = new Intent(home.this, .class);
//                startActivity(intent1);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.estimation_nav:
                //
                Intent intent3 = new Intent(home.this, estimation.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id._3d_buildings_nav:
                Intent intent2 = new Intent(home.this, ThreeDActivity.class);
                startActivity(intent2);
                return true;

            case R.id.account_nav:
                //
                if (common.currentUser == null){
                    Intent intent = new Intent(home.this, signin.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else{
                    Intent intent = new Intent(home.this, account.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                return true;


        }
        return false;
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFloors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapterCostEstimation adapter = new recyclerViewAdapterCostEstimation(this, numStoreys);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//        if(numStoreys > 1){
//            recyclerView.getLayoutManager().scrollToPosition(1);
//        }
//        mDialog.dismiss();
    }
}

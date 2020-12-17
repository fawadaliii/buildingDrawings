package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.SAR.buildingdrawing.estimates.beamEstimate;
import com.SAR.buildingdrawing.estimates.bricksEstimate;
import com.SAR.buildingdrawing.estimates.floorTilesEstimate;
import com.SAR.buildingdrawing.estimates.lanterEstimate;
import com.SAR.buildingdrawing.estimates.paintEstimate;
import com.SAR.buildingdrawing.estimates.plasterCeiling;
import com.SAR.buildingdrawing.estimates.plasterWall;
import com.SAR.buildingdrawing.estimates.wallTilesEstimate;
import com.SAR.buildingdrawing.models.common;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class estimation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    private AppCompatTextView bricksEstimate;
    private AppCompatTextView paintEstimate;
    private AppCompatTextView lanterEstimate;
    private AppCompatTextView beamEstimate;
    private AppCompatTextView floorTilesEstimate;
    private AppCompatTextView wallTilesEstimate;
    private AppCompatTextView plasterCeilingEstimate;
    private AppCompatTextView plasterWallEstimate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimation);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.view.Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Estimation");

        bricksEstimate = findViewById(R.id.bricksEstimate);
        paintEstimate = findViewById(R.id.paintEstimate);
        lanterEstimate = findViewById(R.id.lanterEstimate);
        beamEstimate = findViewById(R.id.beamEstimate);
        floorTilesEstimate = findViewById(R.id.floorTilesEstimate);
        wallTilesEstimate = findViewById(R.id.wallTilesEstimate);
        plasterCeilingEstimate = findViewById(R.id.plasterCeilingEstimate);
        plasterWallEstimate = findViewById(R.id.plasterWallEstimate);

        bricksEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, bricksEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        paintEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, paintEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        lanterEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, lanterEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        beamEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, beamEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        floorTilesEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, floorTilesEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        wallTilesEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, wallTilesEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        plasterCeilingEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, plasterCeiling.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        plasterWallEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estimation.this, plasterWall.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent2 = new Intent(estimation.this, home.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(estimation.this, home.class);
        startActivity(intent2);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home_nav:
                //
                Intent intent2 = new Intent(estimation.this, home.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

//            case R.id.build_house_nav:
//                //
////                Intent intent1 = new Intent(home.this, .class);
////                startActivity(intent1);
////                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                return true;

            case R.id.estimation_nav:
                //
//                Intent intent3 = new Intent(home.this, estimation.class);
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
                if (common.currentUser == null){
                    Intent intent = new Intent(estimation.this, signin.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else{
                    Intent intent = new Intent(estimation.this, account.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                return true;


        }
        return false;
    }
}
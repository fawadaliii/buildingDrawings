package com.SAR.buildingdrawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;

import java.text.NumberFormat;

public class costEstimation extends AppCompatActivity {
    private Toolbar toolbar;

    private String house_type;
    private int plot_size;
    private int courtyard_space;
    private int marla_sqft;
    private double storeys;
    private double totalPartition;

    private AppCompatTextView cement;
    private AppCompatTextView sand;
    private AppCompatTextView bricks;
    private AppCompatTextView aggregate;
    private AppCompatTextView steel;
    private AppCompatTextView paint;
    private AppCompatTextView total_cost;

    private int builtupArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_estimation);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Full House Estimation");

        cement = findViewById(R.id.cement);
        sand = findViewById(R.id.sand);
        bricks = findViewById(R.id.bricks);
        aggregate = findViewById(R.id.aggregate);
        steel = findViewById(R.id.steel);
        paint = findViewById(R.id.paint);
        total_cost = findViewById(R.id.total_cost);

        totalPartition = common.noOfAllBedRooms + common.noOfAllTVLounges + common.noOfAllDrawingRooms
                + common.noOfAllBathrooms + common.noOfAllKitchens;

        totalPartition = (totalPartition/100) + 1;

        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            house_type = extras.getString("house_type");
            plot_size = Integer.parseInt(extras.getString("plot_size"));
            courtyard_space = Integer.parseInt(extras.getString("courtyard_space"));
            marla_sqft = Integer.parseInt(extras.getString("marla_sqft"));

            builtupArea = (plot_size - courtyard_space)*marla_sqft;

            if (extras.getString("storeys").equals("Single"))
                storeys = 1;
            else if (extras.getString("storeys").equals("Double"))
                storeys = 1.9;
            else if (extras.getString("storeys").equals("Triple"))
                storeys = 2.8;


            if (house_type.equals("Normal")){
                cement.setText(Math.ceil(0.04*builtupArea*storeys*totalPartition) + "  Bags"); //4 bags per 100 square feet
                sand.setText(Math.ceil(0.816*builtupArea*storeys*totalPartition) + "  Tons"); //81 ton per 100 square feet
                bricks.setText(Math.ceil(9*builtupArea*storeys*totalPartition) + ""); //9 Bricks/sq. ft.
                aggregate.setText(Math.ceil(0.608*builtupArea*storeys*totalPartition) + "  Tons"); //60 ton per 100 square feet
                steel.setText(Math.ceil(2.66*builtupArea*storeys*totalPartition) + "  KG"); //266 KG per 100 square feet
                paint.setText(Math.ceil(0.18*builtupArea*storeys*totalPartition) + "  Litres"); // 18 litres per 100 sq ft


                Toast.makeText(getApplicationContext(), ""+totalPartition,Toast.LENGTH_SHORT).show();

                total_cost.setText("RS. "+ NumberFormat.getInstance().format(Math.ceil(2500*builtupArea*storeys*totalPartition)) + " /-"); // 2500/Sqft. for normal house
            }
            else if (house_type.equals("Luxury")){
                cement.setText(Math.ceil(0.04*builtupArea*storeys*totalPartition*1.1) + "  Bags"); //4 bags per 100 square feet
                sand.setText(Math.ceil(0.816*builtupArea*storeys*totalPartition*1.1) + "  Tons"); //81 ton per 100 square feet
                bricks.setText(Math.ceil(9*builtupArea*storeys*totalPartition*1.1) + ""); //9 Bricks/sq. ft.
                aggregate.setText(Math.ceil(0.608*builtupArea*storeys*totalPartition*1.1) + "  Tons"); //60 ton per 100 square feet
                steel.setText(Math.ceil(2.66*builtupArea*storeys*totalPartition*1.1) + "  KG"); //266 KG per 100 square feet
                paint.setText(Math.ceil(0.18*builtupArea*storeys*totalPartition*1.1) + "  Litres"); // 18 litres per 100 sq ft

                total_cost.setText("RS. "+ NumberFormat.getInstance().format(Math.ceil(3100*builtupArea*storeys*totalPartition)) + " /-"); // 3100/Sqft. for luxury house
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }
}
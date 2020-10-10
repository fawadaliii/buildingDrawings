package com.SAR.buildingdrawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class filter extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner selectVendorCategory;

    List<String> vendorCategorySpinnerItems;

    private AppCompatButton filter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filters");

        selectVendorCategory = findViewById(R.id.selectVendorCategory);
        filter_btn = findViewById(R.id.filter_btn);

        vendorCategorySpinnerItems = new ArrayList<String>();

        vendorCategorySpinnerItems.add("Select Vendor Category *");
        vendorCategorySpinnerItems.add(".....");
        vendorCategorySpinnerItems.add("Appliance repairs");
        vendorCategorySpinnerItems.add("Electrical repairs");
        vendorCategorySpinnerItems.add("Internet repairs");
        vendorCategorySpinnerItems.add("Locksmith");
        vendorCategorySpinnerItems.add("Painter");
        vendorCategorySpinnerItems.add("Plumbing repairs");
        vendorCategorySpinnerItems.add("Pressure washing");
        vendorCategorySpinnerItems.add("Surface refinishing");

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, vendorCategorySpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectVendorCategory.setAdapter(adapter);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    if(selectVendorCategory.getSelectedItemPosition() == 0 || selectVendorCategory.getSelectedItemPosition() == 1){
                        Toast.makeText(filter.this, "Select Vendor Category",Toast.LENGTH_SHORT).show();
                        selectVendorCategory.setFocusable(true);
                        selectVendorCategory.setFocusableInTouchMode(true);
                        selectVendorCategory.requestFocus();
                        selectVendorCategory.performClick();
                        return;
                    }

                    Intent intent = new Intent(filter.this, repairAndMaintenance.class);
                    intent.putExtra("Filter", selectVendorCategory.getSelectedItem().toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

                }else{
                    Toast.makeText(filter.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        //moveTaskToBack(true);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
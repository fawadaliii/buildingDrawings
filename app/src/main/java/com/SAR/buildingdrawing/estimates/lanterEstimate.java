package com.SAR.buildingdrawing.estimates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.SAR.buildingdrawing.R;

public class lanterEstimate extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText lanter_length;
    private AppCompatEditText lanter_width;
    private AppCompatEditText lanter_thickness;

    private AppCompatButton calculate_btn;

    private double lanterLength;
    private double lanterWidth;
    private double lanterThickness;

    private String steelRequired;
    private String cementBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanter_estimate);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lanter Estimation");

        lanter_length = findViewById(R.id.lanter_length);
        lanter_width = findViewById(R.id.lanter_width);
        lanter_thickness = findViewById(R.id.lanter_thickness);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(lanter_length.getText().toString())){
                    Toast.makeText(lanterEstimate.this, "Lanter Length cannot be null",Toast.LENGTH_SHORT).show();
                    lanter_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(lanter_width.getText().toString())){
                    Toast.makeText(lanterEstimate.this, "Lanter Width cannot be null",Toast.LENGTH_SHORT).show();
                    lanter_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(lanter_thickness.getText().toString())){
                    Toast.makeText(lanterEstimate.this, "Lanter Thickness cannot be null",Toast.LENGTH_SHORT).show();
                    lanter_thickness.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                lanterLength = Double.parseDouble(lanter_length.getText().toString());
                lanterWidth = Double.parseDouble(lanter_width.getText().toString());
                lanterThickness = Double.parseDouble(lanter_thickness.getText().toString());

                double areaOfLanter = lanterLength*lanterWidth;

                steelRequired = Math.ceil(areaOfLanter*3.5)+""; //3.5 is the value of kg per sq ft of steel
                cementBags = Math.ceil((areaOfLanter*0.5*lanterThickness/12))+""; //0.5 because 1 cement covers approximately 2 sq ft

                LayoutInflater inflater = (LayoutInflater) lanterEstimate.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView brick_required_label = formElementsView.findViewById(R.id.brick_required_label);
                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required_label = formElementsView.findViewById(R.id.cement_bags_required_label);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);


                brick_required_label.setText("Steel Required");
                bricks_required.setText(steelRequired + " KG");
                cement_bags_required.setText(cementBags);

                new AlertDialog.Builder(lanterEstimate.this).setView(formElementsView)
                        .setTitle("Material Estimate")
                        .setPositiveButton("Ok", null).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
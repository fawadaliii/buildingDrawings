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

public class beamEstimate extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText beam_length;
    private AppCompatEditText beam_width;
    private AppCompatEditText beam_thickness;

    private AppCompatButton calculate_btn;

    private double beamLength;
    private double beamWidth;
    private double beamThickness;

    private String steelRequired;
    private String cementBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam_estimate);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Beam Estimation");

        beam_length = findViewById(R.id.beam_length);
        beam_width = findViewById(R.id.beam_width);
        beam_thickness = findViewById(R.id.beam_thickness);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(beam_length.getText().toString())){
                    Toast.makeText(beamEstimate.this, "Beam Length cannot be null",Toast.LENGTH_SHORT).show();
                    beam_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(beam_width.getText().toString())){
                    Toast.makeText(beamEstimate.this, "Beam Width cannot be null",Toast.LENGTH_SHORT).show();
                    beam_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(beam_thickness.getText().toString())){
                    Toast.makeText(beamEstimate.this, "Beam Thickness cannot be null",Toast.LENGTH_SHORT).show();
                    beam_thickness.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                beamLength = Double.parseDouble(beam_length.getText().toString());
                beamWidth = Double.parseDouble(beam_width.getText().toString());
                beamThickness = Double.parseDouble(beam_thickness.getText().toString());

                double areaOfBeam = beamLength*beamWidth;

                steelRequired = Math.ceil(areaOfBeam*3.5)+""; //3.5 is the value of kg per sq ft of steel
                cementBags = Math.ceil((areaOfBeam*0.5*beamThickness/12))+"";

                LayoutInflater inflater = (LayoutInflater) beamEstimate.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView brick_required_label = formElementsView.findViewById(R.id.brick_required_label);
                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required_label = formElementsView.findViewById(R.id.cement_bags_required_label);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);


                brick_required_label.setText("Steel Required");
                bricks_required.setText(steelRequired + " KG");
                cement_bags_required.setText(cementBags);

                new AlertDialog.Builder(beamEstimate.this).setView(formElementsView)
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
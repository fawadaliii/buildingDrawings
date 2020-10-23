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

public class plasterCeiling extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText room_length;
    private AppCompatEditText room_width;
    private AppCompatEditText plaster_thickness;

    private AppCompatButton calculate_btn;

    private double roomLength;
    private double roomWidth;
    private double plasterThickness = 0.5;

    private String cementBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaster_ceiling);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Plaster Ceiling");

        room_length = findViewById(R.id.lanter_length);
        room_width = findViewById(R.id.lanter_width);
        plaster_thickness = findViewById(R.id.lanter_thickness);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(room_length.getText().toString())){
                    Toast.makeText(plasterCeiling.this, "Room Length cannot be null",Toast.LENGTH_SHORT).show();
                    room_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_width.getText().toString())){
                    Toast.makeText(plasterCeiling.this, "Room Width cannot be null",Toast.LENGTH_SHORT).show();
                    room_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                roomLength = Double.parseDouble(room_length.getText().toString());
                roomWidth = Double.parseDouble(room_width.getText().toString());

                if(!TextUtils.isEmpty(plaster_thickness.getText().toString())){
                    plasterThickness = Double.parseDouble(plaster_thickness.getText().toString());
                }

                double areaOfRoom = roomLength*roomWidth;

                cementBags = Math.ceil(areaOfRoom*(1+plasterThickness)/150)+""; //1 cement bag plasters approximately 150 sq ft

                LayoutInflater inflater = (LayoutInflater) plasterCeiling.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView brick_required_label = formElementsView.findViewById(R.id.brick_required_label);
                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required_label = formElementsView.findViewById(R.id.cement_bags_required_label);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);


                brick_required_label.setVisibility(View.GONE);
                bricks_required.setVisibility(View.GONE);
                cement_bags_required.setText(cementBags);

                new AlertDialog.Builder(plasterCeiling.this).setView(formElementsView)
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
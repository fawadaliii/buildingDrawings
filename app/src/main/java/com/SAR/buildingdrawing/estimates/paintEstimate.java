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

public class paintEstimate extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText room_length;
    private AppCompatEditText room_width;
    private AppCompatEditText room_height;
    private AppCompatEditText no_of_doors;
    private AppCompatEditText no_of_windows;

    private AppCompatButton calculate_btn;

    private double roomLength;
    private double roomWidth;
    private double roomHeight;
    private double noOfDoors = 1;
    private double noOfWindows = 1;

    private String bucketsRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_estimate);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bricks Estimation");

        room_length = findViewById(R.id.room_length);
        room_width = findViewById(R.id.room_width);
        room_height = findViewById(R.id.room_height);
        no_of_doors = findViewById(R.id.no_of_doors);
        no_of_windows = findViewById(R.id.no_of_windows);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(room_length.getText().toString())){
                    Toast.makeText(paintEstimate.this, "Room Length cannot be null",Toast.LENGTH_SHORT).show();
                    room_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_width.getText().toString())){
                    Toast.makeText(paintEstimate.this, "Room Width cannot be null",Toast.LENGTH_SHORT).show();
                    room_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_height.getText().toString())){
                    Toast.makeText(paintEstimate.this, "Room Height cannot be null",Toast.LENGTH_SHORT).show();
                    room_height.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                roomLength = Double.parseDouble(room_length.getText().toString());
                roomWidth = Double.parseDouble(room_width.getText().toString());
                roomHeight = Double.parseDouble(room_height.getText().toString());
                if(!TextUtils.isEmpty(no_of_doors.getText().toString())){
                    noOfDoors = Double.parseDouble(no_of_doors.getText().toString());
                }
                if(!TextUtils.isEmpty(no_of_windows.getText().toString())){
                    noOfWindows = Double.parseDouble(no_of_windows.getText().toString());
                }

                double areaOfRoom = (roomLength*roomHeight + roomWidth*roomHeight)*2 - (28*noOfDoors + 20*noOfWindows);
                //28 and 20 are are of 1 door and 1 window respectively

                bucketsRequired = Math.ceil(areaOfRoom/250)+"";

                LayoutInflater inflater = (LayoutInflater) paintEstimate.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView brick_required_label = formElementsView.findViewById(R.id.brick_required_label);
                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required_label = formElementsView.findViewById(R.id.cement_bags_required_label);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);

                brick_required_label.setVisibility(View.GONE);
                bricks_required.setVisibility(View.GONE);
                cement_bags_required_label.setText("Paint Buckets required");
                cement_bags_required.setText(bucketsRequired);

                new AlertDialog.Builder(paintEstimate.this).setView(formElementsView)
                        .setTitle("Material Estimate")
                        .setPositiveButton("Ok", null).show();

                noOfDoors = 1;
                noOfWindows = 1;
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
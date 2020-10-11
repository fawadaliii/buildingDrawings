package com.SAR.buildingdrawing.estimates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.SAR.buildingdrawing.R;
import com.SAR.buildingdrawing.estimation;
import com.SAR.buildingdrawing.home;

public class bricksEstimate extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText room_length;
    private AppCompatEditText room_width;
    private AppCompatEditText room_height;
    private AppCompatEditText brick_length;
    private AppCompatEditText brick_width;
    private AppCompatEditText brick_height;

    private AppCompatButton calculate_btn;

    private double roomLength;
    private double roomWidth;
    private double roomHeight;
    private double brickLength = 9;
    private double brickWidth = 4;
    private double brickHeight = 4;

    private String bricksRequired;
    private String cementBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bricks_estimate);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bricks Estimation");

        room_length = findViewById(R.id.room_length);
        room_width = findViewById(R.id.room_width);
        room_height = findViewById(R.id.room_height);
        brick_length = findViewById(R.id.brick_length);
        brick_width = findViewById(R.id.brick_width);
        brick_height = findViewById(R.id.brick_height);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(room_length.getText().toString())){
                    Toast.makeText(bricksEstimate.this, "Room Length cannot be null",Toast.LENGTH_SHORT).show();
                    room_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_width.getText().toString())){
                    Toast.makeText(bricksEstimate.this, "Room Width cannot be null",Toast.LENGTH_SHORT).show();
                    room_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_height.getText().toString())){
                    Toast.makeText(bricksEstimate.this, "Room Height cannot be null",Toast.LENGTH_SHORT).show();
                    room_height.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                roomLength = Double.parseDouble(room_length.getText().toString());
                roomWidth = Double.parseDouble(room_width.getText().toString());
                roomHeight = Double.parseDouble(room_height.getText().toString());
                if(!TextUtils.isEmpty(brick_length.getText().toString())){
                    brickLength = Double.parseDouble(brick_length.getText().toString());
                }
                if(!TextUtils.isEmpty(brick_width.getText().toString())){
                    brickWidth = Double.parseDouble(brick_width.getText().toString());
                }
                if(!TextUtils.isEmpty(brick_height.getText().toString())){
                    brickHeight = Double.parseDouble(brick_height.getText().toString());
                }

                double areaOfBrick = brickLength*brickHeight;
                double noOfBricksIn1SqFt = 144/areaOfBrick;

                double areaOfRoom = (roomLength*roomHeight + roomWidth*roomHeight)*2 - 96; //-48 for excluding door and window

                bricksRequired = Math.ceil(areaOfRoom*noOfBricksIn1SqFt)+"";
                cementBags = Math.ceil((areaOfRoom*noOfBricksIn1SqFt)/250)+"";

                LayoutInflater inflater = (LayoutInflater) bricksEstimate.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);


                bricks_required.setText(bricksRequired);
                cement_bags_required.setText(cementBags);

                new AlertDialog.Builder(bricksEstimate.this).setView(formElementsView)
                        .setTitle("Material Estimate")
                        .setPositiveButton("Ok", null).show();

                brickLength = 9;
                brickHeight = 4;
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
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

public class wallTilesEstimate extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText room_length;
    private AppCompatEditText room_width;
    private AppCompatEditText room_height;
    private AppCompatEditText tile_length;
    private AppCompatEditText tile_width;

    private AppCompatButton calculate_btn;

    private double roomLength;
    private double roomWidth;
    private double roomHeight;
    private double tileLength = 18;
    private double tileWidth = 12;

    private String tilesRequired;
    private String cementBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_tiles_estimate);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Wall Tiles Estimation");

        room_length = findViewById(R.id.room_length);
        room_width = findViewById(R.id.room_width);
        room_height = findViewById(R.id.room_height);
        tile_length = findViewById(R.id.brick_length);
        tile_width = findViewById(R.id.brick_width);

        calculate_btn = findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(room_length.getText().toString())){
                    Toast.makeText(wallTilesEstimate.this, "Room Length cannot be null",Toast.LENGTH_SHORT).show();
                    room_length.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_width.getText().toString())){
                    Toast.makeText(wallTilesEstimate.this, "Room Width cannot be null",Toast.LENGTH_SHORT).show();
                    room_width.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                if(TextUtils.isEmpty(room_height.getText().toString())){
                    Toast.makeText(wallTilesEstimate.this, "Room Height cannot be null",Toast.LENGTH_SHORT).show();
                    room_height.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return;
                }

                roomLength = Double.parseDouble(room_length.getText().toString());
                roomWidth = Double.parseDouble(room_width.getText().toString());
                roomHeight = Double.parseDouble(room_height.getText().toString());
                if(!TextUtils.isEmpty(tile_length.getText().toString())){
                    tileLength = Double.parseDouble(tile_length.getText().toString());
                }
                if(!TextUtils.isEmpty(tile_width.getText().toString())){
                    tileWidth = Double.parseDouble(tile_width.getText().toString());
                }

                double areaOfTile = tileLength*tileWidth;
                double noOfTilesIn1SqFt = 144/areaOfTile;

                double areaOfRoom = (roomLength*roomHeight + roomWidth*roomHeight)*2 - 48; //-48 for excluding door and window

                tilesRequired = Math.ceil(areaOfRoom*noOfTilesIn1SqFt)+"";
                cementBags = Math.ceil(areaOfRoom/25)+"";

                LayoutInflater inflater = (LayoutInflater) wallTilesEstimate.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.required_material_alert,null, false);

                final AppCompatTextView brick_required_label = formElementsView.findViewById(R.id.brick_required_label);
                final AppCompatTextView bricks_required = formElementsView.findViewById(R.id.bricks_required);
                final AppCompatTextView cement_bags_required_label = formElementsView.findViewById(R.id.cement_bags_required_label);
                final AppCompatTextView cement_bags_required = formElementsView.findViewById(R.id.cement_bags_required);

                brick_required_label.setText("Tiles Required");
                bricks_required.setText(tilesRequired);
                cement_bags_required.setText(cementBags);

                new AlertDialog.Builder(wallTilesEstimate.this).setView(formElementsView)
                        .setTitle("Material Estimate")
                        .setPositiveButton("Ok", null).show();

                tileLength = 18;
                tileWidth = 12;
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
package com.SAR.buildingdrawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;

public class contactUs extends AppCompatActivity {

    private Toolbar toolbar;

    ProgressDialog mDialog;

    private AppCompatEditText CU_name_field;
    private AppCompatEditText CU_email_field;
    private AppCompatEditText CU_subject_field;
    private AppCompatEditText CU_message_field;
    private AppCompatButton send_message_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");


        mDialog = new ProgressDialog(contactUs.this);

        CU_name_field = findViewById(R.id.CU_name_field);
        CU_email_field = findViewById(R.id.CU_email_field);
        CU_subject_field = findViewById(R.id.CU_subject_field);
        CU_message_field = findViewById(R.id.CU_message_field);
        send_message_btn = findViewById(R.id.send_message_btn);

        if(isOnline()) {
            CU_name_field.setText(common.currentUser.getName());
            CU_name_field.setFocusable(false);
            CU_email_field.setText(common.currentUser.getEmail());
            CU_email_field.setFocusable(false);
        }
        else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOnline()) {
                    if(TextUtils.isEmpty(CU_subject_field.getText().toString()) || CU_subject_field.getText().toString().contentEquals(" ")){
                        Toast.makeText(contactUs.this, "Subject cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(CU_message_field.getText().toString()) || CU_message_field.getText().toString().contentEquals("\n\n")){
                        Toast.makeText(contactUs.this, "Message cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender("buildingdrawingsfeedback@gmail.com", "buildingdrawings");
                                sender.sendMail(CU_subject_field.getText().toString(),
                                        "A message from " + common.currentUser.getName() +  "\n\n"+ CU_message_field.getText().toString()
                                                +  "\n\n\n Email:  "+ common.currentUser.getEmail(),
                                        common.currentUser.getEmail(),
                                        "buildingdrawingsproject@gmail.com");
                                CU_subject_field.setText(" ");
                                CU_message_field.setText("\n\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();

                    Toast.makeText(contactUs.this,"Message sent successfully. " +
                            "\nWe'll get back to you as soon as reasonably possible.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
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

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
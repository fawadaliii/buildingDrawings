package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class account extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "account";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    private AppCompatTextView viewAndEdit;
    private AppCompatTextView user_name;
    private CircleImageView user_image;

    private AppCompatTextView signoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.view.Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account & Settings");

        viewAndEdit = findViewById(R.id.viewAndEdit);
        user_name = findViewById(R.id.user_name);
        user_image = findViewById(R.id.user_image);

        user_name.setText(common.currentUser.getName());

        signoutBtn = findViewById(R.id.signoutBtn);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(isOnline()) {
            if ( common.currentUser.getPhoto() != null){
                if(!common.currentUser.getPhoto().contentEquals("")){
                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(common.currentUser.getPhoto())
                            .into(user_image);
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline()) {
                    mAuth.signOut();
                    common.currentUser = null;
                    Toast.makeText(getApplicationContext(),"Signed Out!",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(account.this, home.class);
                    startActivity(intent1);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(account.this, editProfile.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(account.this, home.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(account.this, home.class);
        startActivity(intent1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home_nav:
                Intent intent = new Intent(account.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.build_house_nav:
                //
//                Intent intent1 = new Intent(home.this, .class);
//                startActivity(intent1);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.estimation_nav:
                //
//                Intent intent3 = new Intent(home.this, .class);
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
                return true;


        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

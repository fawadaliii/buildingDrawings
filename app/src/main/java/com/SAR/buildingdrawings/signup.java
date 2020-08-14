package com.SAR.buildingdrawings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.SAR.buildingdrawings.models.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText name;
    private AppCompatEditText email;
    private AppCompatEditText phone;
    private AppCompatEditText password;
    private AppCompatEditText confirmPssword;
    private AppCompatButton signup;
    private RelativeLayout RLSignup;

    private AppCompatTextView alreadyHaveAcc;
    private AppCompatTextView sign_up_as_vendor_btn;

    private static final String TAG = "signup";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign up");

        name = findViewById(R.id.name_field);
        email = findViewById(R.id.email_field);
        phone = findViewById(R.id.phone_field);
        password = findViewById(R.id.password_field);
        confirmPssword = findViewById(R.id.confirm_password_field);
        signup = findViewById(R.id.sign_up_btn);
        RLSignup = findViewById(R.id.RL_signup);
        toolbar = findViewById(R.id.toolbar);
        alreadyHaveAcc = findViewById(R.id.already_have_acc);
        sign_up_as_vendor_btn = findViewById(R.id.sign_up_as_vendor_btn);

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(signup.this, "First Name cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(signup.this, "Email cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(signup.this, "Phone Number cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(signup.this, "Password cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 5 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.getText().toString().contentEquals(confirmPssword.getText().toString())){
                    Toast.makeText(signup.this, "Passwords Mismatched",Toast.LENGTH_SHORT).show();
                    return;
                }


                if(isOnline()) {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        addUserdetails(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(signup.this, "Authentication failed. " +task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void addUserdetails(FirebaseUser user1){
        String userId = user1.getUid();

//        user USER = new user();
//        USER.setName(name.getText().toString());
//        USER.setEmail(email.getText().toString());
//        USER.setPhone(phone.getText().toString());
//        USER.setType("user");
        mDatabase.child("users").child(userId).child("name").setValue(name.getText().toString());
        mDatabase.child("users").child(userId).child("email").setValue(email.getText().toString());
        mDatabase.child("users").child(userId).child("phone").setValue(phone.getText().toString());
        mDatabase.child("users").child(userId).child("type").setValue("user");
//        mDatabase.child(userId).setValue(USER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(signup.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(signup.this, home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

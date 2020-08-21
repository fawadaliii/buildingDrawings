package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;
import com.SAR.buildingdrawing.models.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signin extends AppCompatActivity {

    private AppCompatEditText email;
    private AppCompatEditText password;
    private AppCompatButton signin;
    private RelativeLayout RLSignin;
    private Toolbar toolbar;
    private AppCompatTextView dontHaveAccount;
    private AppCompatTextView forgotPassword;

    private static final String TAG = "signin";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.i_email_field);
        password = findViewById(R.id.i_password_field);
        signin = findViewById(R.id.sign_in_btn);
        RLSignin = findViewById(R.id.RL_signin);
        toolbar = findViewById(R.id.toolbar);
        dontHaveAccount = findViewById(R.id.dont_have_acc);
        forgotPassword = findViewById(R.id.forgot_password);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign in");

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        RLSignin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this, signup.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(signin.this, "Email cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(signin.this, "Password cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isOnline()) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString().replaceAll(" ",""),
                            password.getText().toString())
                            .addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user.isEmailVerified()){
                                            setUser(user);
                                        }
                                        else{
                                            Toast.makeText(signin.this,"We have sent verification email to " + user.getEmail() +", Please verify your account.",
                                        Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(signin.this, "Authentication failed. "
                                                        + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    LayoutInflater inflater = (LayoutInflater) signin.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.forget_password_alert,null, false);

                    final AppCompatEditText emailEditText = formElementsView.findViewById(R.id.emailEditText);

                    new AlertDialog.Builder(signin.this).setView(formElementsView)
                            .setTitle("Enter Email")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if(emailEditText.getText().toString().contentEquals("")){
                                        Toast.makeText(getApplicationContext(),"Email cannot be null",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        mAuth.sendPasswordResetEmail(emailEditText.getText().toString().replaceAll(" ",""))
                                                .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent.");
                                                            Toast.makeText(getApplicationContext(),
                                                                    "An email has been sent to "+emailEditText.getText().toString()
                                                                    +"Follow the instructions in email to reset your password.",Toast.LENGTH_LONG).show();
                                                        }
                                                        else {
                                                            Toast.makeText(signin.this, "Authentication failed. "
                                                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }

                            }).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setUser(FirebaseUser user1){
        String userId = user1.getUid();

        final DatabaseReference users_table = FirebaseDatabase.getInstance().getReference("users");

        users_table.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user USER = dataSnapshot.getValue(user.class);
                common.currentUser = USER;
                users_table.removeEventListener(this);
                Toast.makeText(getApplicationContext(),"Sign in Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signin.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveTaskToBack(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

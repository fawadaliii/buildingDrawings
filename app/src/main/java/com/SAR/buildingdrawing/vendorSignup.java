package com.SAR.buildingdrawing;

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
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class vendorSignup extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText name;
    private AppCompatEditText email;
    private AppCompatEditText phone;
    private AppCompatEditText password;
    private AppCompatEditText confirmPssword;
    private AppCompatButton signup;
    private RelativeLayout RLSignup;

    private AppCompatTextView alreadyHaveAcc;

    private static final String TAG = "signupasvendor";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Spinner selectVendorCategory;

    List<String> vendorCategorySpinnerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_signup);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign up as Vendor");

        name = findViewById(R.id.name_field);
        email = findViewById(R.id.email_field);
        phone = findViewById(R.id.phone_field);
        password = findViewById(R.id.password_field);
        confirmPssword = findViewById(R.id.confirm_password_field);

        signup = findViewById(R.id.sign_up_btn);
        RLSignup = findViewById(R.id.RL_signup);
        toolbar = findViewById(R.id.toolbar);
        alreadyHaveAcc = findViewById(R.id.already_have_acc);

        selectVendorCategory = findViewById(R.id.selectVendorCategory);

        vendorCategorySpinnerItems = new ArrayList<String>();

        vendorCategorySpinnerItems.add("Select Vendor Category *");
        vendorCategorySpinnerItems.add(".....");
        vendorCategorySpinnerItems.add("Appliance repairs");
        vendorCategorySpinnerItems.add("Electrical repairs");
        vendorCategorySpinnerItems.add("Internet repairs");
        vendorCategorySpinnerItems.add("Locksmith");
        vendorCategorySpinnerItems.add("Painter");
        vendorCategorySpinnerItems.add("Plumbing repairs");
        vendorCategorySpinnerItems.add("Pressure washing");
        vendorCategorySpinnerItems.add("Surface refinishing");

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, vendorCategorySpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectVendorCategory.setAdapter(adapter);

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vendorSignup.this, signin.class);
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
                    Toast.makeText(vendorSignup.this, "First Name cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(vendorSignup.this, "Email cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(vendorSignup.this, "Phone Number cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(vendorSignup.this, "Password cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 5 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.getText().toString().contentEquals(confirmPssword.getText().toString())){
                    Toast.makeText(vendorSignup.this, "Passwords Mismatched",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectVendorCategory.getSelectedItemPosition() == 0 || selectVendorCategory.getSelectedItemPosition() == 1){
                    Toast.makeText(vendorSignup.this, "Select Vendor Category",Toast.LENGTH_SHORT).show();
                    selectVendorCategory.setFocusable(true);
                    selectVendorCategory.setFocusableInTouchMode(true);
                    selectVendorCategory.requestFocus();
                    selectVendorCategory.performClick();
                    return;
                }


                if(isOnline()) {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(vendorSignup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        addUserdetails(user);
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(vendorSignup.this, "Authentication failed. " +task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
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

    private void addUserdetails(final FirebaseUser user1){
        String userId = user1.getUid();

//        user USER = new user();
//        USER.setName(name.getText().toString());
//        USER.setEmail(email.getText().toString());
//        USER.setPhone(phone.getText().toString());
//        USER.setType("user");
        mDatabase.child("users").child(userId).child("name").setValue(name.getText().toString());
        mDatabase.child("users").child(userId).child("email").setValue(email.getText().toString());
        mDatabase.child("users").child(userId).child("phone").setValue(phone.getText().toString());
        mDatabase.child("users").child(userId).child("type").setValue("vendor");
        mDatabase.child("users").child(userId).child("vendor_category").setValue(selectVendorCategory.getSelectedItem().toString());
//        mDatabase.child(userId).setValue(USER);
        user1.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(vendorSignup.this,
                                    "Verification email sent to " + user1.getEmail(),
                                    Toast.LENGTH_SHORT).show();
//                            Toast.makeText(vendorSignup.this,
//                                    ""+user1.isEmailVerified(),
//                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(vendorSignup.this, signin.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(vendorSignup.this,
                                    "Failed to send verification email. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(vendorSignup.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(vendorSignup.this, home.class);
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

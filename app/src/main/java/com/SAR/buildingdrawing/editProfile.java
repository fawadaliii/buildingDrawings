package com.SAR.buildingdrawing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.SAR.buildingdrawing.models.common;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int GALLERY_REQUEST_CODE = 100;
    static final Integer READ_STORAGE_PERMISSION_REQUEST_CODE=0x3;
    private AppCompatEditText name;
    private AppCompatEditText email;
    private AppCompatEditText phone;
    private AppCompatEditText password;
    private AppCompatEditText vendorCategory;
    private RelativeLayout space6;
    private AppCompatTextView vendorCategoryTag;

    private AppCompatButton saveChanges;
    private RelativeLayout RLProfile;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private CircleImageView profileImage;

    private ProgressDialog mDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.view.Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        toolbar = findViewById(R.id.toolbar);

        RLProfile = findViewById(R.id.RL_profile);

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        password = findViewById(R.id.profilePassword);

        vendorCategory = findViewById(R.id.vendorCategory);
        space6 = findViewById(R.id.space6);
        vendorCategoryTag = findViewById(R.id.vendorCategoryTag);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(common.currentUser.getType().contentEquals("user")){
            vendorCategoryTag.setVisibility(View.GONE);
            space6.setVisibility(View.GONE);
            vendorCategory.setVisibility(View.GONE);
        }

        saveChanges = findViewById(R.id.savechanges_btn);

        profileImage = findViewById(R.id.profileImage);

        mDialog = new ProgressDialog(editProfile.this);

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible){
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        saveChanges.setVisibility(View.INVISIBLE);
        name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        password.setEnabled(false);
        vendorCategory.setEnabled(false);

        if(isOnline()){
            mDialog.setMessage("Please wait...");
            mDialog.show();

            if ( common.currentUser.getPhoto() != null){
                if(!common.currentUser.getPhoto().contentEquals("")){
                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(common.currentUser.getPhoto())
                            .into(profileImage);
                }
            }

            name.setText(common.currentUser.getName());
            email.setText(common.currentUser.getEmail());
            phone.setText(common.currentUser.getPhone());
            password.setText("********");
            vendorCategory.setText(common.currentUser.getVendor_category());
            mDialog.dismiss();

        }else{
            Toast.makeText(editProfile.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setMessage("Please wait...");
                mDialog.show();

                if(isOnline()){
                    if(TextUtils.isEmpty(name.getText().toString())){
                        Toast.makeText(editProfile.this, "Name cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(phone.getText().toString())){
                        Toast.makeText(editProfile.this, "Phone cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(password.getText().toString())){
                        Toast.makeText(editProfile.this, "Password cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(common.currentUser.getType().contentEquals("vendor") && TextUtils.isEmpty(vendorCategory.getText().toString())){
                        Toast.makeText(editProfile.this, "Vendor category cannot be null",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    FirebaseUser user1 = mAuth.getCurrentUser();
                    String userId = user1.getUid();

                    if(common.currentUser.getType().contentEquals("vendor")){
                        common.currentUser.setName(name.getText().toString());
                        common.currentUser.setPhone(phone.getText().toString());
                        common.currentUser.setVendor_category(vendorCategory.getText().toString());

                        mDatabase.child("users").child(userId).child("name").setValue(name.getText().toString());
                        mDatabase.child("users").child(userId).child("phone").setValue(phone.getText().toString());
                        mDatabase.child("users").child(userId).child("vendor_category").setValue(vendorCategory.getText().toString());
                    }
                    else{
                        common.currentUser.setName(name.getText().toString());
                        common.currentUser.setPhone(phone.getText().toString());

                        mDatabase.child("users").child(userId).child("name").setValue(name.getText().toString());
                        mDatabase.child("users").child(userId).child("phone").setValue(phone.getText().toString());
                    }

                    name.setEnabled(false);
                    phone.setEnabled(false);
                    vendorCategory.setEnabled(false);

                    saveChanges.setVisibility(View.INVISIBLE);
                    mDialog.dismiss();

                    Toast.makeText(editProfile.this, "Changes Saved.", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(editProfile.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermissionForReadExtertalStorage()){
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                requestPermissionForReadExtertalStorage();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                pickFromGallery();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home_nav:
                Intent intent = new Intent(editProfile.this, home.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

//            case R.id.build_house_nav:
//                //
////                Intent intent1 = new Intent(home.this, .class);
////                startActivity(intent1);
////                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                return true;

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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_profile,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            case R.id.editBTN_actionBar:
                //
                name.setEnabled(true);
                phone.setEnabled(true);
                vendorCategory.setEnabled(true);

                name.requestFocus();
                name.setSelection(name.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

                saveChanges.setVisibility(View.VISIBLE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    profileImage.setImageURI(selectedImage);
                    File imageFile = new File(getRealPathFromURI(selectedImage));

                    mDialog.setMessage("Please wait...");
                    mDialog.show();

                    FirebaseUser user1 = mAuth.getCurrentUser();
                    final String userId = user1.getUid();

                    final StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

                    StorageReference filepath = mStorageReference.child("profile_pics").child(userId)
                            .child(selectedImage.getLastPathSegment());

                    filepath.putFile(selectedImage)
                            .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Upload succeeded
                                    if (taskSnapshot.getMetadata() != null) {
                                        if (taskSnapshot.getMetadata().getReference() != null) {
                                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imageUrl = uri.toString();
                                                    mDatabase.child("users").child(userId).child("photo").setValue(imageUrl);
                                                    common.currentUser.setPhoto(imageUrl);

                                                    Toast.makeText(editProfile.this, "Profile picture changed.", Toast.LENGTH_SHORT).show();
                                                    mDialog.dismiss();
                                                }
                                            });
                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                // Upload failed
                                Toast.makeText(editProfile.this, "Upload failed.", Toast.LENGTH_SHORT).show();

                                }
                            });
                    break;
            }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

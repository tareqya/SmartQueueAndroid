package com.samrtq.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.samrtq.R;
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.StorageController;
import com.samrtq.controls.UserController;
import com.samrtq.entities.User;
import com.samrtq.utils.Constants;
import com.samrtq.utils.Generic;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    private CircleImageView profileEdit_image;
    private FloatingActionButton profileEdit_FBTN_uploadImage;
    private TextInputLayout profileEdit_TF_firstName;
    private TextInputLayout profileEdit_TF_lastName;
    private TextInputLayout profileEdit_TF_phone;
    private Button editProfile_BTN_update;
    private Uri selectedImageUri;
    private User user;
    UserController userController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.USER);
        if(!checkPermissions()) {
            requestPermissions();
        }
        findViews();
        initVars();
    }

    public  boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                100
        );
    }

    private void initVars() {
        userController = new UserController();
        userController.setUserDataCallBack(new UserDataCallBack() {
            @Override
            public void onSaveUserComplete(boolean status, String msg) {
                if(status){
                    Toast.makeText(ProfileEditActivity.this, "Profile update successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileEditActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserDataFetchComplete(User user) {

            }
        });

        displayUser();
        editProfile_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });

        profileEdit_FBTN_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSourceDialog();
            }
        });
    }

    private void updateUserData() {
        if(selectedImageUri != null){
            // upload new image
            StorageController storageController = new StorageController();
            AuthControl authControl = new AuthControl();
            String uid = authControl.getCurrentUser().getUid();
            String imagePath = Constants.USERS_TABLE + "/" + uid + "." + Generic.getFileExtension(this, selectedImageUri);
            storageController.uploadImage(selectedImageUri, imagePath);
            user.setImagePath(imagePath);
        }

        String firstName = profileEdit_TF_firstName.getEditText().getText().toString();
        String lastName = profileEdit_TF_lastName.getEditText().getText().toString();
        String phone = profileEdit_TF_phone.getEditText().getText().toString();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

        userController.SaveUserData(user);
    }

    private void displayUser() {
        if(user.getImageUrl()!= null){
            Glide.with(this).load(user.getImageUrl()).into(profileEdit_image);
        }
        profileEdit_TF_firstName.getEditText().setText(user.getFirstName());
        profileEdit_TF_lastName.getEditText().setText(user.getLastName());
        if(user.getPhone() != null){
            profileEdit_TF_phone.getEditText().setText(user.getPhone());
        }

    }

    private void findViews() {
        profileEdit_image = findViewById(R.id.profileEdit_image);
        profileEdit_FBTN_uploadImage = findViewById(R.id.profileEdit_FBTN_uploadImage);
        profileEdit_TF_firstName = findViewById(R.id.profileEdit_TF_firstName);
        profileEdit_TF_lastName = findViewById(R.id.profileEdit_TF_lastName);
        profileEdit_TF_phone = findViewById(R.id.profileEdit_TF_phone);
        editProfile_BTN_update = findViewById(R.id.editProfile_BTN_update);
    }

    private void showImageSourceDialog() {
        CharSequence[] items = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder.setTitle("Choose Image Source");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResults.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery_results.launch(intent);
    }

    private final ActivityResultLauncher<Intent> gallery_results = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            try {
                                Intent intent = result.getData();
                                selectedImageUri = intent.getData();
                                final InputStream imageStream = ProfileEditActivity.this.getContentResolver().openInputStream(selectedImageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                profileEdit_image.setImageBitmap(selectedImage);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(ProfileEditActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(ProfileEditActivity.this, "Gallery canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    private final ActivityResultLauncher<Intent> cameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            Bitmap bitmap = (Bitmap)  intent.getExtras().get("data");
                            profileEdit_image.setImageBitmap(bitmap);
                            selectedImageUri = Generic.getImageUri(ProfileEditActivity.this, bitmap);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(ProfileEditActivity.this, "Camera canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
}
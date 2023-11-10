package com.samrtq.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.samrtq.R;
import com.samrtq.callback.AuthCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.entities.User;
import com.samrtq.utils.AuthUser;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;

    private AuthUser authUser;
    private User user;
    private AuthControl authControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void findViews() {
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);

    }

    private void initVars() {

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_PB_loading.setVisibility(View.VISIBLE);
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                AuthUser authUser = new AuthUser()
                        .setEmail(email)
                        .setPassword(password);

                authControl = new AuthControl(authUser);
                authControl.setAuthCallBack(new AuthCallBack() {
                    @Override
                    public void onCreateAccountComplete(boolean status, String error) {
                        signup_PB_loading.setVisibility(View.INVISIBLE);
                        if(status){
                            Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                authControl.signUp();
            }
        });
    }
}
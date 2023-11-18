package com.samrtq.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.samrtq.R;
import com.samrtq.callback.AuthCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.main.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private CircularProgressIndicator login_PB_loading;
    private Button login_BTN_signup;
    private Button login_BTN_login;
    private AuthUser authUser;
    private AuthControl authControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        initVars();
    }

    public void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_PB_loading = findViewById(R.id.login_PB_loading);
        login_BTN_signup = findViewById(R.id.login_BTN_signup);
        login_BTN_login = findViewById(R.id.login_BTN_login);
    }

    private void initVars() {
        authControl = new AuthControl();
        authControl.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAccountComplete(boolean status, String msg) {

            }

            @Override
            public void onLoginComplete(boolean status, String msg) {
                login_PB_loading.setVisibility(View.INVISIBLE);
                if(status){
                    // go to home page
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

            }
        });

        login_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInputs()) return;
                login_PB_loading.setVisibility(View.VISIBLE);
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();

                authUser = new AuthUser()
                        .setPassword(password)
                        .setEmail(email);


                authControl.signIn(authUser);
            }
        });
    }


    public boolean checkInputs(){
        String email = login_TF_email.getEditText().getText().toString();
        String password = login_TF_password.getEditText().getText().toString();
        String [] inputs = { email, password};
        for(String input: inputs){
            if(input.trim().equals("")){
                Toast.makeText(LoginActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(authControl.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
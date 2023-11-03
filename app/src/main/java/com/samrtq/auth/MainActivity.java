package com.samrtq.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.samrtq.R;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private CircularProgressIndicator login_PB_loading;
    private Button login_BTN_signup;
    private Button login_BTN_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        login_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
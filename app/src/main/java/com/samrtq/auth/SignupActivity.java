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
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.UserController;
import com.samrtq.entities.User;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;
    private AuthControl authControl;
    private UserController userController;
    private AuthUser authUser;

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

        authControl = new AuthControl();
        userController = new UserController();

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                signup_PB_loading.setVisibility(View.VISIBLE);
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                authUser = new AuthUser()
                        .setEmail(email)
                        .setPassword(password);
                authControl.signUp(authUser);
            }
        });

        userController.setUserDataCallBack(new UserDataCallBack() {
            @Override
            public void onSaveUserComplete(boolean status, String msg) {
                if(status){
                    Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                    authControl.signOut();
                    finish();
                }else {
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserDataFetchComplete(User user) {

            }
        });

        authControl.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAccountComplete(boolean status, String msg) {

                Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                if(status){
                    // save data user
                    String firstName = signup_TF_firstName.getEditText().getText().toString();
                    String lastName = signup_TF_lastName.getEditText().getText().toString();

                    User user = new User()
                            .setEmail(authUser.getEmail())
                            .setFirstName(firstName)
                            .setLastName(lastName)
                            .setPhone("");
                    user.setId(authControl.getCurrentUser().getUid());
                    userController.SaveUserData(user);
                }else {
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onLoginComplete(boolean status, String msg) {

            }
        });
    }


    public boolean checkInput(){
        String firstName = signup_TF_firstName.getEditText().getText().toString();
        String lastName = signup_TF_lastName.getEditText().getText().toString();
        String email = signup_TF_email.getEditText().getText().toString();
        String password = signup_TF_password.getEditText().getText().toString();
        String confirmPassword = signup_TF_confirmPassword.getEditText().getText().toString();

        String [] inputs = {firstName, lastName, email, password, confirmPassword};
        for(String input: inputs){
            if(input.trim().equals("")){
                Toast.makeText(SignupActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(SignupActivity.this, "Password and confirm password mismatch!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
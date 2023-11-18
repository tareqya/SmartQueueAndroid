package com.samrtq.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.samrtq.R;
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.UserControl;
import com.samrtq.entities.User;

public class HomeActivity extends AppCompatActivity {

    private User user;
    private UserControl userControl;
    private AuthControl authControl;

    private TextView home_TV_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        initVars();
    }

    private void findViews() {
        home_TV_username = findViewById(R.id.home_TV_username);
    }

    private void initVars() {
        authControl = new AuthControl();
        userControl = new UserControl();
        userControl.setUserDataCallBack(new UserDataCallBack() {
            @Override
            public void onSaveUserComplete(boolean status, String msg) {

            }

            @Override
            public void onUserDataFetchComplete(User fetchedUser) {
                user = fetchedUser;
                String txt = "Hello " + user.getFirstName() + ",";
                home_TV_username.setText(txt);
            }
        });

        userControl.getUserData(authControl.getCurrentUser().getUid());

    }
}
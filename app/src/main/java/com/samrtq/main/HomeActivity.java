package com.samrtq.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.samrtq.R;
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.UserControl;
import com.samrtq.entities.User;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private AddAppointmentFragment addAppointmentFragment;
    private FrameLayout home_frame_home, home_frame_profile, home_frame_addAppointment;
    private BottomNavigationView home_BN;
    private UserControl userControl;
    private AuthControl authControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        initVars();
        fetchCurrentUser();
    }

    private void findViews() {
        home_frame_profile = findViewById(R.id.home_frame_profile);
        home_frame_home = findViewById(R.id.home_frame_home);
        home_frame_addAppointment = findViewById(R.id.home_frame_addAppointment);
        home_BN = findViewById(R.id.home_BN);
    }

    private void initVars() {
        authControl = new AuthControl();
        userControl = new UserControl();

        FragmentManager fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();

        addAppointmentFragment = new AddAppointmentFragment(this, fragmentManager);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_addAppointment, addAppointmentFragment).commit();

        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();


        home_frame_profile.setVisibility(View.INVISIBLE);
        home_frame_addAppointment.setVisibility(View.INVISIBLE);

        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_addAppointment.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.VISIBLE);
                }else if (item.getItemId() == R.id.profile){
                    home_frame_profile.setVisibility(View.VISIBLE);
                    home_frame_addAppointment.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }else if(item.getItemId() == R.id.addQueue) {
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_addAppointment.setVisibility(View.VISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });

    }

    private void fetchCurrentUser() {
        userControl.setUserDataCallBack(new UserDataCallBack() {
            @Override
            public void onSaveUserComplete(boolean status, String msg) {

            }
            @Override
            public void onUserDataFetchComplete(User fetchedUser) {
                homeFragment.setUser(fetchedUser);
            }
        });

        userControl.getUserData(authControl.getCurrentUser().getUid());
    }
}
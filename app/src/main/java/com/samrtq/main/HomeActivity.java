package com.samrtq.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.samrtq.R;
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.UserController;
import com.samrtq.entities.User;
import com.samrtq.utils.Constants;
import com.samrtq.utils.NotificationReceiver;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private AddAppointmentFragment addAppointmentFragment;
    private FrameLayout home_frame_home, home_frame_profile, home_frame_addAppointment;
    private BottomNavigationView home_BN;
    private UserController userController;
    private AuthControl authControl;
    private static String CHANNEL_ID = "1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        initVars();
        fetchCurrentUser();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkPermissions()) {
                requestPermissions();
            }

        }
        createNotificationChannel();

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.POST_NOTIFICATIONS,
                },
                100
        );
    }

    private void findViews() {
        home_frame_profile = findViewById(R.id.home_frame_profile);
        home_frame_home = findViewById(R.id.home_frame_home);
        home_frame_addAppointment = findViewById(R.id.home_frame_addAppointment);
        home_BN = findViewById(R.id.home_BN);
    }

    private void initVars() {
        authControl = new AuthControl();
        userController = new UserController();

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
                if (item.getItemId() == R.id.home) {
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_addAppointment.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.VISIBLE);
                } else if (item.getItemId() == R.id.profile) {
                    home_frame_profile.setVisibility(View.VISIBLE);
                    home_frame_addAppointment.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                } else if (item.getItemId() == R.id.addQueue) {
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_addAppointment.setVisibility(View.VISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });

    }

    private void fetchCurrentUser() {
        userController.setUserDataCallBack(new UserDataCallBack() {
            @Override
            public void onSaveUserComplete(boolean status, String msg) {

            }

            @Override
            public void onUserDataFetchComplete(User fetchedUser) {
                homeFragment.setUser(fetchedUser);
                profileFragment.setUser(fetchedUser);
            }
        });

        userController.getUserData(authControl.getCurrentUser().getUid());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SmartQ";
            String description = "SamrtQ reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Smart Queue - Reminder")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    public void scheduleNotification(Calendar calendar, String msg) {

        // Create an intent to trigger the BroadcastReceiver
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("body", msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
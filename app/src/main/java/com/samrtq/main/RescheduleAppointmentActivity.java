package com.samrtq.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.samrtq.R;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.controls.AppointmentController;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class RescheduleAppointmentActivity extends AppCompatActivity {
    private Appointment appointment;
    private Button reschedule_BTN_pickDate;
    private TextView reschedule_TV_pickDate;
    private Button reschedule_BTN_pickTime;
    private TextView reschedule_TV_pickTime;
    private Button reschedule_BTN_update;
    private AppointmentController appointmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_appointment);

        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra(Constants.SELECTED_APPOINTMENT);

        findViews();
        initVars();
        displayTimes();
    }

    private void displayTimes() {
        Date date = appointment.getDate();
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Use the format() method to convert Date to String
        String formattedDate = sdf.format(date);

        reschedule_TV_pickDate.setText(formattedDate);
        String time = date.getHours() + ":" + date.getMinutes();
        reschedule_TV_pickTime.setText(time);
    }


    private void findViews() {
        reschedule_BTN_pickDate = findViewById(R.id.reschedule_BTN_pickDate);
        reschedule_TV_pickDate = findViewById(R.id.reschedule_TV_pickDate);
        reschedule_BTN_pickTime = findViewById(R.id.reschedule_BTN_pickTime);
        reschedule_TV_pickTime = findViewById(R.id.reschedule_TV_pickTime);
        reschedule_BTN_update = findViewById(R.id.reschedule_BTN_update);
    }
    private void initVars() {
        appointmentController = new AppointmentController();
        appointmentController.setAppointmentCallBack(new AppointmentCallBack() {
            @Override
            public void onAddAppointmentComplete(Task<Void> task) {

            }

            @Override
            public void onFetchAppointmentsComplete(ArrayList<Appointment> appointments) {

            }

            @Override
            public void onCancelAppointmentComplete(Task<Void> task) {

            }

            @Override
            public void onUpdateAppointmentComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(RescheduleAppointmentActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(RescheduleAppointmentActivity.this, "Your appointment date reschedule successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(RescheduleAppointmentActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        reschedule_BTN_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDatePicker();
            }
        });

        reschedule_BTN_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTimePicker();
            }
        });

        reschedule_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAppointmentTime();
            }
        });
    }

    private void updateAppointmentTime() {
        try{
            String dateTimeString = reschedule_TV_pickDate.getText().toString() + " " + reschedule_TV_pickTime.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            Date dateTime = sdf.parse(dateTimeString);
            appointment.setDate(dateTime);

            appointmentController.updateAppointmentTime(appointment);
        }catch (Exception e){
            Toast.makeText(RescheduleAppointmentActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void toggleDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(getSupportFragmentManager(), "datePicker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                // Use the format() method to convert Date to String
                String formattedDate = sdf.format(date);
                reschedule_TV_pickDate.setText(formattedDate);
            }
        });
    }

    private void toggleTimePicker() {
        MaterialTimePicker picker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select Appointment time")
                        .build();

        picker.show(getSupportFragmentManager(), "timePicker");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = picker.getHour() + ":" + picker.getMinute();
                reschedule_TV_pickTime.setText(time);
            }
        });
    }
}
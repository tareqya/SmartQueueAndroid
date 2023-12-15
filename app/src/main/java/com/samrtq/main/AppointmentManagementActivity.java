package com.samrtq.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.samrtq.R;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

import java.text.SimpleDateFormat;

public class AppointmentManagementActivity extends AppCompatActivity {
    private ImageView appointmentManagement_IV_doctorImage;
    private TextView appointmentManagement_TV_doctorName;
    private TextView appointmentManagement_TV_doctorSpecialist;
    private TextView appointmentManagement_TV_appointmentTime;
    private Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_management);

        Intent intent = getIntent();

        appointment = (Appointment) intent.getSerializableExtra(Constants.SELECTED_APPOINTMENT);
        
        findViews();
        initVars();
        displayAppointment();
    }

    private void findViews() {
        appointmentManagement_IV_doctorImage = findViewById(R.id.appointmentManagement_IV_doctorImage);
        appointmentManagement_TV_doctorName = findViewById(R.id.appointmentManagement_TV_doctorName);
        appointmentManagement_TV_doctorSpecialist = findViewById(R.id.appointmentManagement_TV_doctorSpecialist);
        appointmentManagement_TV_appointmentTime = findViewById(R.id.appointmentManagement_TV_appointmentTime);

    }

    private void initVars() {

    }

    private void displayAppointment(){
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = desiredDateFormat.format(appointment.getDate());
        appointmentManagement_TV_appointmentTime.setText(formattedDate);

        appointmentManagement_TV_doctorName.setText(appointment.getDoctor().getName());
        appointmentManagement_TV_doctorSpecialist.setText(appointment.getDoctor().getSpecialist());
        Glide.with(this).load(appointment.getDoctor().getImageUrl())
                .into(appointmentManagement_IV_doctorImage);
    }
}
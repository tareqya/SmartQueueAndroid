package com.samrtq.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private CardView appointmentManagement_CV_reschedule;
    private CardView appointmentManagement_CV_cancel;
    private CardView appointmentManagement_CV_closer;

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
        appointmentManagement_CV_closer = findViewById(R.id.appointmentManagement_CV_closer);
        appointmentManagement_CV_cancel = findViewById(R.id.appointmentManagement_CV_cancel);
        appointmentManagement_CV_reschedule = findViewById(R.id.appointmentManagement_CV_reschedule);
    }

    private void initVars() {
        appointmentManagement_CV_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentManagementActivity.this, RescheduleAppointmentActivity.class);
                intent.putExtra(Constants.SELECTED_APPOINTMENT, appointment);
                startActivity(intent);
            }
        });
        appointmentManagement_CV_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentManagementActivity.this, CancelAppointmentActivity.class);
                intent.putExtra(Constants.SELECTED_APPOINTMENT, appointment);
                startActivity(intent);
            }
        });

        appointmentManagement_CV_closer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentManagementActivity.this, CloserAppointmentActivity.class);
                intent.putExtra(Constants.SELECTED_APPOINTMENT, appointment);
                startActivity(intent);
            }
        });
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
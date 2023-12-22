package com.samrtq.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.samrtq.R;
import com.samrtq.adapter.AppointmentAdapter;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.callback.OnClickAppointment;
import com.samrtq.controls.AppointmentController;
import com.samrtq.controls.AuthControl;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;

public class CloserAppointmentActivity extends AppCompatActivity {
    private Appointment appointment;
    private ArrayList<Appointment> appointments;
    private AppointmentController appointmentController;
    private RecyclerView closerAppointments_RV_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closer_appointment);
        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra(Constants.SELECTED_APPOINTMENT);

        findViews();
        initVars();
    }

    private void findViews() {
        closerAppointments_RV_appointments = findViewById(R.id.closerAppointments_RV_appointments);

    }

    private void initVars() {
        appointmentController = new AppointmentController();
        appointmentController.setAppointmentCallBack(new AppointmentCallBack() {
            @Override
            public void onAddAppointmentComplete(Task<Void> task) {

            }

            @Override
            public void onFetchAppointmentsComplete(ArrayList<Appointment> arr) {
                appointments = arr;
                Collections.sort(appointments);
                AppointmentAdapter appointmentAdapter = new AppointmentAdapter(CloserAppointmentActivity.this, appointments);
                appointmentAdapter.setOnClickAppointment(new OnClickAppointment() {
                    @Override
                    public void onClick(Appointment selectedAppointment, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CloserAppointmentActivity.this);
                        builder.setMessage("Are you sure you want to change your the appointment ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appointmentController.changeAppointment(selectedAppointment, appointment.getClientId());
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                closerAppointments_RV_appointments.setLayoutManager(new LinearLayoutManager(CloserAppointmentActivity.this, LinearLayoutManager.VERTICAL, false));
                closerAppointments_RV_appointments.setHasFixedSize(true);
                closerAppointments_RV_appointments.setItemAnimator(new DefaultItemAnimator());
                closerAppointments_RV_appointments.setAdapter(appointmentAdapter);

            }

            @Override
            public void onCancelAppointmentComplete(Task<Void> task) {

                if(task.isSuccessful()){
                    Intent intent = new Intent(CloserAppointmentActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(CloserAppointmentActivity.this, "Your appointment changed", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(CloserAppointmentActivity.this, err, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onUpdateAppointmentComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    appointmentController.cancelAppointment(appointment);
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(CloserAppointmentActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

        appointmentController.fetchCloserAppointments(appointment);
    }
}
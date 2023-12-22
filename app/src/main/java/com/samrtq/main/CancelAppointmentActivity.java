package com.samrtq.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.samrtq.R;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.controls.AppointmentController;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CancelAppointmentActivity extends AppCompatActivity {
    private Appointment appointment;

    private ImageView cancelAppointment_IV_doctorImage;
    private TextView cancelAppointment_TV_doctorName;
    private TextView cancelAppointment_TV_doctorSpecialist;
    private TextView cancelAppointment_TV_appointmentTime;
    private Button cancelAppointment_BTN_cancel;

    private AppointmentController appointmentController ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);

        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra(Constants.SELECTED_APPOINTMENT);
        findViews();
        initVars();
        displayAppointment();
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
                if(task.isSuccessful()){
                    Toast.makeText(CancelAppointmentActivity.this, "Appointment removed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CancelAppointmentActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(CancelAppointmentActivity.this, err, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onUpdateAppointmentComplete(Task<Void> task) {

            }
        });
        cancelAppointment_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CancelAppointmentActivity.this);
                builder.setMessage("Are you sure you want to cancel the appointment ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appointmentController.cancelAppointment(appointment);
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
    }

    private void findViews() {
        cancelAppointment_IV_doctorImage = findViewById(R.id.cancelAppointment_IV_doctorImage);
        cancelAppointment_TV_doctorName = findViewById(R.id.cancelAppointment_TV_doctorName);
        cancelAppointment_TV_doctorSpecialist = findViewById(R.id.cancelAppointment_TV_doctorSpecialist);
        cancelAppointment_TV_appointmentTime = findViewById(R.id.cancelAppointment_TV_appointmentTime);

        cancelAppointment_BTN_cancel = findViewById(R.id.cancelAppointment_BTN_cancel);
    }

    private void displayAppointment(){
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = desiredDateFormat.format(appointment.getDate());
        cancelAppointment_TV_appointmentTime.setText(formattedDate);

        cancelAppointment_TV_doctorName.setText(appointment.getDoctor().getName());
        cancelAppointment_TV_doctorSpecialist.setText(appointment.getDoctor().getSpecialist());
        Glide.with(this).load(appointment.getDoctor().getImageUrl())
                .into(cancelAppointment_IV_doctorImage);
    }
}
package com.samrtq.controls;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

public class AppointmentController {

    private DatabaseReference mDatabase;
    private AppointmentCallBack appointmentCallBack;
    public AppointmentController(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void setAppointmentCallBack(AppointmentCallBack appointmentCallBack){
        this.appointmentCallBack = appointmentCallBack;
    }

    public void addAppointment(Appointment appointment){
        mDatabase.child(Constants.APPOINTMENT_TABLE).push().setValue(appointment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                appointmentCallBack.onAddAppointmentComplete(task);
            }
        });
    }


}

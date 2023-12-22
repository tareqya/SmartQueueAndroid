package com.samrtq.controls;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.entities.Appointment;
import com.samrtq.utils.Constants;

import java.util.ArrayList;
import java.util.Date;

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

    public void fetchUserAppointment(String uid){
        StorageController storageController = new StorageController();
        mDatabase.child(Constants.APPOINTMENT_TABLE)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Appointment> appointments = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    appointment.setId(dataSnapshot.getKey());
                    if(appointment.getClientId().equals(uid) && appointment.getDate().compareTo(new Date()) >= 0){
                        String imageUrl = storageController.downloadImageUrl(appointment.getDoctor().getImageUrl());
                        appointment.getDoctor().setImageUrl(imageUrl);
                        appointments.add(appointment);
                    }
                }

                appointmentCallBack.onFetchAppointmentsComplete(appointments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchCloserAppointments(Appointment myAppointment){
        StorageController storageController = new StorageController();
        mDatabase.child(Constants.APPOINTMENT_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Appointment> appointments = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    appointment.setId(dataSnapshot.getKey());
                    if(appointment.getClientId().equals("")
                            && appointment.getDate().compareTo(myAppointment.getDate()) < 0
                            && myAppointment.getDoctor().getSpecialist().equals(appointment.getDoctor().getSpecialist())
                            && appointment.getDate().compareTo(new Date()) >= 0){

                        String imageUrl = storageController.downloadImageUrl(appointment.getDoctor().getImageUrl());
                        appointment.getDoctor().setImageUrl(imageUrl);
                        appointments.add(appointment);
                    }
                }

                appointmentCallBack.onFetchAppointmentsComplete(appointments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void cancelAppointment(Appointment appointment){
        mDatabase.child(Constants.APPOINTMENT_TABLE).child(appointment.getId()).child("clientId").setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                appointmentCallBack.onCancelAppointmentComplete(task);
            }
        });
    }

    public void changeAppointment(Appointment appointment, String uid){
        mDatabase.child(Constants.APPOINTMENT_TABLE).child(appointment.getId()).child("clientId").setValue(uid)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        appointmentCallBack.onUpdateAppointmentComplete(task);
                    }
                });
    }

    public void updateAppointmentTime(Appointment appointment){
        mDatabase.child(Constants.APPOINTMENT_TABLE).child(appointment.getId()).child("date").setValue(appointment.getDate())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        appointmentCallBack.onUpdateAppointmentComplete(task);
                    }
                });
    }
}

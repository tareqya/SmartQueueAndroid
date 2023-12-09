package com.samrtq.controls;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.samrtq.callback.DoctorCallBack;
import com.samrtq.entities.Doctor;
import com.samrtq.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    private DatabaseReference mDatabase;
    private DoctorCallBack doctorCallBack;

    public DoctorController(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void setDoctorCallBack(DoctorCallBack doctorCallBack){
        this.doctorCallBack = doctorCallBack;
    }

    public void fetchAllDoctors(){
        mDatabase.child(Constants.DOCTOR_TABLE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Doctor> data = new ArrayList<>();
                if(task.isSuccessful()){
                    GenericTypeIndicator<ArrayList<Doctor>> typeIndicator = new GenericTypeIndicator<ArrayList<Doctor>>() {};
                    data = task.getResult().getValue(typeIndicator);
                    ArrayList<Doctor> doctors = new ArrayList<>();
                    for(Doctor d: data){
                        if(d != null){
                            doctors.add(d);
                        }
                    }
                    doctorCallBack.onDoctorsFetchComplete(doctors);
                }else{
                    doctorCallBack.onDoctorsFetchComplete(data);
                }
            }
        });
    }

}

package com.samrtq.callback;

import com.samrtq.entities.Doctor;

import java.util.ArrayList;

public interface DoctorCallBack {
    void onDoctorsFetchComplete(ArrayList<Doctor> doctors);
}

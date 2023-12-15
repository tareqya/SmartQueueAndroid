package com.samrtq.callback;

import com.google.android.gms.tasks.Task;
import com.samrtq.entities.Appointment;

import java.util.ArrayList;

public interface AppointmentCallBack {
    void onAddAppointmentComplete(Task<Void> task);
    void onFetchUserAppointmentsComplete(ArrayList<Appointment>appointments);
}

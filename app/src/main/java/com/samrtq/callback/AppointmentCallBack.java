package com.samrtq.callback;

import com.google.android.gms.tasks.Task;
import com.samrtq.entities.Appointment;

import java.util.ArrayList;

public interface AppointmentCallBack {
    void onAddAppointmentComplete(Task<Void> task);
    void onFetchAppointmentsComplete(ArrayList<Appointment>appointments);
    void onCancelAppointmentComplete(Task<Void> task);
    void onUpdateAppointmentComplete(Task<Void> task);
}

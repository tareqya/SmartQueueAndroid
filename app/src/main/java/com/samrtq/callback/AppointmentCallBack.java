package com.samrtq.callback;

import com.google.android.gms.tasks.Task;

public interface AppointmentCallBack {
    void onAddAppointmentComplete(Task<Void> task);
}

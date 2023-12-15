package com.samrtq.callback;

import com.samrtq.entities.Appointment;

public interface OnClickAppointment {
    void onClick(Appointment appointment, int position);

}

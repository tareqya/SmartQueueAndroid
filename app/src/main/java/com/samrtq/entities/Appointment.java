package com.samrtq.entities;

import java.io.Serializable;
import java.util.Date;

public class Appointment extends FirebaseId implements Comparable<Appointment>, Serializable {
    private String title;
    private Date date;
    private Doctor doctor;

    private String clientId;

    public Appointment() {}

    public String getTitle() {
        return title;
    }

    public Appointment setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Appointment setDate(Date date) {
        this.date = date;
        return this;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Appointment setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public Appointment setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public int compareTo(Appointment appointment) {
        return this.getDate().compareTo(appointment.getDate());
    }
}

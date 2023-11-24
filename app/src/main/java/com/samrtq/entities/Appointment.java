package com.samrtq.entities;

import java.util.Date;

public class Appointment extends FirebaseId{
    private String title;
    private Date date;
    private Doctor doctor;

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
}

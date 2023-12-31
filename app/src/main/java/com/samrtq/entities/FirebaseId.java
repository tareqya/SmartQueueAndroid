package com.samrtq.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class FirebaseId implements Serializable {
    private String id;

    public FirebaseId() {
        this.id = "";
    }

    public FirebaseId setId(String id) {
        this.id = id;
        return this;
    }
    @Exclude
    public String getId() {
        return id;
    }
}

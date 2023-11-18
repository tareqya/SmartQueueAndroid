package com.samrtq.entities;

public class FirebaseId {
    private String id;

    public FirebaseId() {
        this.id = "";
    }

    public FirebaseId setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}

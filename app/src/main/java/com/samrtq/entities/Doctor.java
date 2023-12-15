package com.samrtq.entities;

import java.io.Serializable;

public class Doctor extends FirebaseId implements Serializable {

    private String name;
    private String imageUrl;
    private String specialist;

    public Doctor() {}

    public String getName() {
        return name;
    }

    public Doctor setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Doctor setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getSpecialist() {
        return specialist;
    }

    public Doctor setSpecialist(String specialist) {
        this.specialist = specialist;
        return this;
    }

    public String toString(){
        return this.name;
    }
}

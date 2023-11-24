package com.samrtq.entities;

public class Doctor extends FirebaseId {

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
}

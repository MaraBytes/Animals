package com.example.animals.Classes;

public class Animals {
    private String species;
    private String description;
    private double latitude;
    private double longitude;
    private String time;

    public Animals(String species, String description, double latitude, double longitude, String time) {
        this.species = species;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public Animals() {
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

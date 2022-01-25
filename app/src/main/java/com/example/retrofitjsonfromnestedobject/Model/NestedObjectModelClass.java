package com.example.retrofitjsonfromnestedobject.Model;

public class NestedObjectModelClass {

    //Model class for Main Api Model Class
    private String release, category, duration;

    public NestedObjectModelClass() {
    }

    public NestedObjectModelClass(String release, String category, String duration) {
        this.release = release;
        this.category = category;
        this.duration = duration;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

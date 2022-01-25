package com.example.retrofitjsonfromnestedobject.Model;

import com.google.gson.annotations.SerializedName;

public class ApiModelClass {

    //object (Must be same of API)
    private String id, name, rating, imageUrl;

    //detailed object element
    @SerializedName("details")
    private NestedObjectModelClass nestedObject;

    //default or null constructor
    public ApiModelClass() {
    }

    public ApiModelClass(String id, String name, String rating, String imageUrl, NestedObjectModelClass nestedObject) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.nestedObject = nestedObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public NestedObjectModelClass getNestedObject() {
        return nestedObject;
    }

    public void setNestedObject(NestedObjectModelClass nestedObject) {
        this.nestedObject = nestedObject;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

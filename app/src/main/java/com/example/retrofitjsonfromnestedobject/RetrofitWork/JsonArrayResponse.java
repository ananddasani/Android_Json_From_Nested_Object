package com.example.retrofitjsonfromnestedobject.RetrofitWork;

import com.example.retrofitjsonfromnestedobject.Model.ApiModelClass;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonArrayResponse {

    @SerializedName("moviz")
    @Expose
    ApiModelClass[] movieArray;

    public JsonArrayResponse(ApiModelClass[] movieArray) {
        this.movieArray = movieArray;
    }

    public ApiModelClass[] getMovieArray() {
        return movieArray;
    }

    public void setMovieArray(ApiModelClass[] movieArray) {
        this.movieArray = movieArray;
    }
}

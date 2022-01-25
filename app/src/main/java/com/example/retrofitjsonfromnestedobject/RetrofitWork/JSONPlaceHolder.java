package com.example.retrofitjsonfromnestedobject.RetrofitWork;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolder {

    @GET("v1/95db9038-3204-400d-8ac9-b6909da445cd")
    Call<JsonArrayResponse> getMovieArray();
}

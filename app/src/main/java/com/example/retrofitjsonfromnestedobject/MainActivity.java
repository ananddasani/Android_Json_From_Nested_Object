package com.example.retrofitjsonfromnestedobject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitjsonfromnestedobject.Adapter.CustomAdapter;
import com.example.retrofitjsonfromnestedobject.Model.ApiModelClass;
import com.example.retrofitjsonfromnestedobject.RetrofitWork.JSONPlaceHolder;
import com.example.retrofitjsonfromnestedobject.RetrofitWork.JsonArrayResponse;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<ApiModelClass> movieList;
    CustomAdapter customAdapter;
    String url = "https://mocki.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //Create retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolder jsonPlaceHolder = retrofit.create(JSONPlaceHolder.class);

        Call<JsonArrayResponse> call = jsonPlaceHolder.getMovieArray();

        call.enqueue(new Callback<JsonArrayResponse>() {
            @Override
            public void onResponse(Call<JsonArrayResponse> call, Response<JsonArrayResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.GONE);

                JsonArrayResponse jsonArrayResponse = response.body();
                movieList = new ArrayList<>(Arrays.asList(jsonArrayResponse.getMovieArray()));

                //now pass this movieList in the custom adapter
                customAdapter = new CustomAdapter(MainActivity.this, movieList);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<JsonArrayResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure Executed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
# Android_Json_From_Nested_Object
Fetching Data from Nested Object and then sowing onto the UI using RecyclerView

# Dependency
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

# App Highlight

<img src="app_images/Retrofit Json From Nested Object Code.png" /><br>

<img src="app_images/Retrofit Json From Nested Object App1.png" width="300" /> <img src="app_images/Retrofit Json From Nested Object App2.png" width="300" />


# Code

### ApiModelClass.java
- Type of Model Class (The name should be exact of the API Data to be fetched)
```
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
```

### NestedObjectModelClass.java
- Model Class of Nested Object Present in the API which we have to fetch
```
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
```

### JsonArrayResponse.java
- Fetching the Key:Value pair ("moviz": [ ... ])
```
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
```

### JSONPlaceholder Inerface
- Call will be of type JsonArrayResponse because we will get the Array (as key:value pair)
```
public interface JSONPlaceHolder {

    @GET("v1/95db9038-3204-400d-8ac9-b6909da445cd")
    Call<JsonArrayResponse> getMovieArray();
}
```

#### MainActivity.java
```
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
```

### CustomAdapter.java
```
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    List<ApiModelClass> arrayList;

    public CustomAdapter(Context context, List<ApiModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        ApiModelClass data = arrayList.get(position);

        holder.name.setText(data.getName());

        //extracting data form nested objects
        holder.category.setText(data.getNestedObject().getCategory());
        holder.duration.setText(data.getNestedObject().getDuration());

        Glide.with(context).load(data.getImageUrl()).into(holder.imageView);

        //set the rating
        float ratings = Float.parseFloat(data.getRating());
        holder.ratingBar.setRating(ratings / 2);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        ImageView imageView;
        TextView name, category, duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.nameTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            duration = itemView.findViewById(R.id.durationTextView);

        }
    }
}
```

### custom_layout.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        app:cardElevation="6dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <ImageView
                android:id="@+id/imageView"
                android:layout_width="150dp"
                android:layout_height="150dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:srcCompat="@tools:sample/avatars" />

            <TextView
                android:id="@+id/categoryTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:fontFamily="sans-serif-condensed"
                android:text="Category:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toBottomOf="@+id/nameTV" />

            <TextView
                android:id="@+id/nameTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:fontFamily="sans-serif-condensed"
                android:text="Name:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toTopOf="@+id/imageView" />

            <TextView
                android:id="@+id/durationTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:fontFamily="sans-serif-condensed"
                android:text="Duration:"
                android:textColor="@color/black"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toBottomOf="@+id/categoryTV" />

            <RatingBar
                android:id="@+id/ratingBar"
                android:layout_width="wrap_content"
                android:layout_height="0dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                app:layout_constraintBottom_toBottomOf="@+id/imageView"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toEndOf="@+id/imageView"
                app:layout_constraintTop_toBottomOf="@+id/durationTV"
                tools:ignore="SpeakableTextPresentCheck" />

            <TextView
                android:id="@+id/nameTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="TextView"
                app:layout_constraintBottom_toBottomOf="@+id/nameTV"
                app:layout_constraintStart_toEndOf="@+id/nameTV"
                app:layout_constraintTop_toTopOf="@+id/nameTV" />

            <TextView
                android:id="@+id/categoryTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="TextView"
                app:layout_constraintBottom_toBottomOf="@+id/categoryTV"
                app:layout_constraintStart_toEndOf="@+id/categoryTV"
                app:layout_constraintTop_toTopOf="@+id/categoryTV" />

            <TextView
                android:id="@+id/durationTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:text="TextView"
                app:layout_constraintBottom_toBottomOf="@+id/durationTV"
                app:layout_constraintStart_toEndOf="@+id/durationTV"
                app:layout_constraintTop_toTopOf="@+id/durationTV" />

        </androidx.constraintlayout.widget.ConstraintLayout>

    </androidx.cardview.widget.CardView>

</androidx.constraintlayout.widget.ConstraintLayout>
```

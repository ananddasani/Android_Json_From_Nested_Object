package com.example.retrofitjsonfromnestedobject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitjsonfromnestedobject.Model.ApiModelClass;
import com.example.retrofitjsonfromnestedobject.R;

import java.util.List;

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

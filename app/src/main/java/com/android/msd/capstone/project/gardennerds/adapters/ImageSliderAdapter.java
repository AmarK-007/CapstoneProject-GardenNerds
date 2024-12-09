package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.models.productResponses.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of media items in a RecyclerView for an image slider.
 */
public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {

    private final ArrayList<Media> mediaList;
    private final Context context;

    /**
     * Constructor for ImageSliderAdapter.
     *
     * @param mediaList List of media items to display.
     * @param context   Context in which the adapter is used.
     */
    public ImageSliderAdapter(ArrayList<Media> mediaList, Context context) {
        this.mediaList = mediaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_image_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(mediaList.get(position).getLink()).placeholder(R.drawable.designer).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    /**
     * ViewHolder class for ImageSliderAdapter.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        /**
         * Constructor for MyViewHolder.
         *
         * @param itemView The view of the item.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
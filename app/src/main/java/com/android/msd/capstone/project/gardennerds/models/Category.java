package com.android.msd.capstone.project.gardennerds.models;

import com.android.msd.capstone.project.gardennerds.R;

import java.util.ArrayList;

public class Category {
    int imageId;
    String title;
    String Query;

    public Category(int imageId, String title, String query) {
        this.imageId = imageId;
        this.title = title;
        Query = query;
    }

    public Category() {

    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public ArrayList<Category> getCategoryList(){
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.indoor_plants,"Indoor Plants", "Indoor Gardening Plants"));
        categories.add(new Category(R.drawable.outdoor_plants,"Outdoor Plants", "Outdoor Gardening Plants"));
        categories.add(new Category(R.drawable.gardening_tools,"Gardening Tools", "Gardening Tools"));
        categories.add(new Category(R.drawable.gardening_decors,"Garden Decorative", "Garden Decorative"));

        return categories;
    }
}

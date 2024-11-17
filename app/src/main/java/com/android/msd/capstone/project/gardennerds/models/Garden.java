package com.android.msd.capstone.project.gardennerds.models;

public class Garden {
    private String name;
    private String area;
    private String imageUrl;

    public Garden(String name, String area, String imageUrl) {
        this.name = name;
        this.area = area;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

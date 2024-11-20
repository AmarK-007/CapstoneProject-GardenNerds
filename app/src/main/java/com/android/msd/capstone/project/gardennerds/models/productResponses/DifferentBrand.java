package com.android.msd.capstone.project.gardennerds.models.productResponses;

public class DifferentBrand {
    private String title;
    private String link;
    private String thumbnail;
    private String price;

    public DifferentBrand(String title, String link, String thumbnail, String price) {
        this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

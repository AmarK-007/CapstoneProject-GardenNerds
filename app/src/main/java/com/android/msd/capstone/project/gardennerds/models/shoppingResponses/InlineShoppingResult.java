package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;


import com.google.gson.annotations.SerializedName;

public class InlineShoppingResult {
    private long position;
    private String blockPosition;
    private String title;
    private String price;
    private double extractedPrice;
    private String link;
    private String source;
    private Double rating;
    private Long reviews;
    private String thumbnail;
    private String shipping;
    private String[] extensions;

    @SerializedName("position")
    public long getPosition() { return position; }
    @SerializedName("position")
    public void setPosition(long value) { this.position = value; }

    @SerializedName("block_position")
    public String getBlockPosition() { return blockPosition; }
    @SerializedName("block_position")
    public void setBlockPosition(String value) { this.blockPosition = value; }

    @SerializedName("title")
    public String getTitle() { return title; }
    @SerializedName("title")
    public void setTitle(String value) { this.title = value; }

    @SerializedName("price")
    public String getPrice() { return price; }
    @SerializedName("price")
    public void setPrice(String value) { this.price = value; }

    @SerializedName("extracted_price")
    public double getExtractedPrice() { return extractedPrice; }
    @SerializedName("extracted_price")
    public void setExtractedPrice(double value) { this.extractedPrice = value; }

    @SerializedName("link")
    public String getLink() { return link; }
    @SerializedName("link")
    public void setLink(String value) { this.link = value; }

    @SerializedName("source")
    public String getSource() { return source; }
    @SerializedName("source")
    public void setSource(String value) { this.source = value; }

    @SerializedName("rating")
    public Double getRating() { return rating; }
    @SerializedName("rating")
    public void setRating(Double value) { this.rating = value; }

    @SerializedName("reviews")
    public Long getReviews() { return reviews; }
    @SerializedName("reviews")
    public void setReviews(Long value) { this.reviews = value; }

    @SerializedName("thumbnail")
    public String getThumbnail() { return thumbnail; }
    @SerializedName("thumbnail")
    public void setThumbnail(String value) { this.thumbnail = value; }

    @SerializedName("shipping")
    public String getShipping() { return shipping; }
    @SerializedName("shipping")
    public void setShipping(String value) { this.shipping = value; }

    @SerializedName("extensions")
    public String[] getExtensions() { return extensions; }
    @SerializedName("extensions")
    public void setExtensions(String[] value) { this.extensions = value; }
}

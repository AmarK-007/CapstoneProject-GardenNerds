package com.android.msd.capstone.project.gardennerds.models.productResponses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ProductResults {
    private String product_id;
    private String title;
    private String[] prices;
    private String[] conditions;
    private TypicalPrices typical_prices;
    private long reviews;
    private double rating;
    private String[] extensions;
    private String description;
    private ArrayList<Media> media;
    private Map<String, Size> sizes;
    private String[] highlights;

    public String[] getHighlights() {
        return highlights;
    }

    @Override
    public String toString() {
        return "ProductResults{" +
                "product_id='" + product_id + '\'' +
                ", title='" + title + '\'' +
                ", prices=" + Arrays.toString(prices) +
                ", conditions=" + Arrays.toString(conditions) +
                ", typical_prices=" + typical_prices +
                ", reviews=" + reviews +
                ", rating=" + rating +
                ", extensions=" + Arrays.toString(extensions) +
                ", description='" + description + '\'' +
                ", media=" + media +
                ", sizes=" + sizes +
                ", highlights=" + Arrays.toString(highlights) +
                '}';
    }

    public void setHighlights(String[] highlights) {
        this.highlights = highlights;
    }

    public String getProductID() {
        return product_id;
    }

    public void setProductID(String productID) {
        this.product_id = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getPrices() {
        return prices;
    }

    public void setPrices(String[] prices) {
        this.prices = prices;
    }

    public String[] getConditions() {
        return conditions;
    }

    public void setConditions(String[] conditions) {
        this.conditions = conditions;
    }

    public TypicalPrices getTypicalPrices() {
        return typical_prices;
    }

    public void setTypicalPrices(TypicalPrices typicalPrices) {
        this.typical_prices = typicalPrices;
    }

    public long getReviews() {
        return reviews;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public Map<String, Size> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, Size> sizes) {
        this.sizes = sizes;
    }

    //    @JsonProperty("product_id")
//    public String getProductID() { return productID; }
//    @JsonProperty("product_id")
//    public void setProductID(String value) { this.productID = value; }
//
//    @JsonProperty("title")
//    public String getTitle() { return title; }
//    @JsonProperty("title")
//    public void setTitle(String value) { this.title = value; }
//
//    @JsonProperty("prices")
//    public String[] getPrices() { return prices; }
//    @JsonProperty("prices")
//    public void setPrices(String[] value) { this.prices = value; }
//
//    @JsonProperty("conditions")
//    public String[] getConditions() { return conditions; }
//    @JsonProperty("conditions")
//    public void setConditions(String[] value) { this.conditions = value; }
//
//    @JsonProperty("typical_prices")
//    public TypicalPrices getTypicalPrices() { return typicalPrices; }
//    @JsonProperty("typical_prices")
//    public void setTypicalPrices(TypicalPrices value) { this.typicalPrices = value; }
//
//    @JsonProperty("reviews")
//    public long getReviews() { return reviews; }
//    @JsonProperty("reviews")
//    public void setReviews(long value) { this.reviews = value; }
//
//    @JsonProperty("rating")
//    public double getRating() { return rating; }
//    @JsonProperty("rating")
//    public void setRating(double value) { this.rating = value; }
//
//    @JsonProperty("extensions")
//    public String[] getExtensions() { return extensions; }
//    @JsonProperty("extensions")
//    public void setExtensions(String[] value) { this.extensions = value; }
//
//    @JsonProperty("description")
//    public String getDescription() { return description; }
//    @JsonProperty("description")
//    public void setDescription(String value) { this.description = value; }
//
//    @JsonProperty("media")
//    public Media[] getMedia() { return media; }
//    @JsonProperty("media")
//    public void setMedia(Media[] value) { this.media = value; }
//
//    @JsonProperty("sizes")
//    public Map<String, Size> getSizes() { return sizes; }
//    @JsonProperty("sizes")
//    public void setSizes(Map<String, Size> value) { this.sizes = value; }
}

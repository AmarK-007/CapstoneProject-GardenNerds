package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;


public class ShoppingResult {
    private long position;
    private String title;
    private String productLink;
    private String product_id;
    private String serpapiProductAPI;
    private NumberOfComparisons numberOfComparisons;
    private String source;
    private String sourceIcon;
    private String price;
    private double extractedPrice;

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getProductID() {
        return product_id;
    }

    public void setProductID(String productID) {
        this.product_id = productID;
    }

    public String getSerpapiProductAPI() {
        return serpapiProductAPI;
    }

    public void setSerpapiProductAPI(String serpapiProductAPI) {
        this.serpapiProductAPI = serpapiProductAPI;
    }

    public NumberOfComparisons getNumberOfComparisons() {
        return numberOfComparisons;
    }

    public void setNumberOfComparisons(NumberOfComparisons numberOfComparisons) {
        this.numberOfComparisons = numberOfComparisons;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getExtractedPrice() {
        return extractedPrice;
    }

    public void setExtractedPrice(double extractedPrice) {
        this.extractedPrice = extractedPrice;
    }

    public Long getExtractedOldPrice() {
        return extractedOldPrice;
    }

    public void setExtractedOldPrice(Long extractedOldPrice) {
        this.extractedOldPrice = extractedOldPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getReviews() {
        return reviews;
    }

    public void setReviews(Long reviews) {
        this.reviews = reviews;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public SecondHandCondition getSecondHandCondition() {
        return secondHandCondition;
    }

    public void setSecondHandCondition(SecondHandCondition secondHandCondition) {
        this.secondHandCondition = secondHandCondition;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    private String oldPrice;
    private Long extractedOldPrice;
    private Double rating;
    private Long reviews;
    private String thumbnail;
    private String tag;
    private String[] extensions;
    private Delivery delivery;
    private SecondHandCondition secondHandCondition;
    private String snippet;
    private String badge;
//
//    @SerializedName("position")
//    public long getPosition() { return position; }
//    @SerializedName("position")
//    public void setPosition(long value) { this.position = value; }
//
//    @SerializedName("title")
//    public String getTitle() { return title; }
//    @SerializedName("title")
//    public void setTitle(String value) { this.title = value; }
//
//    @SerializedName("product_link")
//    public String getProductLink() { return productLink; }
//    @SerializedName("product_link")
//    public void setProductLink(String value) { this.productLink = value; }
//
//    @SerializedName("product_id")
//    public String getProductID() { return productID; }
//    @SerializedName("product_id")
//    public void setProductID(String value) { this.productID = value; }
//
//    @SerializedName("serpapi_product_api")
//    public String getSerpapiProductAPI() { return serpapiProductAPI; }
//    @SerializedName("serpapi_product_api")
//    public void setSerpapiProductAPI(String value) { this.serpapiProductAPI = value; }
//
//    @SerializedName("number_of_comparisons")
//    public NumberOfComparisons getNumberOfComparisons() { return numberOfComparisons; }
//    @SerializedName("number_of_comparisons")
//    public void setNumberOfComparisons(NumberOfComparisons value) { this.numberOfComparisons = value; }
//
//    @SerializedName("source")
//    public String getSource() { return source; }
//    @SerializedName("source")
//    public void setSource(String value) { this.source = value; }
//
//    @SerializedName("source_icon")
//    public String getSourceIcon() { return sourceIcon; }
//    @SerializedName("source_icon")
//    public void setSourceIcon(String value) { this.sourceIcon = value; }
//
//    @SerializedName("price")
//    public String getPrice() { return price; }
//    @SerializedName("price")
//    public void setPrice(String value) { this.price = value; }
//
//    @SerializedName("extracted_price")
//    public double getExtractedPrice() { return extractedPrice; }
//    @SerializedName("extracted_price")
//    public void setExtractedPrice(double value) { this.extractedPrice = value; }
//
//    @SerializedName("old_price")
//    public String getOldPrice() { return oldPrice; }
//    @SerializedName("old_price")
//    public void setOldPrice(String value) { this.oldPrice = value; }
//
//    @SerializedName("extracted_old_price")
//    public Long getExtractedOldPrice() { return extractedOldPrice; }
//    @SerializedName("extracted_old_price")
//    public void setExtractedOldPrice(Long value) { this.extractedOldPrice = value; }
//
//    @SerializedName("rating")
//    public Double getRating() { return rating; }
//    @SerializedName("rating")
//    public void setRating(Double value) { this.rating = value; }
//
//    @SerializedName("reviews")
//    public Long getReviews() { return reviews; }
//    @SerializedName("reviews")
//    public void setReviews(Long value) { this.reviews = value; }
//
//    @SerializedName("thumbnail")
//    public String getThumbnail() { return thumbnail; }
//    @SerializedName("thumbnail")
//    public void setThumbnail(String value) { this.thumbnail = value; }
//
//    @SerializedName("tag")
//    public String getTag() { return tag; }
//    @SerializedName("tag")
//    public void setTag(String value) { this.tag = value; }
//
//    @SerializedName("extensions")
//    public String[] getExtensions() { return extensions; }
//    @SerializedName("extensions")
//    public void setExtensions(String[] value) { this.extensions = value; }
//
//    @SerializedName("delivery")
//    public Delivery getDelivery() { return delivery; }
//    @SerializedName("delivery")
//    public void setDelivery(Delivery value) { this.delivery = value; }
//
//    @SerializedName("second_hand_condition")
//    public SecondHandCondition getSecondHandCondition() { return secondHandCondition; }
//    @SerializedName("second_hand_condition")
//    public void setSecondHandCondition(SecondHandCondition value) { this.secondHandCondition = value; }
//
//    @SerializedName("snippet")
//    public String getSnippet() { return snippet; }
//    @SerializedName("snippet")
//    public void setSnippet(String value) { this.snippet = value; }
//
//    @SerializedName("badge")
//    public String getBadge() { return badge; }
//    @SerializedName("badge")
//    public void setBadge(String value) { this.badge = value; }


//    private int position;
//    private String title;
//    private String price;
//    private double extracted_price;
//    private String link;
//    private String source;
//    private String shipping;
//    private String thumbnail;
//
//    // Getters and setters
//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public double getExtractedPrice() {
//        return extracted_price;
//    }
//
//    public void setExtractedPrice(double extracted_price) {
//        this.extracted_price = extracted_price;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getShipping() {
//        return shipping;
//    }
//
//    public void setShipping(String shipping) {
//        this.shipping = shipping;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
}

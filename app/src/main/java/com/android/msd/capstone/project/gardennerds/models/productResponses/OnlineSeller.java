package com.android.msd.capstone.project.gardennerds.models.productResponses;

public class OnlineSeller {
    private long position;
    private String name;
    private boolean topQualityStore;
    private String paymentMethods;
    private String link;
    private String directLink;
    private DetailsAndOffer[] detailsAndOffers;
    private String originalPrice;
    private String basePrice;
    private AdditionalPrice additionalPrice;
    private String badge;
    private String totalPrice;

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTopQualityStore() {
        return topQualityStore;
    }

    public void setTopQualityStore(boolean topQualityStore) {
        this.topQualityStore = topQualityStore;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDirectLink() {
        return directLink;
    }

    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }

    public DetailsAndOffer[] getDetailsAndOffers() {
        return detailsAndOffers;
    }

    public void setDetailsAndOffers(DetailsAndOffer[] detailsAndOffers) {
        this.detailsAndOffers = detailsAndOffers;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public AdditionalPrice getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(AdditionalPrice additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}

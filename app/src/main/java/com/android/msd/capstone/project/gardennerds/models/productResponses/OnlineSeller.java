package com.android.msd.capstone.project.gardennerds.models.productResponses;

public class OnlineSeller {
    private long position;
    private String name;
    private boolean topQualityStore;
    private String payment_methods;
    private String link;
    private String direct_link;
    private DetailsAndOffer[] details_and_offers;
    private String original_price;
    private String base_price;
    private AdditionalPrice additional_price;
    private String badge;
    private String total_price;

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
        return payment_methods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.payment_methods = paymentMethods;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDirectLink() {
        return direct_link;
    }

    public void setDirectLink(String directLink) {
        this.direct_link = directLink;
    }

    public DetailsAndOffer[] getDetailsAndOffers() {
        return details_and_offers;
    }

    public void setDetailsAndOffers(DetailsAndOffer[] detailsAndOffers) {
        this.details_and_offers = detailsAndOffers;
    }

    public String getOriginalPrice() {
        return original_price;
    }

    public void setOriginalPrice(String originalPrice) {
        this.original_price = originalPrice;
    }

    public String getBasePrice() {
        return base_price;
    }

    public void setBasePrice(String basePrice) {
        this.base_price = basePrice;
    }

    public AdditionalPrice getAdditionalPrice() {
        return additional_price;
    }

    public void setAdditionalPrice(AdditionalPrice additionalPrice) {
        this.additional_price = additionalPrice;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(String totalPrice) {
        this.total_price = totalPrice;
    }
}

package com.android.msd.capstone.project.gardennerds.models.productResponses;


/**
 * "low": "$549",
 * "high": "$625",
 * "shown_price": "$613.79 at Bonanza - BundleGalore's Booth"*/
public class TypicalPrices {
    private String shown_price;
    private String low;
    private String high;

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getShownPrice() {
        return shown_price;
    }

    public void setShownPrice(String shownPrice) {
        this.shown_price = shownPrice;
    }
}

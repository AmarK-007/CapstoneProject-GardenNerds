package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class SellersResults {
    private OnlineSeller[] onlineSellers;

    public SellersResults(OnlineSeller[] onlineSellers) {
        this.onlineSellers = onlineSellers;
    }

    public OnlineSeller[] getOnlineSellers() {
        return onlineSellers;
    }

    public void setOnlineSellers(OnlineSeller[] onlineSellers) {
        this.onlineSellers = onlineSellers;
    }

    //    @JsonProperty("online_sellers")
//    public OnlineSeller[] getOnlineSellers() { return onlineSellers; }
//    @JsonProperty("online_sellers")
//    public void setOnlineSellers(OnlineSeller[] value) { this.onlineSellers = value; }
}

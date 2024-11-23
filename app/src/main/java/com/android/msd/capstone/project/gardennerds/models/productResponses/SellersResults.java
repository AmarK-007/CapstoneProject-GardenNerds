package com.android.msd.capstone.project.gardennerds.models.productResponses;


import java.util.ArrayList;

public class SellersResults {
    private ArrayList<OnlineSeller> online_sellers;

    public SellersResults(ArrayList<OnlineSeller> onlineSellers) {
        this.online_sellers = onlineSellers;
    }

    public ArrayList<OnlineSeller> getOnlineSellers() {
        return online_sellers;
    }

    public void setOnlineSellers(ArrayList<OnlineSeller> onlineSellers) {
        this.online_sellers = onlineSellers;
    }

    //    @JsonProperty("online_sellers")
//    public OnlineSeller[] getOnlineSellers() { return onlineSellers; }
//    @JsonProperty("online_sellers")
//    public void setOnlineSellers(OnlineSeller[] value) { this.onlineSellers = value; }
}

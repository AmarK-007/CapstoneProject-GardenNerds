package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class SearchInformation {
    private String shoppingResultsState;
    private String queryDisplayed;

    @SerializedName("shopping_results_state")
    public String getShoppingResultsState() { return shoppingResultsState; }
    @SerializedName("shopping_results_state")
    public void setShoppingResultsState(String value) { this.shoppingResultsState = value; }

    @SerializedName("query_displayed")
    public String getQueryDisplayed() { return queryDisplayed; }
    @SerializedName("query_displayed")
    public void setQueryDisplayed(String value) { this.queryDisplayed = value; }
}

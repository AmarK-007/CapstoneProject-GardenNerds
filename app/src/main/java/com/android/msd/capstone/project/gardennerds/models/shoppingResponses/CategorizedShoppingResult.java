package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class CategorizedShoppingResult {
    private String title;
    private ShoppingResult[] shoppingResults;

    @SerializedName("title")
    public String getTitle() { return title; }
    @SerializedName("title")
    public void setTitle(String value) { this.title = value; }

    @SerializedName("shopping_results")
    public ShoppingResult[] getShoppingResults() { return shoppingResults; }
    @SerializedName("shopping_results")
    public void setShoppingResults(ShoppingResult[] value) { this.shoppingResults = value; }
}

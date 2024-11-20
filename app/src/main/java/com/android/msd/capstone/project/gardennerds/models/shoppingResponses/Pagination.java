package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class Pagination {
    private String next;

    @SerializedName("next")
    public String getNext() { return next; }
    @SerializedName("next")
    public void setNext(String value) { this.next = value; }
}

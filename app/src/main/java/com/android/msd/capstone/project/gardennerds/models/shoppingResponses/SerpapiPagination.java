package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class SerpapiPagination {
    private String nextLink;
    private String next;

    @SerializedName("next_link")
    public String getNextLink() { return nextLink; }
    @SerializedName("next_link")
    public void setNextLink(String value) { this.nextLink = value; }

    @SerializedName("next")
    public String getNext() { return next; }
    @SerializedName("next")
    public void setNext(String value) { this.next = value; }
}

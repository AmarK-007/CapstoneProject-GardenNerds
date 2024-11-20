package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;


import com.google.gson.annotations.SerializedName;

public class Option {
    private String text;
    private String serpapiLink;

    @SerializedName("text")
    public String getText() { return text; }
    @SerializedName("text")
    public void setText(String value) { this.text = value; }

    @SerializedName("serpapi_link")
    public String getSerpapiLink() { return serpapiLink; }
    @SerializedName("serpapi_link")
    public void setSerpapiLink(String value) { this.serpapiLink = value; }
}

package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class SearchMetadata {
    private String id;
    private String status;
    private String jsonEndpoint;
    private String createdAt;
    private String processedAt;
    private String googleShoppingURL;
    private String rawHTMLFile;
    private long totalTimeTaken;

    @SerializedName("id")
    public String getID() { return id; }
    @SerializedName("id")
    public void setID(String value) { this.id = value; }

    @SerializedName("status")
    public String getStatus() { return status; }
    @SerializedName("status")
    public void setStatus(String value) { this.status = value; }

    @SerializedName("json_endpoint")
    public String getJSONEndpoint() { return jsonEndpoint; }
    @SerializedName("json_endpoint")
    public void setJSONEndpoint(String value) { this.jsonEndpoint = value; }

    @SerializedName("created_at")
    public String getCreatedAt() { return createdAt; }
    @SerializedName("created_at")
    public void setCreatedAt(String value) { this.createdAt = value; }

    @SerializedName("processed_at")
    public String getProcessedAt() { return processedAt; }
    @SerializedName("processed_at")
    public void setProcessedAt(String value) { this.processedAt = value; }

    @SerializedName("google_shopping_url")
    public String getGoogleShoppingURL() { return googleShoppingURL; }
    @SerializedName("google_shopping_url")
    public void setGoogleShoppingURL(String value) { this.googleShoppingURL = value; }

    @SerializedName("raw_html_file")
    public String getRawHTMLFile() { return rawHTMLFile; }
    @SerializedName("raw_html_file")
    public void setRawHTMLFile(String value) { this.rawHTMLFile = value; }

    @SerializedName("total_time_taken")
    public long getTotalTimeTaken() { return totalTimeTaken; }
    @SerializedName("total_time_taken")
    public void setTotalTimeTaken(long value) { this.totalTimeTaken = value; }
}

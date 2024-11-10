package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class SearchMetadata {
    private String id;
    private String status;
    private String jsonEndpoint;
    private String createdAt;
    private String processedAt;
    private String googleProductURL;
    private String rawHTMLFile;
    private double totalTimeTaken;

    public SearchMetadata(String id, String status, String jsonEndpoint, String createdAt, String processedAt, String googleProductURL, String rawHTMLFile, double totalTimeTaken) {
        this.id = id;
        this.status = status;
        this.jsonEndpoint = jsonEndpoint;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
        this.googleProductURL = googleProductURL;
        this.rawHTMLFile = rawHTMLFile;
        this.totalTimeTaken = totalTimeTaken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJsonEndpoint() {
        return jsonEndpoint;
    }

    public void setJsonEndpoint(String jsonEndpoint) {
        this.jsonEndpoint = jsonEndpoint;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    public String getGoogleProductURL() {
        return googleProductURL;
    }

    public void setGoogleProductURL(String googleProductURL) {
        this.googleProductURL = googleProductURL;
    }

    public String getRawHTMLFile() {
        return rawHTMLFile;
    }

    public void setRawHTMLFile(String rawHTMLFile) {
        this.rawHTMLFile = rawHTMLFile;
    }

    public double getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(double totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }
}

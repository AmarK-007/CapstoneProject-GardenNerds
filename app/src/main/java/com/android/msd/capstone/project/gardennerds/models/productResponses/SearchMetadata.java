package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class SearchMetadata {
    private String id;
    private String status;
    private String json_endpoint;
    private String created_at;
    private String processed_at;
    private String google_product_url;
    private String raw_html_file;
    private double total_time_taken;

    public SearchMetadata(String id, String status, String json_endpoint, String created_at, String processed_at, String google_product_url, String raw_html_file, double total_time_taken) {
        this.id = id;
        this.status = status;
        this.json_endpoint = json_endpoint;
        this.created_at = created_at;
        this.processed_at = processed_at;
        this.google_product_url = google_product_url;
        this.raw_html_file = raw_html_file;
        this.total_time_taken = total_time_taken;
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

    public String getJson_endpoint() {
        return json_endpoint;
    }

    public void setJson_endpoint(String json_endpoint) {
        this.json_endpoint = json_endpoint;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProcessed_at() {
        return processed_at;
    }

    public void setProcessed_at(String processed_at) {
        this.processed_at = processed_at;
    }

    public String getGoogle_product_url() {
        return google_product_url;
    }

    public void setGoogle_product_url(String google_product_url) {
        this.google_product_url = google_product_url;
    }

    public String getRaw_html_file() {
        return raw_html_file;
    }

    public void setRaw_html_file(String raw_html_file) {
        this.raw_html_file = raw_html_file;
    }

    public double getTotal_time_taken() {
        return total_time_taken;
    }

    public void setTotal_time_taken(double total_time_taken) {
        this.total_time_taken = total_time_taken;
    }
}

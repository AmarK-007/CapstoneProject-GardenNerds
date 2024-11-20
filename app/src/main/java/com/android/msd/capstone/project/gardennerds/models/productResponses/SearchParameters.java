package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class SearchParameters {
    private String engine;
    private String productID;
    private String googleDomain;
    private String hl;
    private String gl;
    private String device;

    public SearchParameters(String engine, String productID, String googleDomain, String hl, String gl, String device) {
        this.engine = engine;
        this.productID = productID;
        this.googleDomain = googleDomain;
        this.hl = hl;
        this.gl = gl;
        this.device = device;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getGoogleDomain() {
        return googleDomain;
    }

    public void setGoogleDomain(String googleDomain) {
        this.googleDomain = googleDomain;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    //    @JsonProperty("engine")
//    public String getEngine() { return engine; }
//    @JsonProperty("engine")
//    public void setEngine(String value) { this.engine = value; }
//
//    @JsonProperty("product_id")
//    public String getProductID() { return productID; }
//    @JsonProperty("product_id")
//    public void setProductID(String value) { this.productID = value; }
//
//    @JsonProperty("google_domain")
//    public String getGoogleDomain() { return googleDomain; }
//    @JsonProperty("google_domain")
//    public void setGoogleDomain(String value) { this.googleDomain = value; }
//
//    @JsonProperty("hl")
//    public String getHl() { return hl; }
//    @JsonProperty("hl")
//    public void setHl(String value) { this.hl = value; }
//
//    @JsonProperty("gl")
//    public String getGl() { return gl; }
//    @JsonProperty("gl")
//    public void setGl(String value) { this.gl = value; }
//
//    @JsonProperty("device")
//    public String getDevice() { return device; }
//    @JsonProperty("device")
//    public void setDevice(String value) { this.device = value; }
}

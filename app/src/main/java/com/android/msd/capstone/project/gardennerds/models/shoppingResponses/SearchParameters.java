package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class SearchParameters {
    private String engine;
    private String q;
    private String googleDomain;
    private String hl;
    private String gl;
    private String device;

    @SerializedName("engine")
    public String getEngine() { return engine; }
    @SerializedName("engine")
    public void setEngine(String value) { this.engine = value; }

    @SerializedName("q")
    public String getQ() { return q; }
    @SerializedName("q")
    public void setQ(String value) { this.q = value; }

    @SerializedName("google_domain")
    public String getGoogleDomain() { return googleDomain; }
    @SerializedName("google_domain")
    public void setGoogleDomain(String value) { this.googleDomain = value; }

    @SerializedName("hl")
    public String getHl() { return hl; }
    @SerializedName("hl")
    public void setHl(String value) { this.hl = value; }

    @SerializedName("gl")
    public String getGl() { return gl; }
    @SerializedName("gl")
    public void setGl(String value) { this.gl = value; }

    @SerializedName("device")
    public String getDevice() { return device; }
    @SerializedName("device")
    public void setDevice(String value) { this.device = value; }
}

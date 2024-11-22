package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class Details {
    private String matureHeight;
    private String matureWidth;
    private String growthRate;
    private String botanicalName;
    private String growsWellInZones;

    public String getMatureHeight() {
        return matureHeight;
    }

    public void setMatureHeight(String matureHeight) {
        this.matureHeight = matureHeight;
    }

    public String getMatureWidth() {
        return matureWidth;
    }

    public void setMatureWidth(String matureWidth) {
        this.matureWidth = matureWidth;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public String getBotanicalName() {
        return botanicalName;
    }

    public void setBotanicalName(String botanicalName) {
        this.botanicalName = botanicalName;
    }

    public String getGrowsWellInZones() {
        return growsWellInZones;
    }

    public void setGrowsWellInZones(String growsWellInZones) {
        this.growsWellInZones = growsWellInZones;
    }
}

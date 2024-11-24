package com.android.msd.capstone.project.gardennerds.models;

public class Weather {

    private double temperature;
    private String icon;

    public Weather(double temperature, String icon) {
        this.temperature = temperature;
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }
}

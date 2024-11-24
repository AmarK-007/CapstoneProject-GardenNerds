package com.android.msd.capstone.project.gardennerds.network.response;

import java.util.List;

public class WeatherResponse {
    private Main main;
    private List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }
    }

    public static class Weather {
        private String icon;

        public String getIcon() {
            return icon;
        }
    }
}

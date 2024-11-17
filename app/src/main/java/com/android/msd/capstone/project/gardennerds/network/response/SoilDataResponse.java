package com.android.msd.capstone.project.gardennerds.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoilDataResponse {
    @SerializedName("hours")
    private List<HourData> hours;

    @SerializedName("meta")
    private MetaData meta;

    public List<HourData> getHours() {
        return hours;
    }

    public MetaData getMeta() {
        return meta;
    }

    public static class HourData {
        @SerializedName("soilMoisture")
        private SoilMoisture soilMoisture;

        @SerializedName("soilTemperature")
        private SoilTemperature soilTemperature;

        @SerializedName("time")
        private String time;

        public SoilMoisture getSoilMoisture() {
            return soilMoisture;
        }

        public SoilTemperature getSoilTemperature() {
            return soilTemperature;
        }

        public String getTime() {
            return time;
        }
    }

    public static class SoilMoisture {
        @SerializedName("noaa")
        private double noaa;

        @SerializedName("sg")
        private double sg;

        public double getNoaa() {
            return noaa;
        }

        public double getSg() {
            return sg;
        }
    }

    public static class SoilTemperature {
        @SerializedName("noaa")
        private double noaa;

        @SerializedName("sg")
        private double sg;

        public double getNoaa() {
            return noaa;
        }

        public double getSg() {
            return sg;
        }
    }

    public static class MetaData {
        @SerializedName("cost")
        private int cost;

        @SerializedName("dailyQuota")
        private int dailyQuota;

        @SerializedName("end")
        private String end;

        @SerializedName("lat")
        private double lat;

        @SerializedName("lng")
        private double lng;

        @SerializedName("params")
        private List<String> params;

        @SerializedName("requestCount")
        private int requestCount;

        @SerializedName("start")
        private String start;

        public int getCost() {
            return cost;
        }

        public int getDailyQuota() {
            return dailyQuota;
        }

        public String getEnd() {
            return end;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public List<String> getParams() {
            return params;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public String getStart() {
            return start;
        }
    }
}

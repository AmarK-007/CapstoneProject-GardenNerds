package com.android.msd.capstone.project.gardennerds.utils;

/**
 * Constants class
 */
public class Constants {
    // Database Constants
    public static final String DATABASE_NAME = "gardennerds.db";
    public static final int DATABASE_VERSION = 1;

    //Sensor Constants
    public static final String API_KEY = "063f969c-80c5-11ef-ae24-0242ac130003-063f978c-80c5-11ef-ae24-0242ac130003";

    // Dummy JSON response string
    public static String dummyJsonResponse = "{ \"hours\": ["
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.89, \"sg\": 13.89 }, "
            + "\"time\": \"2024-10-09T00:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.79, \"sg\": 13.79 }, "
            + "\"time\": \"2024-10-09T01:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.69, \"sg\": 13.69 }, "
            + "\"time\": \"2024-10-09T02:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.59, \"sg\": 13.59 }, "
            + "\"time\": \"2024-10-09T03:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.55, \"sg\": 13.55 }, "
            + "\"time\": \"2024-10-09T04:00:00+00:00\" } "
            + "], "
            + "\"meta\": { \"cost\": 1, \"dailyQuota\": 10, \"end\": \"2024-10-09 00:00\", "
            + "\"lat\": 50.714691, \"lng\": 4.3991, "
            + "\"params\": [\"soilMoisture\", \"soilTemperature\"], "
            + "\"requestCount\": 1, \"start\": \"2024-10-09 00:00\" } }";

}

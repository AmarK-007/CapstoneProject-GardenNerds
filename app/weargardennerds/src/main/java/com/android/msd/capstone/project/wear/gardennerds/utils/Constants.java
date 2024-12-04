package com.android.msd.capstone.project.wear.gardennerds.utils;

/**
 * Constants class
 */
public class Constants {
    // Database Constants
    public static final String DATABASE_NAME = "gardenNerds.db";
    public static final int DATABASE_VERSION = 1;

    public static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 1234;
    //Sensor Constants
    public static final String API_KEY = "063f969c-80c5-11ef-ae24-0242ac130003-063f978c-80c5-11ef-ae24-0242ac130003";

    // Dummy JSON response string
    public static String dummyJsonResponse = "{ \"hours\": ["
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.89, \"sg\": 13.89 }, "
            + "\"surfaceTemperature\": { \"noaa\": 10.23, \"sg\": 10.23 }, "
            + "\"time\": \"2024-10-09T00:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.79, \"sg\": 13.79 }, "
            + "\"surfaceTemperature\": { \"noaa\": 10.23, \"sg\": 10.23 }, "
            + "\"time\": \"2024-10-09T01:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.69, \"sg\": 13.69 }, "
            + "\"surfaceTemperature\": { \"noaa\": 10.23, \"sg\": 10.23 }, "
            + "\"time\": \"2024-10-09T02:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.59, \"sg\": 13.59 }, "
            + "\"surfaceTemperature\": { \"noaa\": 10.23, \"sg\": 10.23 }, "
            + "\"time\": \"2024-10-09T03:00:00+00:00\" }, "
            + "{ \"soilMoisture\": { \"noaa\": 0.24, \"sg\": 0.24 }, "
            + "\"soilTemperature\": { \"noaa\": 13.55, \"sg\": 13.55 }, "
            + "\"surfaceTemperature\": { \"noaa\": 10.23, \"sg\": 10.23 }, "
            + "\"time\": \"2024-10-09T04:00:00+00:00\" } "
            + "], "
            + "\"meta\": { \"cost\": 1, \"dailyQuota\": 10, \"end\": \"2024-10-09 00:00\", "
            + "\"lat\": 50.714691, \"lng\": 4.3991, "
            + "\"params\": [\"soilMoisture\", \"soilTemperature\"], "
            + "\"requestCount\": 1, \"start\": \"2024-10-09 00:00\" } }";


    // Constants for Reminder Types
    public static final int REMINDER_TYPE_WATER = 1;
    public static final int REMINDER_TYPE_FERTILIZE = 2;
    public static final int REMINDER_TYPE_SUNLIGHT = 3;
    public static final int REMINDER_TYPE_CHANGE_SOIL = 4;

    // Constants for Reminder Frequency
    public static final int SET_REMINDER_SNOOZE = 10;


    //    public static final String apiKey = "525f949b49a6440982fa7faade39d1757d915944f05df7776cc224e50f96b054";
    public static final String apiKey = "0e6be35624704b0d35d9a1fe79c6bf2898255c194dddd1a5bc59243a84995dec";

    public static final int REQUEST_CODE_SCHEDULE_EXACT_ALARM = 1;




}

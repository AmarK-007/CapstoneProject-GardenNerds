package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import java.io.IOException;

public enum Delivery {
    FREE_2_DAY, FREE_3_DAY, FREE_BY_1015, FREE_BY_1016, FREE_BY_1018, FREE_DELIVERY;

//    @JsonValue
    public String toValue() {
        switch (this) {
            case FREE_2_DAY: return "Free 2-day";
            case FREE_3_DAY: return "Free 3-day";
            case FREE_BY_1015: return "Free by 10/15";
            case FREE_BY_1016: return "Free by 10/16";
            case FREE_BY_1018: return "Free by 10/18";
            case FREE_DELIVERY: return "Free delivery";
        }
        return null;
    }

//    @JsonCreator
    public static Delivery forValue(String value) throws IOException {
        if (value.equals("Free 2-day")) return FREE_2_DAY;
        if (value.equals("Free 3-day")) return FREE_3_DAY;
        if (value.equals("Free by 10/15")) return FREE_BY_1015;
        if (value.equals("Free by 10/16")) return FREE_BY_1016;
        if (value.equals("Free by 10/18")) return FREE_BY_1018;
        if (value.equals("Free delivery")) return FREE_DELIVERY;
        throw new IOException("Cannot deserialize Delivery");
    }
}

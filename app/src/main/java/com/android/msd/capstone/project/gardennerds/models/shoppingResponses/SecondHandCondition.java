package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import java.io.IOException;

public enum SecondHandCondition {
    PRE_OWNED, REFURBISHED;

//    @JsonValue
    public String toValue() {
        switch (this) {
            case PRE_OWNED: return "pre-owned";
            case REFURBISHED: return "refurbished";
        }
        return null;
    }

//    @JsonCreator
    public static SecondHandCondition forValue(String value) throws IOException {
        if (value.equals("pre-owned")) return PRE_OWNED;
        if (value.equals("refurbished")) return REFURBISHED;
        throw new IOException("Cannot deserialize SecondHandCondition");
    }
}

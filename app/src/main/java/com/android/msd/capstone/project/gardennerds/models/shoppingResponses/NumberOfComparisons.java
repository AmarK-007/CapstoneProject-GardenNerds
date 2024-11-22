package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import java.io.IOException;

public enum NumberOfComparisons {
    MORE;

//    @JsonValue
    public String toValue() {
        switch (this) {
            case MORE: return "& more";
        }
        return null;
    }
//
//    @JsonCreator
    public static NumberOfComparisons forValue(String value) throws IOException {
        if (value.equals("& more")) return MORE;
        throw new IOException("Cannot deserialize NumberOfComparisons");
    }
}

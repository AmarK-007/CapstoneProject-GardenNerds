package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;

import com.google.gson.annotations.SerializedName;

public class Filter {
    private Option[] options;
    private String type;

    @SerializedName("options")
    public Option[] getOptions() { return options; }
    @SerializedName("options")
    public void setOptions(Option[] value) { this.options = value; }

    @SerializedName("type")
    public String getType() { return type; }
    @SerializedName("type")
    public void setType(String value) { this.type = value; }
}

package com.android.msd.capstone.project.gardennerds.models.productResponses;

public class AdditionalPrice {
    private String shipping;
    private String tax;

    public AdditionalPrice(String shipping, String tax) {
        this.shipping = shipping;
        this.tax = tax;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    //    @JsonProperty("shipping")
//    public String getShipping() { return shipping; }
//    @JsonProperty("shipping")
//    public void setShipping(String value) { this.shipping = value; }
//
//    @JsonProperty("tax")
//    public String getTax() { return tax; }
//    @JsonProperty("tax")
//    public void setTax(String value) { this.tax = value; }
}

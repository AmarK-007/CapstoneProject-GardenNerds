package com.android.msd.capstone.project.gardennerds.models.productResponses;

import java.util.ArrayList;

public class RelatedProduct {
    public ArrayList<DifferentBrand> different_brand;

    public ArrayList<DifferentBrand> getDifferent_brand() {
        return different_brand;
    }

    public void setDifferent_brand(ArrayList<DifferentBrand> different_brand) {
        this.different_brand = different_brand;
    }

    public RelatedProduct(ArrayList<DifferentBrand> different_brand) {
        this.different_brand = different_brand;
    }
}

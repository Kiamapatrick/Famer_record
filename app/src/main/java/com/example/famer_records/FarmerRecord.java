package com.example.famer_records;

import java.io.Serializable;

public class FarmerRecord implements Serializable {
    private final String farmerRef;
    private final String crop;
    private final double quantityKg;

    public FarmerRecord(String farmerRef, String crop, double quantityKg) {
        this.farmerRef = farmerRef;
        this.crop = crop;
        this.quantityKg = quantityKg;
    }

    public String getFarmerRef() {
        return farmerRef;
    }

    public String getCrop() {
        return crop;
    }

    public double getQuantityKg() {
        return quantityKg;
    }

    @Override
    public String toString() {
        return farmerRef + " - " + crop + " - " + quantityKg + " kg";
    }
}
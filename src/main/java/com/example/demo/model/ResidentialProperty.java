package com.example.demo.model;

import com.example.demo.stric.PetFriendlyLevel;

public class ResidentialProperty extends Property {
    int bedrooms;
    boolean hasGarden;
    int propertyId;
    String petFriendlyLevel;

    public ResidentialProperty(int ownerId, float pricing, int propertyId, String address, String status, int bedrooms, boolean hasGarden, String petFriendlyLevel) {
        super(ownerId, pricing, propertyId, address, status);
        this.propertyId = propertyId;
        this.bedrooms = bedrooms;
        this.hasGarden = hasGarden;
        this.petFriendlyLevel = petFriendlyLevel;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPetFriendlyLevel() {
        return petFriendlyLevel;
    }

    public void setPetFriendlyLevel(String petFriendlyLevel) {
        this.petFriendlyLevel = petFriendlyLevel;
    }
    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "bedrooms=" + bedrooms +
                ", hasGarden=" + hasGarden +
                ", propertyId=" + propertyId +
                ", isPetFriendly='" + petFriendlyLevel + '\'' +
                '}';
    }

}

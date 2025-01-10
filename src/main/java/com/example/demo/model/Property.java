package com.example.demo.model;

public class Property {
    int ownerId;
    float pricing;
    int propertyId;
    String address;
    String status;

    public Property(int ownerId, float pricing, int propertyId, String address, String status) {
        this.ownerId = ownerId;
        this.pricing = pricing;
        this.propertyId = propertyId;
        this.address = address;
        this.status = status;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Property{" +
                "ownerId=" + ownerId +
                ", pricing=" + pricing +
                ", propertyId=" + propertyId +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}

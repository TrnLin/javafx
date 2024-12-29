package com.example.demo.model;

public class CommercialProperty extends Property {
    int parkingSpace;
    int propertyId;
    int squareFootage;
    String businessType;

    public CommercialProperty(int ownerId, float pricing, int propertyId, String address, String status, int parkingSpace, int squareFootage, String businessType) {
        super(ownerId, pricing, propertyId, address, status); // Initialize fields in the superclass
        this.propertyId = propertyId;
        this.parkingSpace = parkingSpace;
        this.squareFootage = squareFootage;
        this.businessType = businessType;
    }

    public int getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(int parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "CommercialProperty{" +

                "parkingSpace=" + parkingSpace +
                ", propertyId=" + propertyId +
                ", squareFootage=" + squareFootage +
                ", businessType='" + businessType + '\'' +
                '}';
    }
}

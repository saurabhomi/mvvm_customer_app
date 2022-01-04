package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageRequest {

    @Expose
    @SerializedName("City")
    private String city;

    @Expose
    @SerializedName("passengerId")
    private String passenger_id;

    @Expose
    @SerializedName("PassengerType")
    private String Passenger_type;

    @Expose
    @SerializedName("BookingType")
    private String BookingType;

    @Expose
    @SerializedName("pickUpDateTime")
    private String pickUpDateTime;

    @Expose
    @SerializedName("CustomerID")
    private String CustomerID;



    @Expose
    @SerializedName("dropOffDateTime")
    private String dropOffDateTime;


    public PackageRequest setCity(String city) {
        this.city = city;
        return this;
    }

    public PackageRequest setPassengerId(String passenger_id) {
        this.passenger_id = passenger_id;
        return this;
    }

    public PackageRequest setPassengerType(String passenger_type) {
        Passenger_type = passenger_type;
        return this;
    }

    public PackageRequest setBookingType(String bookingType) {
        BookingType = bookingType;
        return this;
    }

    public String getPickUpDateTime() {
        return pickUpDateTime;
    }

    public PackageRequest setPickUpDateTime(String pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
        return this;
    }

    public String getDropOffDateTime() {
        return dropOffDateTime;
    }

    public PackageRequest setDropOffDateTime(String dropOffDateTime) {
        this.dropOffDateTime = dropOffDateTime;
        return this;
    }

    public PackageRequest setCustomerID(String customerID) {
        CustomerID = customerID;
        return this;
    }


}

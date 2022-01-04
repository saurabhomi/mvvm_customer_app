package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryStateCityRequest {

    public CountryStateCityRequest setCountry(String country) {
        this.country = country;
        return this;
    }

    public CountryStateCityRequest setState(String state) {
        this.state = state;
        return this;
    }

    public CountryStateCityRequest setPassengerType(String passengerType) {
        PassengerType= passengerType;
        return this;
    }

    public CountryStateCityRequest setPackageType(String packageType) {
        PackageType = packageType;
        return this;
    }

    public CountryStateCityRequest setPassengerId(String passengerId) {
        this.passengerId = passengerId;
        return this;
    }

    @Expose
    @SerializedName("CountryName")
    public String country;

    @Expose
    @SerializedName("State")
    private String state;

    @Expose
    @SerializedName("PassengerType")
    private String PassengerType;

    @Expose
    @SerializedName("PackageType")
    private String PackageType;

    @Expose
    @SerializedName("passengerId")
    private String passengerId;

}

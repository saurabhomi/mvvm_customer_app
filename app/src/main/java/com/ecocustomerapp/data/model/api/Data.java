package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getPassenger_type() {
        return passenger_type;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getCountry() {
        return country;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName(value = "name", alternate = {"CityName"})
    private String name;


    @Expose
    @SerializedName("lat")
    private String lat;

    @Expose
    @SerializedName("lon")
    private String lon;

    @Expose
    @SerializedName("PassengerType")
    private String passenger_type;

    @Expose
    @SerializedName("PackageType")
    private String package_type;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("passengerId")
    private String passenger_id;

    public String getCityGeoName() {
        return CityGeoName;
    }

    public Data setCityGeoName(String cityGeoName) {
        CityGeoName = cityGeoName;
        return this;
    }

    @Expose
    @SerializedName("CityGeoName")
    private String CityGeoName;

    public Data setName(String name) {
        this.name = name;
        return this;
    }
}

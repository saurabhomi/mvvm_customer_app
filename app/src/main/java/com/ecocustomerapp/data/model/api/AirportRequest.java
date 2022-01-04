package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportRequest {
    @Expose
    @SerializedName("city")
    private String city;

    public AirportRequest setCity(String city) {
        this.city = city;
        return this;
    }
}

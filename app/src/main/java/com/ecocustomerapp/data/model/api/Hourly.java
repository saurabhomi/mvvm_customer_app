package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hourly implements Serializable {

    public String getHourlyPackage() {
        return hourlyPackage;
    }

    public Hourly setHourlyPackage(String hourlyPackage) {
        this.hourlyPackage = hourlyPackage;
        return this;
    }

    @Expose
    @SerializedName("hourly_package")
    public String hourlyPackage;
}

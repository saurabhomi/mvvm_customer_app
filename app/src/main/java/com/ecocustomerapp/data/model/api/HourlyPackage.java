package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyPackage {
    public String getName() {
        return name;
    }

    public HourlyPackage setName(String name) {
        this.name = name;
        return this;
    }

    @Expose
    @SerializedName("hourly_package")
    private String name;
}

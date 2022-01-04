package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HourlyPackageResponse extends BaseModel implements Serializable {

    @Expose
    @SerializedName("hourly")
    private List<HourlyPackage> hourlyPackages;

    public List<HourlyPackage> getHourlyPackages() {
        return hourlyPackages;
    }
}

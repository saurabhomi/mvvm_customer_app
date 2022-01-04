package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverData extends BaseModel {
    @Expose
    @SerializedName("DriverDetails")
    private DriverDetails driverDetails;

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }
}

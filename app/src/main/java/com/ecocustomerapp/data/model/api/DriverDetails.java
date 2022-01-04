package com.ecocustomerapp.data.model.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DriverDetails {

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCar_no() {
        return car_no;
    }

    public String getMobile() {
        return mobile;
    }

    public String getType() {
        return type;
    }

    @Expose
    @SerializedName("DriverName")
    private String name;
    @Expose
    @SerializedName("DriverImage")
    private String image;
    @Expose
    @SerializedName("CarNo")
    private String car_no;
    @Expose
    @SerializedName("DriverMobile")
    private String mobile;
    @Expose
    @SerializedName("CarType")
    private String type;
}

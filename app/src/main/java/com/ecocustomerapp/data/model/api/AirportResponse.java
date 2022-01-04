package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportResponse extends BaseModel {

    @Expose
    @SerializedName("name")
    private String name;


    @Expose
    @SerializedName("id")
    private String id;


    @Expose
    @SerializedName("city")
    private String city;

    @Expose
    @SerializedName("code")
    private String code;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }
}

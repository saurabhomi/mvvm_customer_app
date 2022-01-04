package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TerminalData {


    @Expose
    @SerializedName("name")
    String name;

    @Expose
    @SerializedName("lat")
    String lat;

    @Expose
    @SerializedName("lon")
    String lon;

    @Expose
    @SerializedName("terminalName")
    String terminalName;

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getTerminalName() {
        return terminalName;
    }
}

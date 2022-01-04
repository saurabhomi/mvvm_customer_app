package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListBookings extends BaseModel {
    public List<Booking> getUpcoming() {
        return upcoming != null ? upcoming : new ArrayList<Booking>();
    }

    public List<Booking> getComplete() {
        return past != null ? past : new ArrayList<Booking>();
    }

    @Expose
    @SerializedName("UpcomingBookings")
    private List<Booking> upcoming;

    @Expose
    @SerializedName("PastBookings")
    private List<Booking> past;
}

package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingResponse extends BaseModel implements Serializable {
    public Booking getBooking() {
        return booking;
    }

    @Expose
    @SerializedName("Booking")
    private Booking booking;
}

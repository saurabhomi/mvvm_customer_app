package com.ecocustomerapp.data.model.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtpData implements Serializable {

    public String getOtp_mobile() {
        return otp_mobile;
    }

    public String getOtp_email() {
        return otp_email;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    @Expose
    @SerializedName("OTPMobile")
    private String otp_mobile;

    @Expose
    @SerializedName("OTPEmail")
    private String otp_email;

    @Expose
    @SerializedName("Email")
    private String email;

    @Expose
    @SerializedName("Mobile")
    private String mobile;


    public String getPassengerId() {
        return passengerId;
    }

    public String getPassengerType() {
        return passengerType;
    }

    @Expose
    @SerializedName("PassengerID")
    private String passengerId;

    @Expose
    @SerializedName("passengerType")
    private String passengerType;

}

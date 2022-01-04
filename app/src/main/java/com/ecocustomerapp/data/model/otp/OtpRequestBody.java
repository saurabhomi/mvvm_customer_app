package com.ecocustomerapp.data.model.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequestBody {

    @Expose
    @SerializedName("Mobile")
    private String mobile;

    public OtpRequestBody setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Expose
    @SerializedName("Email")
    private String email;

    public OtpRequestBody setEmail(String email) {
        this.email = email;
        return this;
    }

    @Expose
    @SerializedName("PassengerType")
    private String customerType;

    public OtpRequestBody setCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }
}

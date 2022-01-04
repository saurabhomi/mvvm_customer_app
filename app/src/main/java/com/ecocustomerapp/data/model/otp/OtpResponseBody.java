package com.ecocustomerapp.data.model.otp;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponseBody extends BaseModel {

    public OtpData getData() {
        return data;
    }

    @Expose
    @SerializedName("")
    private OtpData data;


}

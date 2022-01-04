package com.ecocustomerapp.data.model.Login;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saurabh Srivastava on 11/11/19
 */
public class LoginResponseData extends BaseModel {
    @Expose
    @SerializedName(value = "", alternate = "Passenger")
    private LoginBody passenger;

    public LoginBody getPassenger() {
        return passenger;
    }
}

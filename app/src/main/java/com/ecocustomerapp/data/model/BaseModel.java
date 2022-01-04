package com.ecocustomerapp.data.model;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Saurabh Srivastava on 11/9/19
 */
public class BaseModel implements Serializable {
    public String getRequestKey() {
        return requestKey;
    }

    public String getMessage() {
        return message != null ? message : "Something went wrong!";
    }

    public boolean getResponseStatus() {
        if (status == null || (status.trim().equalsIgnoreCase("")) || !status.equalsIgnoreCase("Success")) {
            throw new Error(getMessage());
        } else {
            return status.equalsIgnoreCase("Success");
        }

    }

    public String getStatus() {
        return status;
    }

    public void setRequestKey(String request_key) {
        this.requestKey = request_key;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Ignore
    @Expose
    @SerializedName("RequestKey")
    private String requestKey;

    @Ignore
    @Expose
    @SerializedName("Message")
    private String message;

    @Ignore
    @Expose
    @SerializedName("Status")
    private String status;

    @Ignore
    @Expose
    @SerializedName("CalenderBlockTime")
    private String blockTime;

    public String getBlockTime() {
        return blockTime;
    }
}

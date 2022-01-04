package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class Registration implements Serializable {

    public String getGuest_name() {
        return guest_name;
    }

    public Registration setGuest_name(String guest_name) {
        this.guest_name = guest_name;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public Registration setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Registration setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Registration setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Registration setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPushToken() {
        return pushToken;
    }

    public Registration setPushToken(String pushToken) {
        this.pushToken = pushToken;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Registration setToken(String token) {
        this.token = token;
        return this;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public Registration setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
        return this;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public Registration setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Registration setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Registration setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public Registration setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Registration setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerType() {
        return customerType;
    }

    public Registration setCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    @Expose
    @SerializedName("GuestName")
    public String guest_name;

    @Expose
    @SerializedName("DeviceType")
    public String deviceType;

    @Expose
    @SerializedName("Mobile")
    public String mobile;

    @Expose
    @SerializedName("Email")
    public String email;

    @Expose
    @SerializedName("Password")
    public String password;

    @Expose
    @SerializedName("PushToken")
    public String pushToken;

    @Expose
    @SerializedName("Token")
    public String token;

    @Expose
    @SerializedName("GuestFirstName")
    public String guestFirstName;

    @Expose
    @SerializedName("GuestLastName")
    public String guestLastName;

    @Expose
    @SerializedName("Gender")
    public String gender;

    @Expose
    @SerializedName("DeviceId")
    public String deviceId;

    @Expose
    @SerializedName("AppVersion")
    public String appVersion;

    @Expose
    @SerializedName("CustomerID")
    public String customerId;

    @Expose
    @SerializedName("CustomerType")
    public String customerType;

    public String getMobileOtp() {
        return mobileOtp;
    }

    public Registration setMobileOtp(String mobileOtp) {
        this.mobileOtp = mobileOtp;
        return this;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public Registration setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
        return this;
    }

    @SerializedName("mobileOtp")
    public String mobileOtp;

    @SerializedName("emailOtp")
    public String emailOtp;

    @SerializedName("confirm_password")
    public String confirmPassword;

    public boolean isCorporate() {
        return customerType.equals("Corporate");
    }

}
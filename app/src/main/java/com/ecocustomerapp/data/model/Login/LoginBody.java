package com.ecocustomerapp.data.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Saurabh Srivastava on 11/11/19
 */
public class LoginBody {

    public String getEmail() {
        return email;
    }

    public LoginBody setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginBody setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getId() {
        return id;
    }

    public LoginBody setId(String id) {
        this.id = id;
        return this;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public LoginBody setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
        return this;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public LoginBody setGuest_name(String guest_name) {
        this.guest_name = guest_name;
        return this;
    }

    public String getDevice_type() {
        return device_type;
    }

    public LoginBody setDevice_type(String device_type) {
        this.device_type = device_type;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public LoginBody setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public LoginBody setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getDevice_model() {
        return device_model;
    }

    public LoginBody setDevice_model(String device_model) {
        this.device_model = device_model;
        return this;
    }

    public String getPush_token() {
        return push_token;
    }

    public LoginBody setPush_token(String push_token) {
        this.push_token = push_token;
        return this;
    }

    public String getApp_version() {
        return app_version;
    }

    public LoginBody setApp_version(String app_version) {
        this.app_version = app_version;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginBody setToken(String token) {
        this.token = token;
        return this;
    }


    @Expose
    @SerializedName("Email")

    private String email;
    @Expose
    @SerializedName("Password")

    private String password;
    @Expose
    @SerializedName("ID")

    private String id;
    @Expose
    @SerializedName("CustomerID")

    private String customer_id;
    @Expose
    @SerializedName("GuestName")
    private String guest_name;

    @Expose
    @SerializedName("GuestFirstName")
    private String guest_first_name;

    @Expose
    @SerializedName("GuestLastName")
    private String guest_last_name;

    @Expose
    @SerializedName("DeviceType")
    private String device_type;

    @Expose
    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("DeviceId")
    private String device_id;

    @SerializedName("DeviceModel")
    private String device_model;

    @Expose
    @SerializedName("PushToken")
    private String push_token;

    @SerializedName("AppVersion")

    private String app_version;

    @Expose
    @SerializedName("Token")
    private String token;

    @SerializedName("countryCode")
    private String country_code;

    @Expose
    @SerializedName("CustomerType")
    private String customer_type;

    @Expose
    @SerializedName("Gender")
    private String gender;

    public List<String> getModeOfPayment() {
        return modeOfPayment==null?new ArrayList<String>(Collections.emptyList()):modeOfPayment;
    }

    @Expose
    @SerializedName("ModeofPayment")
    private List<String> modeOfPayment;

    @Expose
    @SerializedName("ChangePassword")
    private String changePassword;

    public String getGuest_first_name() {
        return guest_first_name;
    }

    public boolean getChangePassword() {
        return changePassword != null && changePassword.equals("Yes");
    }

    public LoginBody setChangePassword(String changePassword) {
        this.changePassword = changePassword;
        return this;
    }

    public LoginBody setGuest_first_name(String guest_first_name) {
        this.guest_first_name = guest_first_name;
        return this;
    }

    public String getGuest_last_name() {
        return guest_last_name;
    }

    public LoginBody setGuest_last_name(String guest_last_name) {
        this.guest_last_name = guest_last_name;
        return this;
    }

    public String getCountry_code() {
        return country_code;
    }

    public LoginBody setCountry_code(String country_code) {
        this.country_code = country_code;
        return this;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public LoginBody setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public LoginBody setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getBooker_id() {
        return booker_id;
    }

    public LoginBody setBooker_id(String booker_id) {
        this.booker_id = booker_id;
        return this;
    }

    @Expose
    @SerializedName("BookerId")
    private String booker_id;

    public String getShowCaseCode() {
        return showCaseCode;
    }

    public LoginBody setShowCaseCode(String showCaseCode) {
        this.showCaseCode = showCaseCode;
        return this;
    }

    public String getCaseCodeName() {
        return CaseCodeName;
    }

    public LoginBody setCaseCodeName(String caseCodeName) {
        CaseCodeName = caseCodeName;
        return this;
    }

    @Expose
    @SerializedName("showCaseCode")
    private String showCaseCode;

    @Expose
    @SerializedName("CaseCodeName")
    private String CaseCodeName;


}
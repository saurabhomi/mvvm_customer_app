package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Package implements Serializable {

    @Expose
    @SerializedName("Car")
    private String car;

    @Expose
    @SerializedName("Car_category")
    private String car_category;

    @Expose
    @SerializedName("PackageType")
    private String package_type;

    @Expose
    @SerializedName("Rate")
    private String rate;

    @Expose
    @SerializedName("Extra_Km_Rate")
    private String extra_km_rate;

    public String getExtra_hr_Rate() {
        return Extra_hr_Rate;
    }

    public Package setExtra_hr_Rate(String extra_hr_Rate) {
        Extra_hr_Rate = extra_hr_Rate;
        return this;
    }

    @Expose
    @SerializedName("Extra_hr_Rate")
    private String Extra_hr_Rate;

    @Expose
    @SerializedName("sittingCapacity")
    private String sitting_capacity;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("minKM")
    private String minKM;

    @Expose
    @SerializedName("nightCharges")
    private String nightCharges;

    public String getCar() {
        return car;
    }

    public String getCar_category() {
        return car_category;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getRate() {
        return rate.equals("0.0")?"":rate;
    }

    public String perDay(){
        return getMinKM()+" Kms included";
    }
    public String extraPerKm(){
        return getExtra_km_rate() + "/km after "+ getMinKM()+" kms";
    }

    public int rate() {
        return Integer.parseInt(new DecimalFormat("#").format(Double.parseDouble(rate)));
    }

    public String getExtra_km_rate() {
        return extra_km_rate;
    }

    public String getSitting_capacity() {
        return sitting_capacity;
    }

    public String getSittingCapacity() {
        return sitting_capacity + " + Driver | 2 Bags";
    }

    public String getImage() {
        return image;
    }


    public String getMinKM() {
        return minKM;
    }

    public Package setMinKM(String minKM) {
        this.minKM = minKM;
        return this;
    }

    public String getNightCharges() {
        return nightCharges;
    }

    public Package setNightCharges(String nightCharges) {
        this.nightCharges = nightCharges;
        return this;
    }

}

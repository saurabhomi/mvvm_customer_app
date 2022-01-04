package com.ecocustomerapp.data.model.api;

import android.icu.text.CaseMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {


    @Expose
    @SerializedName(value = "customerId", alternate = {"Customer_Id"})
    private String customerId;
    @Expose
    @SerializedName("salutation")
    private String salutation;
    @Expose
    @SerializedName("emailAddress")
    private String emailAddress;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName(value = "city", alternate = {"pickUpCity"})
    private String city;
    @Expose
    @SerializedName("pickUpDateTime")
    private String pickUpDateTime;
    @Expose
    @SerializedName("carType")
    private String carType;
    @Expose
    @SerializedName("packageType")
    private String packageType;

    @Expose
    @SerializedName("PassengerType")
    private String passengerType;
    @Expose
    @SerializedName("Picklocationlat")
    private String Picklocationlat;
    @Expose
    @SerializedName("Picklocationlon")
    private String Picklocationlon;
    @Expose
    @SerializedName("Droplocationlat")
    private String Droplocationlat;
    @Expose
    @SerializedName("Droplocationlon")
    private String Droplocationlon;
    @Expose
    @SerializedName("passengerId")
    private String passengerId;
    @Expose
    @SerializedName("pickUpAddress")
    private String pickUpAddress;
    @Expose
    @SerializedName("dropAddress")
    private String dropAddress;
    @Expose
    @SerializedName("bookingNo")
    private String bookingNo;

    @Expose
    @SerializedName("specialInstruction")
    private String specialInstruction;

    public String getCaseCode() {
        return CaseCode;
    }

    @Expose
    @SerializedName("CaseCode")
    private String CaseCode;

    @Expose
    @SerializedName("hourly_package")
    private String hourly_package;

    private boolean enable = true;

    public String getCustomerId() {
        return customerId;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCity() {
        if (city.contains(",")) {
            return city.split(",")[0];
        } else {
            return city;
        }

    }

    public String getPickUpDateTime() {
        return pickUpDateTime;
    }

    public String getCarType() {
        return carType;
    }

    public String getPackageType() {
        return packageType;
    }

    public String getPicklocationlat() {
        return Picklocationlat;
    }

    public String getPicklocationlon() {
        return Picklocationlon;
    }

    public String getDroplocationlat() {
        return Droplocationlat;
    }

    public String getDroplocationlon() {
        return Droplocationlon;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public String getDropAddress() {
        if (dropAddress.contains(",")) {
            return !dropAddress.isEmpty() ? dropAddress.split(",")[0] : "N/A";
        } else {
            return !dropAddress.isEmpty() ? dropAddress : "N/A";
        }

    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getDropCity() {
        if (dropCity == null || dropCity.isEmpty()) {
            return getDropAddress();
        }
        if (dropCity.contains(",")) {
            return dropCity.split(",")[0];
        } else {
            return dropCity;
        }

    }

    public void setDropCity(String dropCity) {
        this.dropCity = dropCity;
    }

    @Expose
    @SerializedName("dropCity")
    private String dropCity;

    public String getStatus() {
        return status;
    }

    public String getBookingId() {
        return "Booking Number: " + bookingNo;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public String getPickUpCityWithPackage() {
        return city.split(",")[0] + " > " + packageType + " (" + hourly_package + ")";
    }

    public String getServicePackage() {
        String value;
        switch (packageType) {
            case "Local":
                value = packageType + " (" + hourly_package + ")";
                break;
            case "Airport":
                value = "Airport Transfer" + " (" + hourly_package + ")";
                break;
            case "Outstation":
                value = "Outstation" + " (" + getCity() + ">>" + getDropCity() + ">>" + getCity() + ")";
                break;
            default:
                value = "";
                break;

        }
        return value;
    }


    public String getCityWithPickupAddress() {
        if (city.contains(",")) {
            return city.split(",")[0] + "\n" + pickUpAddress;
        } else {
            return city + "\n" + pickUpAddress;
        }

    }

    public String getCityWithDropAddress() {
        if (dropCity.contains(",") && packageType.equals("Outstation")) {
            return dropCity.split(",")[0] + "\n" + dropAddress;
        } else if (packageType.equals("Outstation")) {
            return dropCity + "\n" + dropAddress;
        } else {
            return dropAddress;
        }

    }

    public String getHourlyPackage() {
        return hourly_package;
    }

    public Booking setHourlyPackage(String hourly_package) {
        this.hourly_package = hourly_package;
        return this;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public boolean isEnable() {
        return enable;
    }

    public Booking setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getPaymentMode() {
        return "Bill to company";
    }

    public String getTraveller() {
        return salutation + "\n" + mobile + "\n" + emailAddress;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

}

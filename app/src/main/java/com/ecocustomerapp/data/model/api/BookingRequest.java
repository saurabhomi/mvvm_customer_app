package com.ecocustomerapp.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class BookingRequest implements Serializable {

    @Expose
    @SerializedName("customerId")
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
    @SerializedName("pickUpCity")
    private String pickUpCity;

    @Expose
    @SerializedName("pickUpDateTime")
    private String pickUpDateTime;

    @Expose
    @SerializedName("dropOffDateTime")
    private String dropOffDateTime;

    @Expose
    @SerializedName("carType")
    private String carType;

    @Expose
    @SerializedName("packageType")
    private String packageType;

    @Expose
    @SerializedName("picklocationlat")
    private String picklocationlat;

    @Expose
    @SerializedName("picklocationlon")
    private String picklocationlon;

    @Expose
    @SerializedName("droplocationlat")
    private String droplocationlat;

    @Expose
    @SerializedName("droplocationlon")
    private String droplocationlon;

    @Expose
    @SerializedName("passengerId")
    private int passengerId;

    @Expose
    @SerializedName("CaseCode")
    private String caseCode;

    @Expose
    @SerializedName("dropCity")
    private String dropCity;

    @Expose
    @SerializedName("pickUpAddress")
    private String pickUpAddress;

    @Expose
    @SerializedName("dropAddress")
    private String dropAddress;

    @Expose
    @SerializedName("SomeoneElse")
    private String SomeoneElse;

    @Expose
    @SerializedName("Flightdetails")
    private String flightDetails;

    @Expose
    @SerializedName("passengerType")
    private String passengerType;

    @Expose
    @SerializedName("BookerId")
    private String BookerId;

    public BookingRequest setHourlyPackage(String hourlyPackage) {
        this.hourlyPackage = hourlyPackage;
        return this;
    }

    @Expose
    @SerializedName("hourly_package")
    private String hourlyPackage;

    public BookingRequest setName(String name) {
        this.name = name;
        return this;
    }

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("TripT0")
    private String tripTo;

    public BookingRequest setTripTo(String tripTo) {
        this.tripTo = tripTo;
        return this;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public BookingRequest setPassengerType(String passengerType) {
        this.passengerType = passengerType;
        return this;
    }

    public String getSomeoneElse() {
        return SomeoneElse;
    }

    public boolean forElse() {
        return SomeoneElse!=null && SomeoneElse.equals("Yes");
    }

    public BookingRequest setSomeoneElse(String someoneElse) {
        SomeoneElse = someoneElse;
        return this;
    }

    public String getFlightDetails() {
        return flightDetails;
    }

    public BookingRequest setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
        return this;
    }

    private String bookingType;

    private int tabPosition;

    private String entity;

    private String city;

    private String CityGeoName;

    private String localPackage;

    private String tripType;

    private String origin_address;

    private String origin_point;

    private String destination_address;

    private String destination_point;

    private String city_terminal;

    private String date;

    private String time;

    private boolean oneway;

    private String else_name;

    private String else_email;

    private String else_mobile;

    private long calendarTime;

    public String getElse_name() {
        return else_name == null ? "" : else_name;
    }

    public BookingRequest setElse_name(String else_name) {
        this.else_name = else_name;
        return this;
    }

    public String getElse_email() {
        return else_email == null ? "" : else_email;
    }

    public BookingRequest setElse_email(String else_email) {
        this.else_email = else_email;
        return this;
    }

    public String getElse_mobile() {
        return else_mobile == null ? "" : else_mobile;
    }

    public BookingRequest setElse_mobile(String else_mobile) {
        this.else_mobile = else_mobile;
        return this;
    }


    public String getLocalPackage() {
        return localPackage;
    }

    public void setLocalPackage(String localPackage) {
        this.localPackage = localPackage;
    }


    public String getCityGeoName() {
        return CityGeoName;
    }

    public BookingRequest setCityGeoName(String cityGeoName) {
        CityGeoName = cityGeoName;
        return this;
    }


    public String getOrigin_point() {
        return origin_point == null ? "" : origin_point+" ,";
    }

    public BookingRequest setOrigin_point(String origin_point) {
        this.origin_point = origin_point;
        return this;
    }

    public String getDestination_address() {
        return destination_address==null?"":destination_address;
    }

    public BookingRequest setDestination_address(String destination_address) {
        this.destination_address = destination_address;
        return this;
    }

    public String getDestination_point() {
        return destination_point == null ? "" : destination_point+" ,";
    }

    public BookingRequest setDestination_point(String destination_point) {
        this.destination_point = destination_point;
        return this;
    }


    public String getDate() {
        return date;
    }


    public String getFlight_details() {
        return flight_details;
    }

    public BookingRequest setFlight_details(String flight_details) {
        this.flight_details = flight_details;
        return this;
    }

    private String flight_details;

    public BookingRequest setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getSalutation() {
        return else_name==null|| else_name.isEmpty()?salutation:else_name;
    }

    public BookingRequest setSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public String getEmailAddress() {
        return else_email==null|| else_email.isEmpty()?emailAddress:else_email;
    }

    public BookingRequest setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public String getMobile() {
        return else_mobile==null|| else_mobile.isEmpty()?mobile:else_mobile;
    }

    public BookingRequest setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public BookingRequest setPickUpCity(String pickUpCity) {
        this.pickUpCity = pickUpCity;
        return this;
    }

    public String getPickUpCity() {
        return pickUpCity == null ? "" : pickUpCity;
    }

    public BookingRequest setPickUpDateTime(String pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
        return this;
    }

    public BookingRequest setCarType(String carType) {
        this.carType = carType;
        return this;
    }

    public BookingRequest setPackageType(String packageType) {
        this.packageType = packageType;
        return this;
    }

    public BookingRequest setPicklocationlat(String picklocationlat) {
        this.picklocationlat = picklocationlat;
        return this;
    }

    public BookingRequest setPicklocationlon(String picklocationlon) {
        this.picklocationlon = picklocationlon;
        return this;
    }

    public BookingRequest setDroplocationlat(String droplocationlat) {
        this.droplocationlat = droplocationlat;
        return this;
    }

    public BookingRequest setDroplocationlon(String droplocationlon) {
        this.droplocationlon = droplocationlon;
        return this;
    }

    public BookingRequest setPassengerId(String passengerId) {
        this.passengerId = Integer.parseInt(passengerId);
        return this;
    }

    public BookingRequest setCaseCode(String caseCode) {
        this.caseCode = caseCode;
        return this;
    }

    public BookingRequest setDropCity(String dropCity) {
        this.dropCity = dropCity;
        return this;
    }

    public BookingRequest setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
        return this;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public BookingRequest setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
        return this;
    }

    public BookingRequest setBookingType(String type) {
        this.bookingType = type;
        return this;
    }

    public String getBookingType() {
        return bookingType;
    }

    public boolean showRange(){
       return bookingType.equals("Corporate") || bookingType.equals("Booker");
    }

    public BookingRequest setTabPosition(int position) {
        tabPosition = position;
        return this;
    }

    public String getEntity() {
        return entity;
    }

    public BookingRequest setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public int getTabPosition() {
        return tabPosition;
    }

    public String getTripType() {
        return tripType;
    }

    public BookingRequest setTripType(String tripType) {
        this.tripType = tripType;
        return this;
    }

    public String getOriginAddress() {
        return origin_address==null?"": origin_address;
    }

    public BookingRequest setOriginAddress(String address) {
        this.origin_address = address;
        return this;
    }

    public BookingRequest setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public BookingRequest setTime(String time) {
        this.time = time;
        return this;
    }

    public String getCity_terminal() {
        return city_terminal;
    }

    public BookingRequest setCity_terminal(String city_terminal) {
        this.city_terminal = city_terminal;
        return this;
    }

    public boolean fromAirport() {
        return tripType.equals("From airport");
    }

    public boolean toAirport() {
        return tripType.equals("To airport");
    }

    public String getPackageType() {
        return packageType == null ? "" : packageType;
    }

    public String getPickUpDateTime() {
        return pickUpDateTime;
    }

    public String getDropCity() {
        return dropCity == null ? "" : dropCity;
    }

    public String getDropOffDateTime() {
        return dropOffDateTime;
    }

    public BookingRequest setDropOffDateTime(String dropOffDateTime) {
        this.dropOffDateTime = dropOffDateTime;
        return this;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public String getDepartureTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault());
        try {
            return dateFormat.format(Objects.requireNonNull(sdf.parse(pickUpDateTime)));
        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }
    }

    public String showRoute() {
        switch (tripType) {
            case "From airport":
                return "From Airport " + pickUpCity + " to " + dropCity;
            case "To airport":
                return "From " + pickUpCity + " to " + dropCity;
            case "Local":
                return "From " + pickUpCity;
            case "Outstation":
                return "From " + pickUpCity + " to" + getDropCity();
            default:
                return "";

        }

    }

    public String getCity() {
        return city;
    }

    public BookingRequest setCity(String city) {
        this.city = city;
        return this;
    }

    public boolean isOneway() {
        return oneway;
    }

    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }

    public long getCalendarTime() {
        return calendarTime;
    }

    public void setCalendarTime(String calendarTime) {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(calendarTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.calendarTime = date.getTime();
    }

    public BookingRequest setBookerId(String bookerId) {
        BookerId = bookerId;
        return this;
    }

    public String getHourlyPackage() {
        return hourlyPackage;
    }
}

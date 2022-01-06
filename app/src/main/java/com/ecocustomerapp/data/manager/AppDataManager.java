/*
 *  Copyright (C) 2019 JAJ Technologies Private Limited
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://jajtechnologies.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp.data.manager;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.ecocustomerapp.data.local.db.DbHelper;
import com.ecocustomerapp.data.local.prefs.PreferencesHelper;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.Login.LoginBody;
import com.ecocustomerapp.data.model.Login.LoginResponseData;
import com.ecocustomerapp.data.model.api.AirportRequest;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.BookingResponse;
import com.ecocustomerapp.data.model.api.CountryStateCityRequest;
import com.ecocustomerapp.data.model.api.CountryStateCityResponse;
import com.ecocustomerapp.data.model.api.DriverData;
import com.ecocustomerapp.data.model.api.Entities;
import com.ecocustomerapp.data.model.api.HourlyPackageResponse;
import com.ecocustomerapp.data.model.api.ListBookings;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.data.model.api.PackageResponse;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.api.Terminals;
import com.ecocustomerapp.data.model.api.Verification;
import com.ecocustomerapp.data.model.otp.OtpRequestBody;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;
import com.ecocustomerapp.data.remote.ApiHelper;
import com.ecocustomerapp.utils.rx.permission.Permission;
import com.ecocustomerapp.utils.rx.permission.RxPermissions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */
@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    AppDataManager(Context context, PreferencesHelper preferencesHelper, ApiHelper apiHelper, DbHelper dbHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mDbHelper = dbHelper;
    }

    @Override
    public Task<String> seedFireBaseToken() {
        return FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        mPreferencesHelper.setFireBaseToken(Objects.requireNonNull(task.getResult()));
                });
    }

    @Override
    public Observable<Permission> seedRequestPermission(AppCompatActivity activity) {
        return null;
    }

    @Override
    public Observable<Permission> seedLocationPermission(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return new RxPermissions(activity).setLogging(true).requestEach(Manifest.permission.ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new RxPermissions(activity).setLogging(true).requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        } else {
            return new RxPermissions(activity).setLogging(true).requestEach(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public Observable<Permission> seedBackgroundLocationPermission(AppCompatActivity activity) {
        return new RxPermissions(activity).setLogging(true).requestEach(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
    }

    @Override
    public Observable<Boolean> shouldShowRequestPermissionRationale(AppCompatActivity activity, int code, String... permissions) {
        return null;
    }

    @Override
    public Observable<Boolean> seedDeviceId(Context context) {
        return null;
    }

    @Override
    public Observable<Boolean> isFireBaseTokenEmpty() {
        return Observable.just(mPreferencesHelper.getFireBaseToken() == null);
    }

    @Override
    public Single<PackageResponse> getPackage(String type, PackageRequest request) {
        switch (type) {
            case "Airport":
                return getLocalLumpsumPackage(request);
            case "Local":
                return getLocalPackage(request);
            case "Outstation":
                return getOutStationPackage(request);
            default:
                return null;
        }
    }

    @Override
    public Single<CountryStateCityResponse> getCity(String type, CountryStateCityRequest request) {
        switch (type) {
            case "Serviceable":
                return getCity(request);
            case "All":
                return getAllCity(request);
            default:
                return null;
        }
    }

    @Override
    public void updateUserInfo(LoginBody response) {
        setAccessToken(response.getToken());
        setCustomerId(response.getCustomer_id());
        setUserName(response.getGuest_name());
        setFirstName(response.getGuest_first_name());
        setLastName(response.getGuest_last_name());
        setUserEmail(response.getEmail());
        setUserMobile(response.getMobile());
        setGender(response.getGender());
        savePassword(response.getPassword());
        setPassengerId(response.getId());
        setBookerId(response.getBooker_id());
        setCustomerType(response.getCustomer_type());
        setPassengerType(response.getCustomer_type());
        setMod(new HashSet<String>(response.getModeOfPayment()));
        setShowCaseCode(response.getShowCaseCode());
        setCaseCodeName(response.getCaseCodeName());
    }

    @Override
    public Registration retrieveUserInfo() {
        return new Registration().setGuest_name(mPreferencesHelper.getUserName())
                .setGuestFirstName(mPreferencesHelper.getFirstName())
                .setGuestLastName(mPreferencesHelper.getLastName())
                .setEmail(mPreferencesHelper.getUserEmail())
                .setMobile(mPreferencesHelper.getUserMobile())
                .setGender(mPreferencesHelper.getGender())
                .setPassword(mPreferencesHelper.getPassword())
                .setPassword(mPreferencesHelper.getPassword())
                .setDeviceId("54554545")
                .setDeviceType("Android")
                .setPushToken("kjkfldjdfhkjdgd")
                .setAppVersion("1.0")
                .setToken("dhfsdjfh")
                .setCustomerType(mPreferencesHelper.getCustomerType());


    }

    @Override
    public void setGPSStatus(boolean b) {
        mPreferencesHelper.setGPSStatus(b);
    }

    @Override
    public boolean getGPSStatus() {
        return mPreferencesHelper.getGPSStatus();
    }

    @Override
    public boolean getRequestingLocationUpdate() {
        return mPreferencesHelper.getRequestingLocationUpdate();
    }

    @Override
    public void setRequestingLocationUpdate(boolean successful) {
        mPreferencesHelper.setRequestingLocationUpdate(successful);
    }

    @Override
    public void setCurrentLocation(Location location) {
        mPreferencesHelper.setCurrentLocation(location);
    }

    @Override
    public Location getCurrentLocation() {
        return mPreferencesHelper.getCurrentLocation();
    }

    @Override
    public Observable<Boolean> insertEntity(Entities entities) {
        return mDbHelper.insertEntity(entities);
    }

    @Override
    public Observable<Boolean> deleteAllEntity() {
        return mDbHelper.deleteAllEntity();
    }

    @Override
    public Observable<Entities> getAllEntity() {
        return mDbHelper.getAllEntity();
    }

    @Override
    public void clearData() {
        mPreferencesHelper.clearData();
    }

    @Override
    public void setMod(Set<String> mod) {
        mPreferencesHelper.setMod(mod);
    }

    @Override
    public Set<String> getMod() {
        return mPreferencesHelper.getMod();
    }

    @Override
    public void setShowCaseCode(String value) {
        mPreferencesHelper.setShowCaseCode(value);
    }

    @Override
    public boolean getShowCaseCode() {
        return mPreferencesHelper.getShowCaseCode();
    }

    @Override
    public void setCaseCodeName(String name) {
        mPreferencesHelper.setCaseCodeName(name);
    }

    @Override
    public String getCaseCodeName() {
        return mPreferencesHelper.getCaseCodeName();
    }

    @Override
    public void setBlockTime(String time) {
        mPreferencesHelper.setBlockTime(time);
    }

    @Override
    public String getBlockTime() {
        return mPreferencesHelper.getBlockTime();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Single<LoginResponseData> loginApiCall(LoginBody request) {
        return mApiHelper.loginApiCall(request);
    }

    @Override
    public Single<OtpResponseBody> getOtpApiCall(OtpRequestBody request) {
        return mApiHelper.getOtpApiCall(request);
    }

    @Override
    public Single<CountryStateCityResponse> getCountry() {
        return mApiHelper.getCountry();
    }

    @Override
    public Single<CountryStateCityResponse> getCity(CountryStateCityRequest countryStateCityRequest) {
        return mApiHelper.getCity(countryStateCityRequest);
    }

    @Override
    public Single<CountryStateCityResponse> getAllCity(CountryStateCityRequest countryStateCityRequest) {
        return mApiHelper.getAllCity(countryStateCityRequest);
    }

    @Override
    public Single<CountryStateCityResponse> getState(CountryStateCityRequest request) {
        return mApiHelper.getState(request);
    }

    @Override
    public Single<PackageResponse> getLocalLumpsumPackage(PackageRequest request) {
        return mApiHelper.getLocalLumpsumPackage(request);
    }

    @Override
    public Single<PackageResponse> getLocalPackage(PackageRequest request) {
        return mApiHelper.getLocalPackage(request);
    }

    @Override
    public Single<PackageResponse> getOutStationPackage(PackageRequest request) {
        return mApiHelper.getOutStationPackage(request);
    }

    @Override
    public Single<BookingResponse> createBooking(BookingRequest request) {
        return mApiHelper.createBooking(request);
    }

    @Override
    public Single<LoginResponseData> createPassenger(Registration request) {
        return mApiHelper.createPassenger(request);
    }

    @Override
    public Single<HourlyPackageResponse> getHourlyPackage(PackageRequest request) {
        return mApiHelper.getHourlyPackage(request);
    }

    @Override
    public Single<OtpResponseBody> getPassword(OtpRequestBody request) {
        return mApiHelper.getPassword(request);
    }

    @Override
    public Single<CountryStateCityResponse> getAirport(AirportRequest request) {
        return mApiHelper.getAirport(request);
    }

    @Override
    public Single<ListBookings> getBookings(PackageRequest request) {
        return mApiHelper.getBookings(request);
    }

    @Override
    public Single<DriverData> getDriverDetails(String request) {
        return mApiHelper.getDriverDetails(request);
    }

    @Override
    public Single<BaseModel> getCancellation(String booking_id, String reason) {
        return mApiHelper.getCancellation(booking_id, reason);
    }

    @Override
    public Single<Verification> getEntity(String email, String mobile,String passengerType) {
        return mApiHelper.getEntity(email, mobile,passengerType);
    }

    @Override
    public Single<Terminals> getTerminal() {
        return mApiHelper.getTerminal();
    }

    @Override
    public Single<BaseModel> changePassword(String id, String password) {
        return mApiHelper.changePassword(id, password);
    }

    @Override
    public Single<OtpResponseBody> forgetPasswordOtp(String email) {
        return mApiHelper.forgetPasswordOtp(email);
    }

    @Override
    public Single<LoginResponseData> getPassenger(String name,String email, String mobile) {
        return mApiHelper.getPassenger(name,email, mobile);
    }

    @Override
    public Single<LoginResponseData> getPaymentMode(String CustomerID) {
        return mApiHelper.getPaymentMode(CustomerID);
    }

    @Override
    public Single<BaseModel> getBlockTime(String CustomerID) {
        return mApiHelper.getBlockTime(CustomerID);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setPushToken(String pushToken) {
        mPreferencesHelper.setPushToken(pushToken);
    }

    @Override
    public String getPushToken() {
        return mPreferencesHelper.getPushToken();
    }

    @Override
    public void setUserName(String userName) {
        mPreferencesHelper.setUserName(userName);
    }

    @Override
    public String getUserName() {
        return mPreferencesHelper.getUserName();
    }

    @Override
    public void setFirstName(String firstName) {
        mPreferencesHelper.setFirstName(firstName);
    }

    @Override
    public String getFirstName() {
        return mPreferencesHelper.getFirstName();
    }

    @Override
    public void setLastName(String lastName) {
        mPreferencesHelper.setLastName(lastName);
    }

    @Override
    public String getLastName() {
        return mPreferencesHelper.getLastName();
    }

    @Override
    public void setUserMobile(String userMobile) {
        mPreferencesHelper.setUserMobile(userMobile);
    }

    @Override
    public String getUserMobile() {
        return mPreferencesHelper.getUserMobile();
    }

    @Override
    public void setUserEmail(String userEmail) {
        mPreferencesHelper.setUserEmail(userEmail);
    }

    @Override
    public String getUserEmail() {
        return mPreferencesHelper.getUserEmail();
    }

    @Override
    public void setCustomerId(String id) {
        mPreferencesHelper.setCustomerId(id);
    }

    @Override
    public String getCustomerId() {
        return mPreferencesHelper.getCustomerId();
    }

    @Override
    public void setPassengerId(String id) {
        mPreferencesHelper.setPassengerId(id);
    }

    @Override
    public String getPassengerId() {
        return mPreferencesHelper.getPassengerId();
    }

    @Override
    public void setCustomerType(String type) {
        mPreferencesHelper.setCustomerType(type);
    }

    @Override
    public String getCustomerType() {
        return mPreferencesHelper.getCustomerType();
    }

    @Override
    public void setPassengerType(String type) {
        mPreferencesHelper.setPassengerType(type);
    }

    @Override
    public String getPassengerType() {
        return mPreferencesHelper.getPassengerType() != null ? mPreferencesHelper.getPassengerType() : "Corporate";
    }

    @Override
    public void setGender(String gender) {
        mPreferencesHelper.setGender(gender);
    }

    @Override
    public String getGender() {
        return mPreferencesHelper.getGender();
    }

    @Override
    public void savePassword(String password) {
        mPreferencesHelper.savePassword(password);
    }

    @Override
    public String getPassword() {
        return mPreferencesHelper.getPassword();
    }

    @Override
    public void setBookerId(String id) {
        mPreferencesHelper.setBookerId(id);
    }

    @Override
    public String getBookerId() {
        return mPreferencesHelper.getBookerId();
    }

    @Override
    public void setAirportPickup(String pickup) {
        mPreferencesHelper.setAirportPickup(pickup);
    }

    @Override
    public String getAirportPickup() {
        return mPreferencesHelper.getAirportPickup();
    }

    @Override
    public String getFireBaseToken() {
        return mPreferencesHelper.getFireBaseToken();
    }

    @Override
    public void setFireBaseToken(String token) {
        mPreferencesHelper.setFireBaseToken(token);
    }
}

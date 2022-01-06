/*
 *  Copyright (C) 2019 JAJ TECHNOLOGIES PRIVATE LIMITED
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

package com.ecocustomerapp.data.remote;


import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.Login.LoginBody;
import com.ecocustomerapp.data.model.Login.LoginResponseData;
import com.ecocustomerapp.data.model.api.AirportRequest;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.BookingResponse;
import com.ecocustomerapp.data.model.api.CountryStateCityRequest;
import com.ecocustomerapp.data.model.api.CountryStateCityResponse;
import com.ecocustomerapp.data.model.api.DriverData;
import com.ecocustomerapp.data.model.api.HourlyPackageResponse;
import com.ecocustomerapp.data.model.api.ListBookings;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.data.model.api.PackageResponse;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.api.Terminals;
import com.ecocustomerapp.data.model.api.Verification;
import com.ecocustomerapp.data.model.otp.OtpRequestBody;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

@Singleton
public class AppApiHelper implements ApiHelper {


    @Inject
    public AppApiHelper() {

    }


    @Override
    public Single<LoginResponseData> loginApiCall(LoginBody request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestLogin(request);
    }

    @Override
    public Single<OtpResponseBody> getOtpApiCall(OtpRequestBody request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestOtp(request);
    }

    @Override
    public Single<CountryStateCityResponse> getCountry() {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestCountry();
    }

    @Override
    public Single<CountryStateCityResponse> getCity(CountryStateCityRequest countryStateCityRequest) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestCity(countryStateCityRequest);
    }

    @Override
    public Single<CountryStateCityResponse> getAllCity(CountryStateCityRequest countryStateCityRequest) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestAllCity(countryStateCityRequest);
    }

    @Override
    public Single<CountryStateCityResponse> getState(CountryStateCityRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestState(request);
    }

    @Override
    public Single<PackageResponse> getLocalLumpsumPackage(PackageRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestLocalLumpsumPackage(request);
    }

    @Override
    public Single<PackageResponse> getLocalPackage(PackageRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestLocalPackage(request);
    }

    @Override
    public Single<PackageResponse> getOutStationPackage(PackageRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestOutStationPackage(request);
    }

    @Override
    public Single<BookingResponse> createBooking(BookingRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestBooking(request);
    }

    @Override
    public Single<LoginResponseData> createPassenger(Registration request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestRegistration(request);
    }

    @Override
    public Single<HourlyPackageResponse> getHourlyPackage(PackageRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestHourlyPackage(request);
    }

    @Override
    public Single<OtpResponseBody> getPassword(OtpRequestBody request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestPassword(request);
    }

    @Override
    public Single<CountryStateCityResponse> getAirport(AirportRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestAirport(request);
    }

    @Override
    public Single<ListBookings> getBookings(PackageRequest request) {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestBookings(request);
    }

    @Override
    public Single<DriverData> getDriverDetails(String booking_id) {
        Map<String, String> map = new HashMap<>();
        map.put("bookingNo", booking_id);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestDriverDetails(map);
    }

    @Override
    public Single<BaseModel> getCancellation(String booking_id, String reason) {
        Map<String, String> map = new HashMap<>();
        map.put("bookingNo", booking_id);
        map.put("reason", reason);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestCancellation(map);
    }

    @Override
    public Single<Verification> getEntity(String email, String mobile,String passengerType) {
        Map<String, String> map = new HashMap<>();
        map.put("Email", email);
        map.put("Mobile", mobile);
        map.put("passengerType", passengerType);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestEntity(map);
    }

    @Override
    public Single<Terminals> getTerminal() {
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).requestTerminal();
    }

    @Override
    public Single<BaseModel> changePassword(String id, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("ID", id);
        map.put("Password", password);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).changePassword(map);
    }

    @Override
    public Single<OtpResponseBody> forgetPasswordOtp(String email) {
        Map<String, String> map = new HashMap<>();
        map.put("Email", email);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.NONE).forgetPasswordOtp(map);
    }

    @Override
    public Single<LoginResponseData> getPassenger(String name,String email, String mobile) {
        Map<String, String> map = new HashMap<>();
        map.put("GuestName", name);
        map.put("Email", email);
        map.put("mobile", mobile);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.BODY).getPassenger(map);
    }

    @Override
    public Single<LoginResponseData> getPaymentMode(String CustomerID) {
        Map<String, String> map = new HashMap<>();
        map.put("CustomerID", CustomerID);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.BODY).getPaymentMode(map);
    }

    @Override
    public Single<BaseModel> getBlockTime(String CustomerID) {
        Map<String, String> map = new HashMap<>();
        map.put("CustomerID", CustomerID);
        return ClientConnection.getInstance().createService(HttpLoggingInterceptor.Level.BODY).getBlockTime(map);
    }
}

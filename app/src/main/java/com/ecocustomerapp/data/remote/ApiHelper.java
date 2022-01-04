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

import io.reactivex.Single;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

public interface ApiHelper {

    Single<LoginResponseData> loginApiCall(LoginBody request);

    Single<OtpResponseBody> getOtpApiCall(OtpRequestBody request);

    Single<CountryStateCityResponse> getCountry();

    Single<CountryStateCityResponse> getCity(CountryStateCityRequest countryStateCityRequest);

    Single<CountryStateCityResponse> getAllCity(CountryStateCityRequest countryStateCityRequest);

    Single<CountryStateCityResponse> getState(CountryStateCityRequest request);

    Single<PackageResponse> getLocalLumpsumPackage(PackageRequest request);

    Single<PackageResponse> getLocalPackage(PackageRequest request);

    Single<PackageResponse> getOutStationPackage(PackageRequest request);

    Single<BookingResponse> createBooking(BookingRequest request);

    Single<LoginResponseData> createPassenger(Registration request);

    Single<HourlyPackageResponse> getHourlyPackage(PackageRequest request);

    Single<OtpResponseBody> getPassword(OtpRequestBody request);

    Single<CountryStateCityResponse> getAirport(AirportRequest request);

    Single<ListBookings> getBookings(PackageRequest request);

    Single<DriverData> getDriverDetails(String request);

    Single<BaseModel> getCancellation(String booking_id, String reason);

    Single<Verification> getEntity(String email, String mobile);

    Single<Terminals> getTerminal();

    Single<BaseModel> changePassword(String id, String password);

    Single<OtpResponseBody> forgetPasswordOtp(String email);

    Single<LoginResponseData> getPassenger(String name, String email, String mobile);

    Single<LoginResponseData> getPaymentMode(String CustomerID);

    Single<BaseModel> getBlockTime(String CustomerID);
}

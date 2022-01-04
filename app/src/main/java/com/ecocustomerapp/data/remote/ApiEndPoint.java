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


/**
 * Created by Saurabh Srivastava on 29.05.19
 */

final class ApiEndPoint {

    static final String HEADER_CONTENT_TYPE = "Content-Type: Application/json";
    static final String HEADER_ACCEPT_CHARSET = "Accept-Charset: utf-8";
    static final String LOGIN = "login";
    static final String OTP = "get-otp";
    static final String FORGET_PASSWORD_OTP = "GetOtpforgotPassword";
    static final String CHANGE_PASSWORD = "changePassword";
    static final String COUNTRY = "getCountry";
    static final String CITY = "cities";
    static final String ALL_CITY = "getAllCity";
    static final String STATE = "getState";
    static final String LUMP_SUM = "getLocalLumpsumPackage";
    static final String LOCAL = "getLocalPackage";
    static final String OUT_STATION = "getOutstaionPackage";
    static final String CREATE_BOOKING = "createBooking";
    static final String CREATE_PASSENGER = "register";
    static final String HOURLY_PACKAGE = "gethourly_package";
    static final String PASSWORD = "ForgotPassword";
    static final String AIRPORT = "getAirport";
    static final String BOOKINGS = "getBookings";
    static final String DRIVER_DETAILS = "DriverDetails";
    static final String CANCEL = "BookingCancellation";
    static final String GET_ENTITY = "get-booking-entity";
    static final String GET_TERMINAL = "getTerminal";
    static final String GET_PASSENGER = "getPassenger";
    static final String GET_PAYMENT_MODE = "getBookerPaymentMethod";
    static final String GET_BLOCK_TIME = "getCalenderBlockTime";


    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}

package com.ecocustomerapp.data.remote;


import static com.ecocustomerapp.data.remote.ApiEndPoint.AIRPORT;
import static com.ecocustomerapp.data.remote.ApiEndPoint.ALL_CITY;
import static com.ecocustomerapp.data.remote.ApiEndPoint.BOOKINGS;
import static com.ecocustomerapp.data.remote.ApiEndPoint.CANCEL;
import static com.ecocustomerapp.data.remote.ApiEndPoint.CHANGE_PASSWORD;
import static com.ecocustomerapp.data.remote.ApiEndPoint.CITY;
import static com.ecocustomerapp.data.remote.ApiEndPoint.COUNTRY;
import static com.ecocustomerapp.data.remote.ApiEndPoint.CREATE_BOOKING;
import static com.ecocustomerapp.data.remote.ApiEndPoint.CREATE_PASSENGER;
import static com.ecocustomerapp.data.remote.ApiEndPoint.DRIVER_DETAILS;
import static com.ecocustomerapp.data.remote.ApiEndPoint.FORGET_PASSWORD_OTP;
import static com.ecocustomerapp.data.remote.ApiEndPoint.GET_BLOCK_TIME;
import static com.ecocustomerapp.data.remote.ApiEndPoint.GET_ENTITY;
import static com.ecocustomerapp.data.remote.ApiEndPoint.GET_PASSENGER;
import static com.ecocustomerapp.data.remote.ApiEndPoint.GET_PAYMENT_MODE;
import static com.ecocustomerapp.data.remote.ApiEndPoint.GET_TERMINAL;
import static com.ecocustomerapp.data.remote.ApiEndPoint.HEADER_ACCEPT_CHARSET;
import static com.ecocustomerapp.data.remote.ApiEndPoint.HEADER_CONTENT_TYPE;
import static com.ecocustomerapp.data.remote.ApiEndPoint.HOURLY_PACKAGE;
import static com.ecocustomerapp.data.remote.ApiEndPoint.LOCAL;
import static com.ecocustomerapp.data.remote.ApiEndPoint.LOGIN;
import static com.ecocustomerapp.data.remote.ApiEndPoint.LUMP_SUM;
import static com.ecocustomerapp.data.remote.ApiEndPoint.OTP;
import static com.ecocustomerapp.data.remote.ApiEndPoint.OUT_STATION;
import static com.ecocustomerapp.data.remote.ApiEndPoint.PASSWORD;
import static com.ecocustomerapp.data.remote.ApiEndPoint.STATE;

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

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ClientService {

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(LOGIN)
    Single<LoginResponseData> requestLogin(@Body LoginBody body);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(OTP)
    Single<OtpResponseBody> requestOtp(@Body OtpRequestBody body);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @GET(COUNTRY)
    Single<CountryStateCityResponse> requestCountry();

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(CITY)
    Single<CountryStateCityResponse> requestCity(@Body CountryStateCityRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(ALL_CITY)
    Single<CountryStateCityResponse> requestAllCity(@Body CountryStateCityRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(STATE)
    Single<CountryStateCityResponse> requestState(@Body CountryStateCityRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(LUMP_SUM)
    Single<PackageResponse> requestLocalLumpsumPackage(@Body PackageRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(LOCAL)
    Single<PackageResponse> requestLocalPackage(@Body PackageRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(OUT_STATION)
    Single<PackageResponse> requestOutStationPackage(@Body PackageRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(CREATE_BOOKING)
    Single<BookingResponse> requestBooking(@Body BookingRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(CREATE_PASSENGER)
    Single<LoginResponseData> requestRegistration(@Body Registration request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(HOURLY_PACKAGE)
    Single<HourlyPackageResponse> requestHourlyPackage(@Body PackageRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(PASSWORD)
    Single<OtpResponseBody> requestPassword(@Body OtpRequestBody request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(AIRPORT)
    Single<CountryStateCityResponse> requestAirport(@Body AirportRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(BOOKINGS)
    Single<ListBookings> requestBookings(@Body PackageRequest request);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(DRIVER_DETAILS)
    Single<DriverData> requestDriverDetails(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(CANCEL)
    Single<BaseModel> requestCancellation(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(GET_ENTITY)
    Single<Verification> requestEntity(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @GET(GET_TERMINAL)
    Single<Terminals> requestTerminal();

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(CHANGE_PASSWORD)
    Single<BaseModel> changePassword(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(FORGET_PASSWORD_OTP)
    Single<OtpResponseBody> forgetPasswordOtp(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(GET_PASSENGER)
    Single<LoginResponseData> getPassenger(@Body Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(GET_PAYMENT_MODE)
    Single<LoginResponseData> getPaymentMode(@Body  Map<String, String> map);

    @Headers({HEADER_CONTENT_TYPE, HEADER_ACCEPT_CHARSET})
    @POST(GET_BLOCK_TIME)
    Single<BaseModel> getBlockTime(@Body  Map<String, String> map);
}

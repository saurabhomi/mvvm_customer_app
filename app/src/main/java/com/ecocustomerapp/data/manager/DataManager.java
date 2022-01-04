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

package com.ecocustomerapp.data.manager;


import android.content.Context;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;

import com.ecocustomerapp.data.local.db.DbHelper;
import com.ecocustomerapp.data.local.prefs.PreferencesHelper;
import com.ecocustomerapp.data.model.Login.LoginBody;
import com.ecocustomerapp.data.model.api.CountryStateCityRequest;
import com.ecocustomerapp.data.model.api.CountryStateCityResponse;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.data.model.api.PackageResponse;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.api.Verification;
import com.ecocustomerapp.data.remote.ApiHelper;
import com.ecocustomerapp.utils.rx.permission.Permission;
import com.google.android.gms.tasks.Task;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {


    Task<String> seedFireBaseToken();

    Observable<Permission> seedRequestPermission(AppCompatActivity activity);

    Observable<Permission> seedLocationPermission(AppCompatActivity activity);

    Observable<Permission> seedBackgroundLocationPermission(AppCompatActivity activity);

    Observable<Boolean> shouldShowRequestPermissionRationale(AppCompatActivity activity, int code, String... permissions);

    Observable<Boolean> seedDeviceId(Context context);

    Observable<Boolean> isFireBaseTokenEmpty();

    Single<PackageResponse> getPackage(String type, PackageRequest request);

    Single<CountryStateCityResponse> getCity(String type, CountryStateCityRequest request);

    void updateUserInfo(LoginBody response);

    Registration retrieveUserInfo();

    void setGPSStatus(boolean b);

    boolean getGPSStatus();

    boolean getRequestingLocationUpdate();

    void setRequestingLocationUpdate(boolean successful);

    void setCurrentLocation(Location location);

    Location getCurrentLocation();

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),

        LOGGED_IN_MODE_SERVER(1),

        LOGGED_IN_MODE_CHANGE_PASSWORD(2);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

}

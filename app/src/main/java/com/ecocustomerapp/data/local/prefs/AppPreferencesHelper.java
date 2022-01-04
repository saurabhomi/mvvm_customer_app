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

package com.ecocustomerapp.data.local.prefs;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.di.PreferenceInfo;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_DEVICE_TOKEN = "PREF_KEY_DEVICE_TOKEN";

    private static final String PREF_KEY_PUSH_TOKEN = "PREF_KEY_DEVICE_TOKEN";

    private static final String PREF_KEY_USER_PHONE = "PREF_KEY_CURRENT_USER_PHONE";

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_FIRST_NAME = "PREF_KEY_FIRST_NAME";

    private static final String PREF_KEY_LAST_NAME = "PREF_KEY_LAST_NAME";

    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_USER_GENDER = "PREF_KEY_CURRENT_USER_GENDER";

    private static final String PREF_KEY_USER_PASSWORD = "PREF_KEY_CURRENT_USER_PASSWORD";

    private static final String PREF_KEY_CUSTOMER_ID = "PREF_KEY_CUSTOMER_ID";

    private static final String PREF_KEY_CUSTOMER_TYPE = "PREF_KEY_CUSTOMER_TYPE";

    private static final String PREF_KEY_PASSENGER_TYPE = "PREF_KEY_PASSENGER_TYPE";

    private static final String PREF_KEY_BOOKER_ID = "PREF_KEY_BOOKER_ID";

    private static final String PREF_KEY_ID = "PREF_KEY_ID";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_AIRPORT_PICKUP_TYPE = "PREF_KEY_AIRPORT_PICKUP_TYPE";

    private static final String PREF_KEY_GPS_STATUS = "PREF_KEY_GPS_STATUS";

    private static final String PREF_KEY_LOCATION_UPDATE = "PREF_KAY_LOCATION_UPDATE";

    private static final String PREF_KEY_CURRENT_LATITUDE = "latitude";

    private static final String PREF_KEY_CURRENT_LONGITUDE = "longitude";

    private static final String PREF_KEY_GPS_TIME = "gps_time";

    private static final String PREF_KEY_MOD = "mod";

    private static final String PREF_KEY_SHOW_CODE = "show_code";

    private static final String PREF_KEY_CODE_NAME = "code_name";

    private static final String PREF_KEY_BLOCK_TIME = "block_time";


    private final SharedPreferences preferences;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        preferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return preferences.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        preferences.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public void setAccessToken(String accessToken) {
        preferences.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getAccessToken() {
        return preferences.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setPushToken(String pushToken) {
        preferences.edit().putString(PREF_KEY_PUSH_TOKEN, pushToken).apply();
    }

    @Override
    public String getPushToken() {
        return preferences.getString(PREF_KEY_PUSH_TOKEN, null);
    }

    @Override
    public void setUserName(String userName) {
        preferences.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public String getUserName() {
        return preferences.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setFirstName(String firstName) {
        preferences.edit().putString(PREF_KEY_FIRST_NAME, firstName).apply();
    }

    @Override
    public String getFirstName() {
        return preferences.getString(PREF_KEY_FIRST_NAME, null);
    }

    @Override
    public void setLastName(String lastName) {
        preferences.edit().putString(PREF_KEY_LAST_NAME, lastName).apply();
    }

    @Override
    public String getLastName() {
        return preferences.getString(PREF_KEY_LAST_NAME, null);
    }

    @Override
    public void setUserMobile(String userMobile) {
        preferences.edit().putString(PREF_KEY_USER_PHONE, userMobile).apply();
    }

    @Override
    public String getUserMobile() {
        return preferences.getString(PREF_KEY_USER_PHONE, null);
    }

    @Override
    public void setUserEmail(String userEmail) {
        preferences.edit().putString(PREF_KEY_USER_EMAIL, userEmail).apply();
    }

    @Override
    public String getUserEmail() {
        return preferences.getString(PREF_KEY_USER_EMAIL, null);
    }

    @Override
    public void setCustomerId(String id) {
        preferences.edit().putString(PREF_KEY_CUSTOMER_ID, id).apply();
    }

    @Override
    public String getCustomerId() {
        return preferences.getString(PREF_KEY_CUSTOMER_ID, null);
    }

    @Override
    public void setPassengerId(String id) {
        preferences.edit().putString(PREF_KEY_ID, id).apply();
    }

    @Override
    public String getPassengerId() {
        return preferences.getString(PREF_KEY_ID, null);
    }

    @Override
    public void setCustomerType(String type) {
        preferences.edit().putString(PREF_KEY_CUSTOMER_TYPE, type).apply();
    }

    @Override
    public String getCustomerType() {
        return preferences.getString(PREF_KEY_CUSTOMER_TYPE, null);
    }

    @Override
    public void setPassengerType(String type) {
        preferences.edit().putString(PREF_KEY_PASSENGER_TYPE, type).apply();
    }

    @Override
    public String getPassengerType() {
        return preferences.getString(PREF_KEY_PASSENGER_TYPE, "");
    }

    @Override
    public void setGender(String gender) {
        preferences.edit().putString(PREF_KEY_USER_GENDER, gender).apply();
    }

    @Override
    public String getGender() {
        return preferences.getString(PREF_KEY_USER_GENDER, null);
    }

    @Override
    public void savePassword(String password) {
        preferences.edit().putString(PREF_KEY_USER_PASSWORD, password).apply();
    }

    @Override
    public String getPassword() {
        return preferences.getString(PREF_KEY_USER_PASSWORD, null);
    }

    @Override
    public void setBookerId(String id) {
        preferences.edit().putString(PREF_KEY_BOOKER_ID, id).apply();
    }

    @Override
    public String getBookerId() {
        return preferences.getString(PREF_KEY_BOOKER_ID, null);
    }

    @Override
    public void setAirportPickup(String pickup) {
        preferences.edit().putString(PREF_KEY_AIRPORT_PICKUP_TYPE, pickup).apply();
    }

    @Override
    public String getAirportPickup() {
        return preferences.getString(PREF_KEY_AIRPORT_PICKUP_TYPE, null);
    }

    @Override
    public synchronized String getFireBaseToken() {
        String token = preferences.getString(PREF_KEY_DEVICE_TOKEN, null);
        Timber.d("token: %s ", token);
        return token;
    }

    @Override
    public synchronized void setFireBaseToken(String token) {
        preferences.edit().putString(PREF_KEY_DEVICE_TOKEN, token).apply();
    }

    @Override
    public void setGPSStatus(boolean b) {
        preferences.edit().putBoolean(PREF_KEY_GPS_STATUS, b).apply();
    }

    @Override
    public boolean getGPSStatus() {
        return preferences.getBoolean(PREF_KEY_GPS_STATUS, false);
    }

    @Override
    public void setRequestingLocationUpdate(boolean successful) {
        preferences.edit().putBoolean(PREF_KEY_LOCATION_UPDATE, successful).apply();
    }

    @Override
    public boolean getRequestingLocationUpdate() {
        return preferences.getBoolean(PREF_KEY_LOCATION_UPDATE, false);
    }

    @Override
    public synchronized Location getCurrentLocation() {
        Location location = new Location(LOCATION_SERVICE);
        location.setLatitude(preferences.getFloat(PREF_KEY_CURRENT_LATITUDE, (float) 0.0));
        location.setLongitude(preferences.getFloat(PREF_KEY_CURRENT_LONGITUDE, (float) 0.0));
        location.setTime(preferences.getLong(PREF_KEY_GPS_TIME, (long) 0.0));
        return location;
    }

    @Override
    public void clearData() {
        preferences.edit().clear().apply();
    }

    @Override
    public void setMod(Set<String> mod) {
        preferences.edit().putStringSet(PREF_KEY_MOD, mod).apply();
    }

    @Override
    public Set<String> getMod() {
        return preferences.getStringSet(PREF_KEY_MOD, Collections.singleton(""));
    }

    @Override
    public void setShowCaseCode(String value) {
        preferences.edit().putString(PREF_KEY_SHOW_CODE, value).apply();
    }

    @Override
    public boolean getShowCaseCode() {
        return preferences.getString(PREF_KEY_SHOW_CODE, null).equals("Yes");
    }

    @Override
    public void setCaseCodeName(String name) {
        preferences.edit().putString(PREF_KEY_CODE_NAME, name).apply();
    }

    @Override
    public String getCaseCodeName() {
        return preferences.getString(PREF_KEY_CODE_NAME, null);
    }

    @Override
    public void setBlockTime(String time) {
        preferences.edit().putString(PREF_KEY_BLOCK_TIME, time).apply();
    }

    @Override
    public String getBlockTime() {
        return preferences.getString(PREF_KEY_BLOCK_TIME, "0");
    }

    @Override
    public synchronized void setCurrentLocation(Location location) {
        preferences.edit().putFloat(PREF_KEY_CURRENT_LATITUDE, (float) location.getLatitude()).apply();
        preferences.edit().putFloat(PREF_KEY_CURRENT_LONGITUDE, (float) location.getLongitude()).apply();
        preferences.edit().putLong(PREF_KEY_GPS_TIME, location.getTime()).apply();
    }
}

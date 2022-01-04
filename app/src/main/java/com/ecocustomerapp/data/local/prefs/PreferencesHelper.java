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


import android.location.Location;

import com.ecocustomerapp.data.manager.DataManager;

import java.util.Set;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    void setAccessToken(String accessToken);

    String getAccessToken();

    void setPushToken(String pushToken);

    String getPushToken();

    void setUserName(String userName);

    String getUserName();

    void setFirstName(String firstName);

    String getFirstName();

    void setLastName(String lastName);

    String getLastName();

    void setUserMobile(String userMobile);

    String getUserMobile();

    void setUserEmail(String userEmail);

    String getUserEmail();

    void setCustomerId(String id);

    String getCustomerId();

    void setPassengerId(String id);

    String getPassengerId();

    void setCustomerType(String type);

    String getCustomerType();

    void setPassengerType(String type);

    String getPassengerType();

    void setGender(String gender);

    String getGender();

    void savePassword(String password);

    String getPassword();

    void setBookerId(String id);

    String getBookerId();

    void setAirportPickup(String pickup);

    String getAirportPickup();

    String getFireBaseToken();

    void setFireBaseToken(String token);

    void setGPSStatus(boolean b);

    boolean getGPSStatus();

    void setRequestingLocationUpdate(boolean successful);

    boolean getRequestingLocationUpdate();

    void setCurrentLocation(Location location);

    Location getCurrentLocation();

    void clearData();

    void setMod(Set<String> mod);

    Set<String> getMod();

    void setShowCaseCode(String value);

    boolean getShowCaseCode();

    void setCaseCodeName(String name);

    String getCaseCodeName();

    void setBlockTime(String time);

    String getBlockTime();

}

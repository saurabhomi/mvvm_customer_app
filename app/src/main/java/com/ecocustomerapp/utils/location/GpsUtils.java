package com.ecocustomerapp.utils.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;

import com.ecocustomerapp.utils.AppConstants;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;

import timber.log.Timber;

public class GpsUtils {
    private final Activity activity;
    private final SettingsClient mSettingsClient;
    private final LocationSettingsRequest mLocationSettingsRequest;
    private final LocationManager locationManager;

    public GpsUtils(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        mSettingsClient = LocationServices.getSettingsClient(activity);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
//**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************
    }

    // method for turn on GPS
    public void turnGPSOn(GpsStatusListener onGpsListener) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (onGpsListener != null) {
                onGpsListener.gpsStatus(true);
            }
        } else {
            mSettingsClient
                    .checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(activity, locationSettingsResponse -> {
//  GPS is already enable, callback GPS status through listener
                        if (onGpsListener != null) {
                            onGpsListener.gpsStatus(true);
                        }
                    })
                    .addOnFailureListener(activity, e -> {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(activity, AppConstants.LOCATION_PERMISSION_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Timber.i("PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Timber.e(errorMessage);
                                break;
                        }
                    });
        }
    }

    public interface GpsStatusListener {
        void gpsStatus(boolean isGPSEnable);
    }
}
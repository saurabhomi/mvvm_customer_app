package com.ecocustomerapp.ui.fragments.tracking;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

public interface TrackingNavigator {

    void showProgress(boolean show);

    void handleError(Throwable throwable, int message);

    void showToast(String message);

    void replaceFragment(Fragment fragment, String TAG);

    Fragment getUi();

    Context getContext();

    String getBookingId();

    SupportMapFragment getMapFragment();
}

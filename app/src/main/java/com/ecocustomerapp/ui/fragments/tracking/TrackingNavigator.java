package com.ecocustomerapp.ui.fragments.tracking;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

public interface TrackingNavigator {

    void showProgress(boolean show);

    void handleError(Throwable throwable, int message);

    void showToast(String message);

    void replaceFragment(Fragment fragment, String TAG);

    Fragment getUi();

    String getBookingId();

    SupportMapFragment getMapFragment();
}

package com.ecocustomerapp.ui.fragments.main.airport;

import android.content.Context;

import androidx.fragment.app.Fragment;

public interface AirportNavigator {

    void showProgress();
    void hideProgress();
    void showToast(String message);
    void replaceFragment(Fragment fragment, String TAG);
    Context getContext();
}

package com.ecocustomerapp.ui.fragments.main.main;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.ecocustomerapp.data.model.api.BookingRequest;

public interface MainFragmentNavigator {

    void showProgress();

    void hideProgress();

    void showToast(String message);

    BookingRequest getBookingRequest();

    void onCheckChange(boolean check);

    void replaceFragment(Fragment fragment, String TAG);

    Context getContext();

}


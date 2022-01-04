package com.ecocustomerapp.ui.fragments.review;

import androidx.fragment.app.Fragment;

import com.ecocustomerapp.data.model.api.BookingResponse;

public interface FinalBookingNavigator {

    void showToast(String message);

    void replaceFragment(Fragment fragment);

    boolean getBack();

    void showBooking(BookingResponse response);

    void showProgress();

    void hideProgress();

    Fragment getUi();
}

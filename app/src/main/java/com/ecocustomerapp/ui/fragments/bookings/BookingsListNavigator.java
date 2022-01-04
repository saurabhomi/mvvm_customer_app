package com.ecocustomerapp.ui.fragments.bookings;

public interface BookingsListNavigator {
    void showProgress();

    void hideProgress();

    void showToast(String message);

    boolean enable();
}

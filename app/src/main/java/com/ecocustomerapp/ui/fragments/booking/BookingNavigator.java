package com.ecocustomerapp.ui.fragments.booking;

import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.data.model.api.BookingRequest;

public interface BookingNavigator {
    void proceedToPay(BookingRequest request);

    void showToast(String message);

    BookingRequest getRequest();

    void showProgress();

    void hideProgress();

    void modify();
}

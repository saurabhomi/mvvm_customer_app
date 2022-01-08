/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp.ui.fragments.bookings;

import androidx.databinding.ObservableField;

import com.ecocustomerapp.data.model.api.Booking;

/**
 * Created by Saurabh Srivastava on 10/07/17.
 */

public class BookingsItemViewModel {

    public final ObservableField<String> pickup;

    public final ObservableField<String> date_time;

    public final ObservableField<String> duty_type;

    public final ObservableField<String> destination;

    public final ObservableField<String> status;

    public final ObservableField<String> booking_no;

    public final ObservableField<String> cancel;

    public final BookingsItemViewModelListener mListener;

    private final Booking booking;

    public BookingsItemViewModel(Booking Booking, BookingsItemViewModelListener listener) {
        this.booking = Booking;
        this.mListener = listener;

        pickup = new ObservableField<>(booking.getPickUpAddress()+", "+booking.getCity());
        date_time = new ObservableField<>(booking.getPickUpDateTime());
        duty_type = new ObservableField<>(booking.getPackageType());
        destination = new ObservableField<>(booking.getDropAddress()+", "+booking.getCity());
        status = new ObservableField<>(booking.getStatus());
        booking_no = new ObservableField<>(booking.getBookingNo());
        cancel = new ObservableField<>("Cancel Booking");
    }

    public void onItemClick() {
        mListener.onItemClick(booking);
    }
    public void onCancel() {
        mListener.onCancel(booking);
    }

    public void onTrack() {
        mListener.onTrack(booking);
    }

    public interface BookingsItemViewModelListener {

        void onItemClick(Booking Booking);

        void onCancel(Booking Booking);

        void onTrack(Booking Booking);
    }
}

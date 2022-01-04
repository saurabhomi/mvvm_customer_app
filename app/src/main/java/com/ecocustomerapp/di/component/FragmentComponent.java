package com.ecocustomerapp.di.component;

import com.ecocustomerapp.di.module.FragmentModule;
import com.ecocustomerapp.di.scope.FragmentScope;
import com.ecocustomerapp.ui.fragments.booking.BookingFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsListFragment;
import com.ecocustomerapp.ui.fragments.cancel.CancelFragment;
import com.ecocustomerapp.ui.fragments.car.CarFragment;
import com.ecocustomerapp.ui.fragments.confirmation.ReceiptFragment;
import com.ecocustomerapp.ui.fragments.main.airport.AirportFragment;
import com.ecocustomerapp.ui.fragments.main.local.LocalFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;
import com.ecocustomerapp.ui.fragments.main.outstation.OutstationFragment;
import com.ecocustomerapp.ui.fragments.mode.ModeFragment;
import com.ecocustomerapp.ui.fragments.profile.ProfileFragment;
import com.ecocustomerapp.ui.fragments.review.FinalBookingFragment;
import com.ecocustomerapp.ui.fragments.support.SupportFragmentFragment;
import com.ecocustomerapp.ui.fragments.tracking.TrackingFragment;

import dagger.Component;

/*
 * Author: rotbolt
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(AirportFragment fragment);

    void inject(LocalFragment fragment);

    void inject(OutstationFragment fragment);

    void inject(MainFragment fragment);

    void inject(CarFragment fragment);

    void inject(BookingFragment fragment);

    void inject(FinalBookingFragment fragment);

    void inject(BookingsFragment fragment);

    void inject(BookingsListFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(TrackingFragment fragment);

    void inject(CancelFragment fragment);

    void inject(ModeFragment fragment);

    void inject(ReceiptFragment fragment);

    void inject(SupportFragmentFragment fragment);


}

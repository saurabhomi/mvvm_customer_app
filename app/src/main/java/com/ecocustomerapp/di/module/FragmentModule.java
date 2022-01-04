package com.ecocustomerapp.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecocustomerapp.ViewModelProviderFactory;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.booking.BookingViewModel;
import com.ecocustomerapp.ui.fragments.bookings.BookingsAdapter;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragmentViewModel;
import com.ecocustomerapp.ui.fragments.bookings.BookingsListViewModel;
import com.ecocustomerapp.ui.fragments.bookings.BookingsPagerAdapter;
import com.ecocustomerapp.ui.fragments.cancel.CancelViewModel;
import com.ecocustomerapp.ui.fragments.car.CarAdapter;
import com.ecocustomerapp.ui.fragments.car.CarViewModel;
import com.ecocustomerapp.ui.fragments.confirmation.ReceiptViewModel;
import com.ecocustomerapp.ui.fragments.main.airport.AirportViewModel;
import com.ecocustomerapp.ui.fragments.main.local.LocalViewModel;
import com.ecocustomerapp.ui.fragments.main.main.MainFragmentViewModel;
import com.ecocustomerapp.ui.fragments.main.main.MainPagerAdapter;
import com.ecocustomerapp.ui.fragments.main.outstation.OutstationViewModel;
import com.ecocustomerapp.ui.fragments.mode.ModeViewModel;
import com.ecocustomerapp.ui.fragments.profile.ProfileViewModel;
import com.ecocustomerapp.ui.fragments.review.FinalBookingViewModel;
import com.ecocustomerapp.ui.fragments.review.GridAdapter;
import com.ecocustomerapp.ui.fragments.support.SupportViewModel;
import com.ecocustomerapp.ui.fragments.tracking.TrackingViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/*
 * Author: rotbolt
 */

@Module
public class FragmentModule {

    private final BaseFragment<?, ?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?, ?> fragment) {
        this.fragment = fragment;
    }

    @Provides
    MainPagerAdapter provideFeedPagerAdapter() {
        return new MainPagerAdapter(fragment.getChildFragmentManager());
    }

    @Provides
    BookingsPagerAdapter providePagerAdapter() {
        return new BookingsPagerAdapter(fragment.getChildFragmentManager());
    }

    @Provides
    CarAdapter provideCarAdapter() {
        return new CarAdapter(new ArrayList<>());
    }

    @Provides
    BookingsAdapter provideMyBookingAdapter() {
        return new BookingsAdapter(new ArrayList<>());
    }

    @Provides
    GridAdapter provideGridAdapter() {
        return new GridAdapter(new ArrayList<>(), new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    GridLayoutManager provideGridLayoutManager() {
        return new GridLayoutManager(fragment.getActivity(), 5);
    }


    @Provides
    CarViewModel provideBlogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<CarViewModel> supplier = () -> new CarViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<CarViewModel> factory = new ViewModelProviderFactory<>(CarViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CarViewModel.class);
    }

    @Provides
    BookingViewModel providePickupViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<BookingViewModel> supplier = () -> new BookingViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<BookingViewModel> factory = new ViewModelProviderFactory<>(BookingViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BookingViewModel.class);
    }

    @Provides
    FinalBookingViewModel provideConfirmationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<FinalBookingViewModel> supplier = () -> new FinalBookingViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<FinalBookingViewModel> factory = new ViewModelProviderFactory<>(FinalBookingViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FinalBookingViewModel.class);
    }

    @Provides
    AirportViewModel provideAirportViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AirportViewModel> supplier = () -> new AirportViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AirportViewModel> factory = new ViewModelProviderFactory<>(AirportViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AirportViewModel.class);
    }

    @Provides
    LocalViewModel provideLocalViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<LocalViewModel> supplier = () -> new LocalViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<LocalViewModel> factory = new ViewModelProviderFactory<>(LocalViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(LocalViewModel.class);
    }

    @Provides
    OutstationViewModel provideOutstationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<OutstationViewModel> supplier = () -> new OutstationViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<OutstationViewModel> factory = new ViewModelProviderFactory<>(OutstationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(OutstationViewModel.class);
    }

    @Provides
    MainFragmentViewModel provideMainFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MainFragmentViewModel> supplier = () -> new MainFragmentViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MainFragmentViewModel> factory = new ViewModelProviderFactory<>(MainFragmentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MainFragmentViewModel.class);
    }

    @Provides
    BookingsFragmentViewModel provideBookingFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<BookingsFragmentViewModel> supplier = () -> new BookingsFragmentViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<BookingsFragmentViewModel> factory = new ViewModelProviderFactory<>(BookingsFragmentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BookingsFragmentViewModel.class);
    }

    @Provides
    BookingsListViewModel provideBookingListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<BookingsListViewModel> supplier = () -> new BookingsListViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<BookingsListViewModel> factory = new ViewModelProviderFactory<>(BookingsListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BookingsListViewModel.class);
    }

    @Provides
    ProfileViewModel provideProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ProfileViewModel> supplier = () -> new ProfileViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ProfileViewModel> factory = new ViewModelProviderFactory<>(ProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProfileViewModel.class);
    }

    @Provides
    TrackingViewModel provideTrackingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrackingViewModel> supplier = () -> new TrackingViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrackingViewModel> factory = new ViewModelProviderFactory<>(TrackingViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrackingViewModel.class);
    }

    @Provides
    CancelViewModel provideCancelViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<CancelViewModel> supplier = () -> new CancelViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<CancelViewModel> factory = new ViewModelProviderFactory<>(CancelViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CancelViewModel.class);
    }

    @Provides
    ModeViewModel provideModeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ModeViewModel> supplier = () -> new ModeViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ModeViewModel> factory = new ViewModelProviderFactory<>(ModeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ModeViewModel.class);
    }

    @Provides
    ReceiptViewModel provideReceiptViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ReceiptViewModel> supplier = () -> new ReceiptViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ReceiptViewModel> factory = new ViewModelProviderFactory<>(ReceiptViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ReceiptViewModel.class);
    }

    @Provides
    SupportViewModel provideSupportViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<SupportViewModel> supplier = () -> new SupportViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<SupportViewModel> factory = new ViewModelProviderFactory<>(SupportViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SupportViewModel.class);
    }
}

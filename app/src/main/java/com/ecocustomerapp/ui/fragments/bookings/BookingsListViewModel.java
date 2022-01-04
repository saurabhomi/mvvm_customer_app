package com.ecocustomerapp.ui.fragments.bookings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.databinding.FragmentBookingListBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.List;

public class BookingsListViewModel extends BaseViewModel<BookingsListNavigator, FragmentBookingListBinding> {

    protected final MutableLiveData<List<Booking>> liveData;

    public BookingsListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        liveData = new MutableLiveData<>();
    }

    public LiveData<List<Booking>> getData() {
        return liveData;
    }

}

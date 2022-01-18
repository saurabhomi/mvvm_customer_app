package com.ecocustomerapp.ui.fragments.bookings;

import android.content.Context;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.databinding.FragmentBookingsBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.google.android.material.tabs.TabLayout;

public class BookingsFragmentViewModel extends BaseViewModel<BookingsNavigator, FragmentBookingsBinding> {
    public BookingsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    void setViewPager(BookingsPagerAdapter mPagerAdapter, Context context, LiveDataListener listener) {
        getBookings();
        mPagerAdapter.setCount(2);


        getBinding().viewpager.setAdapter(mPagerAdapter);
        mPagerAdapter.upcoming.setListener(listener);
        mPagerAdapter.past.setListener(listener);

        getBinding().tabs.addTab(getBinding().tabs.newTab().setText(context.getString(R.string.past)));
        getBinding().tabs.addTab(getBinding().tabs.newTab().setText(context.getString(R.string.upcoming)));

        getBinding().viewpager.setOffscreenPageLimit(getBinding().tabs.getTabCount());

        getBinding().viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(getBinding().tabs));

        getBinding().tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getBinding().viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getBookings() {
        getCompositeDisposable().add(getDataManager()
                .getBookings(new PackageRequest().setPassengerId(getDataManager().getPassengerId().equals("0")?getDataManager().getBookerId():getDataManager().getPassengerId()).setPassengerType(getDataManager().getPassengerType()))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(listBookings -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(listBookings.getMessage());
                    ((BookingsPagerAdapter) getBinding().viewpager.getAdapter()).past.setLiveData(listBookings.getComplete());
                    ((BookingsPagerAdapter) getBinding().viewpager.getAdapter()).upcoming.setLiveData(listBookings.getUpcoming());
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }
}

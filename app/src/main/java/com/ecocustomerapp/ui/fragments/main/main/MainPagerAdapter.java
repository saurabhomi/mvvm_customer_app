package com.ecocustomerapp.ui.fragments.main.main;

import static com.ecocustomerapp.utils.AppConstants.AIRPORT;
import static com.ecocustomerapp.utils.AppConstants.LOCAL;
import static com.ecocustomerapp.utils.AppConstants.OUTSTATION;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.ui.fragments.main.airport.AirportFragment;
import com.ecocustomerapp.ui.fragments.main.local.LocalFragment;
import com.ecocustomerapp.ui.fragments.main.outstation.OutstationFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    private BookingRequest airportRequest;
    private BookingRequest localRequest;
    private BookingRequest outstationRequest;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mTabCount = 0;
    }


    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }

    public void setRequest(BookingRequest request) {
        switch (request.getPackageType()) {
            case AIRPORT:
                airportRequest = request;
                localRequest = new BookingRequest();
                outstationRequest = new BookingRequest();
                break;
            case LOCAL:
                airportRequest = new BookingRequest();
                localRequest = request;
                outstationRequest = new BookingRequest();
                break;
            case OUTSTATION:
                airportRequest = new BookingRequest();
                localRequest = new BookingRequest();
                outstationRequest = request;
                break;
            default:
                airportRequest = new BookingRequest();
                localRequest = new BookingRequest();
                outstationRequest = new BookingRequest();
                break;
        }

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return AirportFragment.newInstance(airportRequest);
            case 1:
                return LocalFragment.newInstance(localRequest);
            case 2:
                return OutstationFragment.newInstance(outstationRequest);
            default:
                return null;
        }
    }
}

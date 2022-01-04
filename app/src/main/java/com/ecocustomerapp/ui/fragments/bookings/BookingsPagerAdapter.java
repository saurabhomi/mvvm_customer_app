package com.ecocustomerapp.ui.fragments.bookings;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class BookingsPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    protected BookingsListFragment past = BookingsListFragment.newInstance(1);
    protected BookingsListFragment upcoming = BookingsListFragment.newInstance(2);


    public BookingsPagerAdapter(FragmentManager fragmentManager) {
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

    @Override
    public @NotNull Fragment getItem(int position) {
        if (position == 0) {
            return past;
        } else {
            return upcoming;
        }
    }
}

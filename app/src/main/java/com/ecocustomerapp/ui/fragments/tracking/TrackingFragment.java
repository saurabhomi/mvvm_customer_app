package com.ecocustomerapp.ui.fragments.tracking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.databinding.FragmentTrackingBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.google.android.gms.maps.SupportMapFragment;

import org.jetbrains.annotations.NotNull;

public class TrackingFragment extends BaseFragment<FragmentCallback, FragmentTrackingBinding, TrackingViewModel> implements TrackingNavigator {

    private FragmentTrackingBinding trackingBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Booking booking;
    private boolean back = false;

    public static TrackingFragment newInstance(Booking booking, boolean back) {
        TrackingFragment fragment = new TrackingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, booking);
        args.putBoolean(ARG_PARAM2, back);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tracking;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        if (getArguments() != null) {
            booking = (Booking) getArguments().getSerializable(ARG_PARAM1);
            back = getArguments().getBoolean(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trackingBinding = getViewDataBinding();
        mViewModel.setBinding(trackingBinding);
        mViewModel.showMap();
        mViewModel.getDriverDetails();
    }

    @Override
    protected void onBackPressed() {
        replaceFragment(BookingsFragment.newInstance(), BookingsFragment.class.getSimpleName());
    }


    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, false);
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            getBaseActivity().showLoading();
        } else {
            getBaseActivity().hideLoading();
        }

    }

    @Override
    public void handleError(Throwable throwable, int message) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void replaceFragment(Fragment fragment, String TAG) {
        getCallback().replaceFragment(fragment, TAG);
    }

    @Override
    public Fragment getUi() {
        return this;
    }

    @Override
    public String getBookingId() {
        return booking.getBookingNo();
    }

    @Override
    public SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    }

    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }
}
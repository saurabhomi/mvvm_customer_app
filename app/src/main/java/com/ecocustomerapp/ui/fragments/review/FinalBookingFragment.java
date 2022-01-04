package com.ecocustomerapp.ui.fragments.review;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.BookingResponse;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.FragmentFinalBookingBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.booking.BookingFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.ecocustomerapp.ui.fragments.confirmation.ReceiptFragment;

import org.jetbrains.annotations.NotNull;

public class FinalBookingFragment extends BaseFragment<FragmentCallback, FragmentFinalBookingBinding, FinalBookingViewModel> implements FinalBookingNavigator {
    FragmentFinalBookingBinding fragmentBinding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private BookingRequest request;
    private Package aPackage;

    private boolean back = false;

    public static FinalBookingFragment newInstance(BookingRequest request, Package aPackage, boolean back) {
        FinalBookingFragment fragment = new FinalBookingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, request);
        args.putSerializable(ARG_PARAM2, aPackage);
        args.putBoolean(ARG_PARAM3, back);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_final_booking;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding = getViewDataBinding();
        mViewModel.setBinding(fragmentBinding);
        fragmentBinding.setBooking(request);
        fragmentBinding.setAPackage(aPackage);
        mViewModel.setModSpinner(getActivity());
        mViewModel.setGuestDetails();

    }

    @Override
    protected void onBackPressed() {
        if (back) {
            replaceFragment(BookingsFragment.newInstance());
        } else {
            getCallback().replaceFragment(BookingFragment.newInstance(request, aPackage), BookingFragment.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request = (BookingRequest) getArguments().getSerializable(ARG_PARAM1);
            aPackage = (Package) getArguments().getSerializable(ARG_PARAM2);
            back = getArguments().getBoolean(ARG_PARAM3);
        }
        mViewModel.setNavigator(this);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, true);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        getCallback().replaceFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public boolean getBack() {
        return back;
    }

    @Override
    public void showBooking(BookingResponse response) {
        getCallback().replaceFragment(ReceiptFragment.newInstance(response.getBooking(), aPackage, false), ReceiptFragment.class.getSimpleName());
    }

    @Override
    public void showProgress() {
        getBaseActivity().showLoading();
    }

    @Override
    public void hideProgress() {
        getBaseActivity().hideLoading();
    }

    @Override
    public Fragment getUi() {
        return this;
    }
}
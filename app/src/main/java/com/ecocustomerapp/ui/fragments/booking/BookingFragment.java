package com.ecocustomerapp.ui.fragments.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.FragmentBookingBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.car.CarFragment;
import com.ecocustomerapp.ui.fragments.review.FinalBookingFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class BookingFragment extends BaseFragment<FragmentCallback, FragmentBookingBinding, BookingViewModel> implements BookingNavigator {

    FragmentBookingBinding pickupFragmentBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BookingRequest bookingRequest;
    private Package aPackage;

    public static BookingFragment newInstance(BookingRequest bookingRequest, Package aPackage) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, bookingRequest);
        args.putSerializable(ARG_PARAM2, aPackage);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onBackPressed() {
        getCallback().replaceFragment(CarFragment.newInstance(bookingRequest), CarFragment.class.getSimpleName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_booking;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pickupFragmentBinding = getViewDataBinding();
        mViewModel.setBinding(pickupFragmentBinding);
        pickupFragmentBinding.setBooking(bookingRequest);
        mViewModel.setUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingRequest = (BookingRequest) getArguments().getSerializable(ARG_PARAM1);
            aPackage = (Package) getArguments().getSerializable(ARG_PARAM2);
        }
        mViewModel.setNavigator(this);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, true);
    }

    @Override
    public void proceedToPay(BookingRequest request) {
        getCallback().replaceFragment(FinalBookingFragment.newInstance(request, aPackage, false), FinalBookingFragment.class.getSimpleName());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public BookingRequest getRequest() {
        if (bookingRequest != null && bookingRequest.getCaseCode() == null) {
            return bookingRequest.setCaseCode("");
        } else {
            return bookingRequest;
        }

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
    public void modify() {
        getCallback().replaceFragment(MainFragment.newInstance(bookingRequest), MainFragment.class.getSimpleName());
    }


}
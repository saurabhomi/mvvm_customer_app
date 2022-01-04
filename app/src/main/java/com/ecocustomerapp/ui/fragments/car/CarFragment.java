package com.ecocustomerapp.ui.fragments.car;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.FragmentCarBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.booking.BookingFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

public class CarFragment extends BaseFragment<FragmentCallback, FragmentCarBinding, CarViewModel> implements CarNavigator, CarAdapter.CarAdapterListener {
    FragmentCarBinding fragmentCarBinding;

    @Inject
    CarAdapter carAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private static final String ARG_PARAM1 = "param1";
    private BookingRequest bookingRequest;

    public static CarFragment newInstance(BookingRequest bookingRequest) {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, bookingRequest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);

    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    protected void onBackPressed() {
        getCallback().replaceFragment(MainFragment.newInstance(bookingRequest), MainFragment.class.getSimpleName());
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentCarBinding = getViewDataBinding();
        mViewModel.setBinding(fragmentCarBinding);
        fragmentCarBinding.setBooking(bookingRequest);
        setUpCarList();
        mViewModel.getCar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingRequest = (BookingRequest) getArguments().getSerializable(ARG_PARAM1);
        }
        mViewModel.setNavigator(this);
        carAdapter.setListener(this);
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
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCars(List<Package> packages) {
        carAdapter.addItems(packages);
    }

    private void setUpCarList() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentCarBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        fragmentCarBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentCarBinding.blogRecyclerView.setAdapter(carAdapter);
    }

    @Override
    public void onRetryClick() {
        mViewModel.getCar();
    }

    @Override
    public void onItemSelect(Package aPackage) {
        bookingRequest.setCarType(aPackage.getCar());
        bookingRequest.setCustomerId(mViewModel.getDataManager().getCustomerId());
        bookingRequest.setSalutation(mViewModel.getDataManager().getUserName());
        bookingRequest.setEmailAddress(mViewModel.getDataManager().getUserEmail());
        bookingRequest.setMobile(mViewModel.getDataManager().getUserMobile());
        bookingRequest.setPicklocationlat("27.2046");
        bookingRequest.setPicklocationlon("27.2046");
        bookingRequest.setDroplocationlat("27.2046");
        bookingRequest.setDroplocationlon("27.2046");
        bookingRequest.setPassengerId(mViewModel.getDataManager().getPassengerId());
        if (mViewModel.getDataManager().getCustomerType().equals("Booker")) {
            mViewModel.getPaymentMethod(bookingRequest, aPackage);
        } else {
            replaceNextFragment(BookingFragment.newInstance(bookingRequest, aPackage));
        }

    }

    @Override
    public void replaceNextFragment(Fragment fragment) {
        getCallback().replaceFragment(fragment, fragment.getClass().getSimpleName());
    }
}
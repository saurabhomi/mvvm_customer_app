package com.ecocustomerapp.ui.fragments.main.airport;

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
import com.ecocustomerapp.databinding.FragmentAirportBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.main.OnRoleChangeListener;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class AirportFragment extends BaseFragment<FragmentCallback, FragmentAirportBinding, AirportViewModel> implements AirportNavigator, OnRoleChangeListener {

    private static final String ARG = "arg";
    private BookingRequest request;
    FragmentAirportBinding airportFragmentBinding;
    private MainFragment mainFragment;


    public static AirportFragment newInstance(BookingRequest request) {
        AirportFragment fragment = new AirportFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG, request);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_airport;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);

    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        airportFragmentBinding = getViewDataBinding();
        airportFragmentBinding.setBooking(request);
        mViewModel.setBinding(airportFragmentBinding);
        mViewModel.setNavigator(this);
        mViewModel.getEntity(getActivity());
        mViewModel.setTripTypeSpinner();
        mViewModel.getTerminal();
        mViewModel.getBlockTime(mViewModel.getDataManager().getCustomerId());

//        airportFragmentBinding.txtPickupDateTime.setOnClickListener(this);
//        airportFragmentBinding.spinnerCountry.setEnabled(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = (BookingRequest) getArguments().getSerializable(ARG);
        mainFragment = (MainFragment) getParentFragment();
        mainFragment.setListener(this);

    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, false);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
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
    public void replaceFragment(Fragment fragment, String TAG) {
        getCallback().replaceFragment(fragment, TAG);
    }

    @Override
    public void onRoleChange(boolean check) {
        mViewModel.setEntityVisibility(check);
    }
}
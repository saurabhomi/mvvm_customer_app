package com.ecocustomerapp.ui.fragments.main.outstation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.databinding.FragmentOutstationBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.main.OnRoleChangeListener;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class OutstationFragment extends BaseFragment<FragmentCallback, FragmentOutstationBinding, OutstationViewModel> implements OutstationNavigator, OnRoleChangeListener {

    private FragmentOutstationBinding outstationBinding;

    private static final String ARG = "arg";
    private BookingRequest request;

    public static OutstationFragment newInstance(BookingRequest request) {
        OutstationFragment fragment = new OutstationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG, request);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_outstation;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);

    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this,false);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        outstationBinding = getViewDataBinding();
        outstationBinding.setBooking(request);
        mViewModel.setBinding(outstationBinding);
        mViewModel.setNavigator(this);
        mViewModel.getCities();
        mViewModel.getEntity(getContext());
        mViewModel.getBlockTime(mViewModel.getDataManager().getCustomerId());

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = (BookingRequest) getArguments().getSerializable(ARG);
        MainFragment mainFragment = (MainFragment) getParentFragment();
        mainFragment.setListener(this);

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
    public void replaceFragment(Fragment fragment, String name) {
        getCallback().replaceFragment(fragment, name);
    }

    @Nullable
    @Override
    public Context getContext() {
        return getBaseActivity();
    }


    @Override
    public void onRoleChange(boolean check) {
        mViewModel.setEntityVisibility(check);
    }
}
package com.ecocustomerapp.ui.fragments.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.FragmentSupportBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.confirmation.ReceiptFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class SupportFragmentFragment extends BaseFragment<FragmentCallback, FragmentSupportBinding, SupportViewModel> implements SupportFragmentNavigator {


    FragmentSupportBinding supportBinding;

    private boolean back;
    private String fragment;
    private Booking response;
    private Package aPackage;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    public static SupportFragmentFragment newInstance(boolean isBack, String name, Booking response, Package aPackage, boolean back) {
        SupportFragmentFragment fragment = new SupportFragmentFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isBack);
        args.putString(ARG_PARAM2, name);
        args.putSerializable(ARG_PARAM3, response);
        args.putSerializable(ARG_PARAM4, aPackage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_support;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supportBinding = getViewDataBinding();
        mViewModel.setBinding(supportBinding);

    }

    @Override
    protected void onBackPressed() {
        replaceFragment();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, back);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            back = getArguments().getBoolean(ARG_PARAM1);
            fragment = getArguments().getString(ARG_PARAM2);
            response = (Booking) getArguments().getSerializable(ARG_PARAM3);
            aPackage = (Package) getArguments().getSerializable(ARG_PARAM4);
            getCallback().setBack(back);
        }
        mViewModel.setNavigator(this);
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void replaceFragment() {
        getCallback().replaceFragment(fragment.equals(ReceiptFragment.class.getSimpleName()) ? ReceiptFragment.newInstance(response, aPackage, true) : MainFragment.newInstance(new BookingRequest()), fragment);
    }

    @Override
    public void makeCall() {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:01140794079")));
    }
}
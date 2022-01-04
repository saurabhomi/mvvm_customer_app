package com.ecocustomerapp.ui.fragments.confirmation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.data.model.api.BookingResponse;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.FragmentReceiptBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiptFragment extends BaseFragment<FragmentCallback, FragmentReceiptBinding, ReceiptViewModel> implements ReceiptNavigator {

    FragmentReceiptBinding fragmentReceiptBinding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private Booking response;
    private Package aPackage;
    private boolean back;


    public static ReceiptFragment newInstance(Booking response, Package aPackage, boolean back) {
        ReceiptFragment fragment = new ReceiptFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, response);
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
        return R.layout.fragment_receipt;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentReceiptBinding = getViewDataBinding();
        mViewModel.setBinding(fragmentReceiptBinding);
        fragmentReceiptBinding.setBooking(response);
        fragmentReceiptBinding.setAPackage(aPackage);

    }

    @Override
    protected void onBackPressed() {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            response = (Booking) getArguments().getSerializable(ARG_PARAM1);
            aPackage = (Package) getArguments().getSerializable(ARG_PARAM2);
            back = getArguments().getBoolean(ARG_PARAM3);
        }
        mViewModel.setNavigator(this);
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        getCallback().replaceFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public Fragment getUi() {
        return this;
    }
}
package com.ecocustomerapp.ui.fragments.main.local;

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
import com.ecocustomerapp.databinding.FragmentLocalBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.main.OnRoleChangeListener;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class LocalFragment extends BaseFragment<FragmentCallback, FragmentLocalBinding, LocalViewModel> implements LocalNavigator, View.OnClickListener, OnRoleChangeListener {

    FragmentLocalBinding fragmentLocalBinding;
    private static final String ARG = "arg";
    private BookingRequest request;

    public static LocalFragment newInstance(BookingRequest request) {
        LocalFragment fragment = new LocalFragment();
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
        return R.layout.fragment_local;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentLocalBinding = getViewDataBinding();
        fragmentLocalBinding.setBooking(request);
        mViewModel.setBinding(fragmentLocalBinding);
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
    public void onClick(View view) {

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
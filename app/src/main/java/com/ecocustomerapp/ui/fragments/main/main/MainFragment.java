package com.ecocustomerapp.ui.fragments.main.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.databinding.FragmentMainBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.main.OnRoleChangeListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainFragment extends BaseFragment<FragmentCallback, FragmentMainBinding, MainFragmentViewModel> implements MainFragmentNavigator {

    private FragmentMainBinding fragmentMainBinding;
    private final static String ARG = "arg";
    private BookingRequest request;
    private ArrayList<OnRoleChangeListener> listener;

    @Inject
    MainPagerAdapter mPagerAdapter;

    public static MainFragment newInstance(BookingRequest request) {
        MainFragment fragment = new MainFragment();
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
        return R.layout.fragment_main;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this, false);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentMainBinding = getViewDataBinding();
        mViewModel.setBinding(fragmentMainBinding);
        mViewModel.setViewPager(mPagerAdapter, getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new ArrayList<>();
        request = (BookingRequest) getArguments().getSerializable(ARG);
        mViewModel.setNavigator(this);

    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public BookingRequest getBookingRequest() {
        return request;
    }

    @Override
    public void onCheckChange(boolean check) {
        if (listener != null) {
            for (OnRoleChangeListener listener : listener) {
                listener.onRoleChange(check);
            }

            mViewModel.setPassengerType(check, getBaseActivity());
            fragmentMainBinding.swc.setChecked(check);
        }
    }

    @Override
    public void replaceFragment(Fragment fragment, String TAG) {
        getCallback().replaceFragment(fragment, TAG);
    }


    public void setListener(OnRoleChangeListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainFragment.this.listener.add(listener);
                boolean type = mViewModel.getDataManager().getCustomerType().equals("Corporate") || mViewModel.getDataManager().getCustomerType().equals("Booker");
                onCheckChange(type);
            }
        }, 100);


//        listener.onRoleChange(fragmentMainBinding.swc.isChecked());
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

}
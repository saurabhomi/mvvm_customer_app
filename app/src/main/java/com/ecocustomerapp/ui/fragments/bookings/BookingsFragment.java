package com.ecocustomerapp.ui.fragments.bookings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.databinding.FragmentBookingsBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingsFragment extends BaseFragment<FragmentCallback, FragmentBookingsBinding, BookingsFragmentViewModel> implements BookingsNavigator, LiveDataListener {

    private FragmentBookingsBinding bookingBinding;

    @Inject
    BookingsPagerAdapter pagerAdapter;


    public BookingsFragment() {
        // Required empty public constructor
    }

    public static BookingsFragment newInstance() {
        return new BookingsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingBinding = getViewDataBinding();
        mViewModel.setBinding(bookingBinding);
        mViewModel.setViewPager(pagerAdapter, getActivity(), this);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this,false);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void getLiveData() {
        mViewModel.getBookings();
    }
}
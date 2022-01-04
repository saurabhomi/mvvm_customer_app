package com.ecocustomerapp.ui.fragments.cancel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.databinding.FragmentCancelBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.ecocustomerapp.ui.fragments.review.FinalBookingFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelFragment extends BaseFragment<FragmentCallback, FragmentCancelBinding, CancelViewModel> implements CancelNavigator {

    FragmentCancelBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private Booking booking;
    private boolean back;
    private String type;


    public CancelFragment() {
        // Required empty public constructor
    }

    public static CancelFragment newInstance(Booking Booking, boolean back, String type) {
        CancelFragment fragment = new CancelFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, Booking);
        args.putBoolean(ARG_PARAM2, back);
        args.putString(ARG_PARAM3, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cancel;
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
        if (type.equals(BookingsFragment.class.getSimpleName())) {
            replaceNextFragment(BookingsFragment.newInstance());
        } else if (type.equals(FinalBookingFragment.class.getSimpleName())) {
//            replaceNextFragment(FinalBookingFragment.newInstance(booking, back));
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        mViewModel.setBinding(binding);
        binding.setMyBooking(booking);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booking = (Booking) getArguments().getSerializable(ARG_PARAM1);
            back = getArguments().getBoolean(ARG_PARAM2);
            type = getArguments().getString(ARG_PARAM3);
        }
        mViewModel.setNavigator(this);
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
    public void replaceNextFragment(Fragment fragment) {
        getCallback().replaceFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
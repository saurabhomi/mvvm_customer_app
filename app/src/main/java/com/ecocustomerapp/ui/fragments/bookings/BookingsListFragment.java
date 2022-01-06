package com.ecocustomerapp.ui.fragments.bookings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.databinding.FragmentBookingListBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;
import com.ecocustomerapp.ui.fragments.cancel.CancelFragment;
import com.ecocustomerapp.ui.fragments.tracking.TrackingFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 */
public class BookingsListFragment extends BaseFragment<FragmentCallback, FragmentBookingListBinding, BookingsListViewModel> implements BookingsListNavigator, BookingsAdapter.BookingsAdapterListener {

    private static final String TYPE = "type";
    @Inject
    BookingsAdapter adapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private LiveDataListener listener;

    FragmentBookingListBinding binding;

    private int type;

    public BookingsListFragment() {
    }

    public static BookingsListFragment newInstance(int type) {
        BookingsListFragment fragment = new BookingsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    protected void setListener(LiveDataListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_booking_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        mViewModel.setBinding(binding);
        setList();
    }

    protected BookingsListFragment setLiveData(List<Booking> liveData) {
        mViewModel.liveData.setValue(liveData);
        return this;
    }

    private void setList() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(mLayoutManager);
        binding.list.setItemAnimator(new DefaultItemAnimator());
        binding.list.setNestedScrollingEnabled(false);
        binding.list.setAdapter(adapter.setViewType(type));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type = getArguments().getInt(TYPE);
        mViewModel.setNavigator(this);
        adapter.setListener(this);
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
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context);
    }

    @Override
    protected void onBackPressed() {

    }


    @Override
    public void onRetryClick() {
        listener.getLiveData();
    }

    @Override
    public void onItemSelect(Booking booking) {
        if (type == 1) {
            booking.setEnable(false);
        } else if (booking.getStatus() != null && booking.getStatus().equalsIgnoreCase("Cancelled")) {
            booking.setEnable(false);
        }
//        getCallback().replaceFragment(FinalBookingFragment.newInstance(booking, null,true), FinalBookingFragment.class.getSimpleName());
    }

    @Override
    public void onCancel(Booking booking) {
        getCallback().replaceFragment(CancelFragment.newInstance(booking, false, BookingsFragment.class.getSimpleName()), CancelFragment.class.getSimpleName());
    }

    @Override
    public void onTrack(Booking booking) {
        getCallback().replaceFragment(TrackingFragment.newInstance(booking, false), TrackingFragment.class.getSimpleName());
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
    public boolean enable() {
        return type == 2;
    }
}
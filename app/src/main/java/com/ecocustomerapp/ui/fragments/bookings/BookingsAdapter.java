package com.ecocustomerapp.ui.fragments.bookings;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.databinding.BookingListItemBinding;
import com.ecocustomerapp.databinding.ItemBookingEmptyViewBinding;
import com.ecocustomerapp.databinding.ItemUpcomingBinding;
import com.ecocustomerapp.ui.base.BaseViewHolder;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_PAST = 1;

    public static final int VIEW_TYPE_UPCOMING = 2;

    private final List<Booking> mValues;
    private int type;

    private BookingsAdapterListener mListener;

    public BookingsAdapter(List<Booking> items) {
        mValues = items;
    }

    @Override
    public int getItemCount() {
        if (mValues != null && mValues.size() > 0) {
            return mValues.size();
        } else {
            return 1;
        }
    }

    protected BookingsAdapter setViewType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues == null || mValues.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return type;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_PAST:
                BookingListItemBinding itemBinding = BookingListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new BookingsViewHolder(itemBinding);
            case VIEW_TYPE_UPCOMING:
                ItemUpcomingBinding upcomingBinding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new BookingsViewHolder(upcomingBinding);
            default:
                ItemBookingEmptyViewBinding emptyViewBinding = ItemBookingEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<Booking> bookings) {
        mValues.addAll(bookings);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mValues.clear();
    }

    public void setListener(BookingsAdapterListener listener) {
        this.mListener = listener;
    }

    public interface BookingsAdapterListener {

        void onRetryClick();

        void onItemSelect(Booking Booking);

        void onCancel(Booking booking);
    }


    public class BookingsViewHolder extends BaseViewHolder implements BookingsItemViewModel.BookingsItemViewModelListener {
        BookingListItemBinding binding;
        ItemUpcomingBinding upcomingBinding;
        BookingsItemViewModel viewModel;

        public BookingsViewHolder(BookingListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public BookingsViewHolder(ItemUpcomingBinding binding) {
            super(binding.getRoot());
            this.upcomingBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Booking mPackage = mValues.get(position);
            if (binding != null) {
                viewModel = new BookingsItemViewModel(mPackage, this);
                binding.setViewModel(viewModel);
                binding.executePendingBindings();
            } else if (upcomingBinding != null) {
                viewModel = new BookingsItemViewModel(mPackage, this);
                upcomingBinding.setViewModel(viewModel);
                upcomingBinding.executePendingBindings();
            }


            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.


        }

        @Override
        public void onItemClick(Booking Booking) {
            mListener.onItemSelect(Booking);
        }

        @Override
        public void onCancel(Booking bookings) {
            mListener.onCancel(bookings);

        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements EmptyBookingItemViewModel.BookingsEmptyItemViewModelListener {

        private final ItemBookingEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBookingEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            EmptyBookingItemViewModel emptyItemViewModel = new EmptyBookingItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}
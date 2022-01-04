/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp.ui.fragments.car;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.databinding.ItemCarEmptyViewBinding;
import com.ecocustomerapp.databinding.ItemCarViewBinding;
import com.ecocustomerapp.ui.base.BaseViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class CarAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private final List<Package> packages;

    private CarAdapterListener mListener;

    public CarAdapter(List<Package> packageList) {
        this.packages =packageList;
    }

    @Override
    public int getItemCount() {
        if (packages != null && packages.size() > 0) {
            return packages.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (packages != null && !packages.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemCarViewBinding blogViewBinding = ItemCarViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CarViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemCarEmptyViewBinding emptyViewBinding = ItemCarEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<Package> blogList) {
        packages.addAll(blogList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        packages.clear();
    }

    public void setListener(CarAdapterListener listener) {
        this.mListener = listener;
    }

    public interface CarAdapterListener {

        void onRetryClick();

        void onItemSelect(Package aPackage);
    }

    public class CarViewHolder extends BaseViewHolder implements CarItemViewModel.CarItemViewModelListener {

        private final ItemCarViewBinding mBinding;

        private CarItemViewModel mCarItemViewModel;

        public CarViewHolder(ItemCarViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Package mPackage = packages.get(position);
            mCarItemViewModel = new CarItemViewModel(mPackage, this);
            mBinding.setViewModel(mCarItemViewModel);
            mBinding.setMPackage(mPackage);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(Package aPackage) {
            mListener.onItemSelect(aPackage);

        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements CarEmptyItemViewModel.BlogEmptyItemViewModelListener {

        private final ItemCarEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemCarEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            CarEmptyItemViewModel emptyItemViewModel = new CarEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}
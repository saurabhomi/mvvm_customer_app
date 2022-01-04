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

package com.ecocustomerapp.ui.fragments.review;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ecocustomerapp.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class GridAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 1;

    private final List<String> strings;
    private final List<Integer> integers;

    private BlogAdapterListener mListener;

    public GridAdapter(List<String> strings, List<Integer> integers) {
        this.strings = strings;
        this.integers = integers;
    }

    @Override
    public int getItemCount() {

        return strings.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    public void addItems(List<String> strings, List<Integer> integers) {
        this.strings.addAll(strings);
        this.integers.addAll(integers);
        notifyDataSetChanged();
    }

    public void clearItems() {
        strings.clear();
        integers.clear();
    }

    public void setListener(BlogAdapterListener listener) {
        this.mListener = listener;
    }

    public interface BlogAdapterListener {

        void onRetryClick();
    }

    public class GridViewHolder extends BaseViewHolder implements GridItemViewModel.GridItemViewModelListener {


        private GridItemViewModel mCarItemViewModel;

        public GridViewHolder() {
            super(null);

        }

        @Override
        public void onBind(int position) {
            final String name = strings.get(position);
            final Integer image = integers.get(position);
            mCarItemViewModel = new GridItemViewModel(name, image, this);
        }

        @Override
        public void onItemClick(String s, Integer i) {

        }
    }
}
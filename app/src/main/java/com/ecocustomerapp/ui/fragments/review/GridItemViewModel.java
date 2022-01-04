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

import androidx.databinding.ObservableField;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class GridItemViewModel {

    public final ObservableField<String> name;

    public final ObservableField<Integer> image;

    public final GridItemViewModelListener mListener;

    private final String string;
    private final Integer integer;

    public GridItemViewModel(String string, Integer integer, GridItemViewModelListener listener) {
        this.string = string;
        this.integer = integer;
        this.mListener = listener;
        name = new ObservableField<>(string);
        image = new ObservableField<>(integer);
    }

    public void onItemClick() {
        mListener.onItemClick(string, integer);
    }

    public interface GridItemViewModelListener {

        void onItemClick(String name, Integer integer);
    }
}

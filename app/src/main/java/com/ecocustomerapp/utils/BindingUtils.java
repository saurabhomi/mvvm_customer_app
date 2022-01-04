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

package com.ecocustomerapp.utils;

import android.content.Context;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Booking;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.ui.fragments.bookings.BookingsAdapter;
import com.ecocustomerapp.ui.fragments.car.CarAdapter;
import com.ecocustomerapp.ui.fragments.review.GridAdapter;

import java.util.List;

/**
 * Created by Saurabh Srivastava on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addCarItems(RecyclerView recyclerView, List<Package> packages) {
        CarAdapter adapter = (CarAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(packages);
        }
    }
    @BindingAdapter({"bookings_adapter"})
    public static void addBookingList(RecyclerView recyclerView, List<Booking> Booking) {
        BookingsAdapter adapter = (BookingsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(Booking);
        }
    }

    @BindingAdapter({"strings", "images"})
    public static void addGridItems(RecyclerView recyclerView, List<String> strings, List<Integer> integers) {
        GridAdapter adapter = (GridAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(strings, integers);
        }
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url!=null && url.contains(".png")) {
            Glide.with(context).load(url).placeholder(R.drawable.sedan).into(imageView);
        } else if (url!=null && !url.trim().equalsIgnoreCase("") ) {
            byte[] imageByteArray = Base64.decode(url, Base64.DEFAULT);
            Glide.with(context).asBitmap().load(imageByteArray).placeholder(R.drawable.sedan).into(imageView);
        }
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(CustomFontFamily.getInstance().getFont(fontName,textView.getContext()));
    }


    @BindingAdapter({"binding"})
    public static void bindEditText(EditText view, final ObservableField<String> observableField) {
        Pair<ObservableField<String>, TextWatcherController> pair = (Pair<ObservableField<String>, TextWatcherController>) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != observableField) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherController watcher = new TextWatcherController() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    observableField.set(s.toString());
                }
            };
            view.setTag(R.id.bound_observable, new Pair<>(observableField, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = observableField.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }
}

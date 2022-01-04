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

import androidx.databinding.ObservableField;

import com.ecocustomerapp.data.model.api.Package;

/**
 * Created by Saurabh Srivastava on 10/07/17.
 */

public class CarItemViewModel {

    public final ObservableField<String> car;

    public final ObservableField<String> car_category;

    public final ObservableField<String> package_type;

    public final ObservableField<String> rate;

    public final CarItemViewModelListener mListener;

    public final ObservableField<String> extra_km_rate;

    public final ObservableField<String> extra_hr_rate;

    public final ObservableField<String> per_day;

    public final ObservableField<String> night_charges;

    public final ObservableField<String> sitting_capacity;

    public final ObservableField<String> image;

    private final Package aPackage;

    public CarItemViewModel(Package aPackage, CarItemViewModelListener listener) {
        this.aPackage = aPackage;
        this.mListener = listener;
        rate = new ObservableField<>(aPackage.getRate().equals("0.0")?"":aPackage.getRate());
        if (aPackage.getPackage_type().equals("Outstation")) {
            extra_km_rate = new ObservableField<>(aPackage.getExtra_km_rate() + "/KM");
        } else {
            extra_km_rate = new ObservableField<>(aPackage.getExtra_km_rate() + "/km after "+ aPackage.getMinKM()+" kms");
        }
        extra_hr_rate = new ObservableField<>("Extra : " + aPackage.getExtra_hr_Rate() + "/Hr");
        per_day = new ObservableField<>(aPackage.getMinKM()+" Kms included");
        night_charges = new ObservableField<>("Night Charge : " + aPackage.getNightCharges());
        car = new ObservableField<>(aPackage.getCar());
        package_type = new ObservableField<>(aPackage.getPackage_type());
        car_category = new ObservableField<>(aPackage.getCar_category());
        sitting_capacity = new ObservableField<>(aPackage.getSitting_capacity() + " + Driver | 2 Bags");
        image = new ObservableField<>(aPackage.getImage());
    }

    public void onItemClick() {
        mListener.onItemClick(aPackage);
    }

    public interface CarItemViewModelListener {

        void onItemClick(Package aPackage);
    }
}

/*
 *  Copyright (C) 2017 JAJ Technologies Private Limited
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://jajtechnologies.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp.ui.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.os.IBinder;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.Login.LoginBody;
import com.ecocustomerapp.databinding.ActivityMainBinding;
import com.ecocustomerapp.di.transactor.SafeFragmentTransaction;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.location.GpsUtils;
import com.ecocustomerapp.utils.location.LocationUpdatesService;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.ecocustomerapp.utils.rx.permission.Permission;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;


/**
 * Created by Saurabh Srivastava on 07/06/19.
 */

public class MainViewModel extends BaseViewModel<MainNavigator, ActivityMainBinding> implements GpsUtils.GpsStatusListener {

    public final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> appVersion = new ObservableField<>();

    @NonNull
    private final ObservableField<Boolean> isShowing = new ObservableField<>(false);
    private final ObservableField<Boolean> isVoidDialogShowing = new ObservableField<>(false);

    private MainActivity activity;
    private Animatable animatable;
    private LocationUpdatesService locationService;
    private boolean locationServiceBound;

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public ObservableField<String> getAppVersion() {
        return appVersion;
    }


    void onNavMenuCreated() {

        ((TextView) getBinding().navView.getHeaderView(0).findViewById(R.id.name)).setText(getDataManager().getUserName());
        ((TextView) getBinding().navView.getHeaderView(0).findViewById(R.id.email)).setText(getDataManager().getUserEmail());
    }

    private void logOut() {

    }


    void transitFragment(MainActivity activity, Fragment fragment) {
        getCompositeDisposable()
                .add(activity.getSafeFragmentTransaction().registerFragmentTransition(fragmentManager -> {
                    fragmentManager
                            .beginTransaction()
                            .disallowAddToBackStack()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                            .replace(getBinding().appBar.contentHome.container.getId(), fragment, fragment.getClass().getSimpleName())
                            .commitNow();
                    fragmentManager.executePendingTransactions();
                })
                        .getFragmentEventObservable()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().trampoline())
                        .subscribe(integer -> {

                        }, throwable -> {
                            Timber.e(" On Fragment replaced %s ", throwable.getMessage());
                        }));
    }

    void removeFragment(MainActivity activity, String TAG) {
        getCompositeDisposable().add(activity.getSafeFragmentTransaction().registerFragmentTransition(new SafeFragmentTransaction.TransitionHandler() {
            @Override
            public void onTransitionAvailable(FragmentManager fragmentManager) {
                Fragment fragment = fragmentManager.findFragmentByTag(TAG);
                if (fragment != null) {

                    fragmentManager.beginTransaction()
                            .disallowAddToBackStack()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                            .remove(fragment)
                            .commitNow();
                    fragmentManager.executePendingTransactions();
                }
            }
        }).getFragmentEventObservable().subscribe(integer -> {

        }, throwable -> {
        }));
    }

    void logout() {
        getDataManager().clearData();
    }


    private final ServiceConnection locationServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            locationService = binder.getService();
            startLocationUpdate();
            locationServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            stopLocationUpdate();
            Timber.i("%s LocationServiceDisconnected", name.toString());
            locationService = null;
            locationServiceBound = false;
        }
    };

    void checkGPS(MainActivity activity) {
        this.activity = activity;
        new GpsUtils(activity).turnGPSOn(this);

    }

    public void startLocationUpdate() {
        if (!granted()) {
            seedPermission();
        } else {
            locationService.requestLocationUpdates();
        }
    }


    /**
     * Returns the current state of the permissions needed.
     */
    private boolean granted() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getNavigator().getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void onStop(MainActivity activity) {
        if (locationServiceBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            activity.unbindService(locationServiceConnection);
            locationServiceBound = false;
        }
    }

    protected void onStart(MainActivity activity) {
        checkGPS(activity);
    }

    public void stopLocationUpdate() {
        locationService.removeLocationUpdates();
    }


    private void seedPermission() {
        // Ask for permissions when button is clicked
        getCompositeDisposable().add(getDataManager().seedLocationPermission(getNavigator().getContext()).subscribe((Permission permission) -> {

                    if (permission.granted) {

                        locationService.requestLocationUpdates();

                    }
                },
                t -> Toast.makeText(getNavigator().getContext(), t.getMessage(), Toast.LENGTH_SHORT).show(),
                () -> Timber.i("OnComplete")));
    }


    @Override
    public void gpsStatus(boolean isGPSEnable) {
        if (!isGPSEnable) {
            checkGPS(activity);
        } else {
            activity.bindService(new Intent(activity, LocationUpdatesService.class), locationServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }
}

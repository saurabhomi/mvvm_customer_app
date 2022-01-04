package com.ecocustomerapp.utils.location;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ecocustomerapp.BuildConfig;
import com.ecocustomerapp.EcoApplication;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.di.component.DaggerFragmentComponent;
import com.ecocustomerapp.di.component.DaggerServiceComponent;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.di.component.ServiceComponent;
import com.ecocustomerapp.di.module.FragmentModule;
import com.ecocustomerapp.di.module.ServiceModule;
import com.ecocustomerapp.ui.main.MainActivity;
import com.ecocustomerapp.ui.splash.SplashActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LocationUpdatesService extends Service {

    private static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";
    public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    /**
     * The name of the channel for notifications.
     */
    private static final String CHANNEL_ID = "channel_01";
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final int NOTIFICATION_ID = 12345678;
    private static final String BROADCAST = "broadcast";
    private static final String POWER_SAVING = "Battery saving";
    private final IBinder mBinder = new LocalBinder();
    @Inject
    DataManager dataManager;
    boolean isLocationManagerUpdatingLocation;
    private CompositeDisposable disposable;
    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;
    private NotificationManager mNotificationManager;
    /**
     * Contains parameters used by {@link FusedLocationProviderClient}.
     */
    private LocationRequest mLocationRequest;
    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * FragmentCallback for changes in location.
     */
    private LocationCallback mLocationCallback;
    private Handler mServiceHandler;
    /**
     * The current location.
     */
    private Location mLocation;
    private BatteryManager bm;
    private PowerManager powerManager;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
        }
    };
    private NetworkRequest networkRequest;
    private ConnectivityManager connectivityManager;
    private LocationManager locationManager;
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                dataManager.setGPSStatus(true);
                notifyLocationProviderStatusUpdated(true);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                dataManager.setGPSStatus(false);
                notifyLocationProviderStatusUpdated(false);
                startActivity();
            }

        }
    };

    public LocationUpdatesService() {
    }

    private void performDependencyInjection(ServiceComponent buildComponent){
        buildComponent.inject(this);

    }


    private ServiceComponent getBuildComponent() {
        return DaggerServiceComponent.builder()
                .appComponent(((EcoApplication)(getApplicationContext())).appComponent)
                .serviceModule(new ServiceModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        performDependencyInjection(getBuildComponent());
        isLocationManagerUpdatingLocation = false;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location.getAccuracy() < 100) {
                    onNewLocation(location);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };

        createLocationRequest();
        getLastLocation();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setSound(Uri.parse("a"), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            mChannel.setShowBadge(false);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.i("Location Service started");
        // Tells the system to try to recreate the service after it has been killed.
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (MainBaseActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        Timber.i("in onBind()");
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (MainBaseActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Timber.i("in onRebind()");
        stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Timber.i("Last client unbound from service");

        // Called when the last client (MainBaseActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainBaseActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && dataManager.getRequestingLocationUpdate()) {
            Timber.i("Starting foreground service");

            startForeground(NOTIFICATION_ID, getNotification());
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(null);
        Timber.i("Location service destroyed");
        startActivity();
        super.onDestroy();
    }

    private void startActivity() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public synchronized void requestLocationUpdates() {
        if (!this.isLocationManagerUpdatingLocation) {
            isLocationManagerUpdatingLocation = true;
            Timber.d("Requesting location updates");

            startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
            try {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()).addOnCompleteListener(task -> {
                    dataManager.setRequestingLocationUpdate(task.isSuccessful());
                    bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
                    powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    networkRequest = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager != null) {
                        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
                    }
                    registerScreenReceiver();
                    registerProviderListener();
                });

            } catch (SecurityException unlikely) {
                dataManager.setRequestingLocationUpdate(false);
                Timber.e("Lost location permission. Could not request updates.");
            }
        }
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * {SecurityException}.
     */
    public synchronized void removeLocationUpdates() {
        if (this.isLocationManagerUpdatingLocation) {
            Timber.i("Removing location updates");
            try {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataManager.setRequestingLocationUpdate(!task.isSuccessful());
                        isLocationManagerUpdatingLocation = false;
                        bm = null;
                        powerManager = null;
                        connectivityManager.unregisterNetworkCallback(networkCallback);
                        networkRequest = null;
                        networkCallback = null;
                        connectivityManager = null;
                        unregisterScreenReceiver();
                        unregisterLocationProviderListener();
                    }
                });

            } catch (SecurityException unlikely) {
                dataManager.setRequestingLocationUpdate(true);
                Timber.e("Lost location permission. Could not remove updates.");
            }
        }
    }

    /**
     * Returns the {@link NotificationCompat} used as part of the foreground service.
     */
    private Notification getNotification() {

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(activityPendingIntent)
                .setContentText(getString(R.string.app_name) + (mLocation != null ? mLocation.getAccuracy() : "0"))
                .setContentTitle(getString(R.string.app_name))
                .setOngoing(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_bird).extractAlpha())
                .setSmallIcon(R.drawable.ic_eco)
                .setColor(getResources().getColor(R.color.dark_gray))
                .setPriority(Notification.PRIORITY_HIGH)
                .setTicker(getString(R.string.app_name))
                .setSound(null)
                .setWhen(System.currentTimeMillis());

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        } else {
            builder.setSound(null);
        }


        return builder.build();
    }

    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLocation = task.getResult();
                            dataManager.setGPSStatus(true);
                        } else {
                            Timber.w("Failed to get location.");
                        }
                    });
        } catch (SecurityException unlikely) {
            Timber.e("Lost location permission.");
        }
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private synchronized void onNewLocation(Location location) {
        Timber.i("Fused location: %s", location);
        mLocation = location;


        //Save current location to shared preference for quick use in WEB API
        dataManager.setCurrentLocation(location);


        // Notify anyone listening for broadcasts about the new location.
//        Intent intent = new Intent(ACTION_BROADCAST);
//        intent.putExtra(EXTRA_LOCATION, location);
//        intent.putExtra(EXTRA_STRING, TAG);
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        // Update notification content if running as a foreground service.
        if (serviceIsRunningInForeground(this)) {
            mNotificationManager.notify(NOTIFICATION_ID, getNotification());
        }
    }

    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : Objects.requireNonNull(manager).getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    private void registerScreenReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST));
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterScreenReceiver() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private String getBatteryCapacity() {
        int capacity = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return String.valueOf(capacity);
    }

    private String getPowerSavingStatus() {
        return powerManager.isPowerSaveMode() ? POWER_SAVING : "";
    }


    private synchronized void notifyLocationProviderStatusUpdated(final boolean isLocationProviderAvailable) {
        //Broadcast location provider status change here

    }


    private void registerProviderListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
        }
    }

    private void unregisterLocationProviderListener() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }
}

package com.ecocustomerapp.utils.maps;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

public class AnimateCameraFunction implements Function<GoogleMap, Observable<Location>> {


    //    private final Marker marker;
    private final Location location;
    private final LatLng position;

    AnimateCameraFunction(Location location) {
//        this.marker = marker;
        this.location = location;
        this.position = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public Observable<Location> apply(GoogleMap googleMap) throws Exception {
        return Observable.create(new ObservableOnSubscribe<Location>() {
            @Override
            public void subscribe(ObservableEmitter<Location> emitter) throws Exception {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().bearing(location.getBearing()).tilt(45f).target(position).zoom(14f).build()), 1000, null);
                emitter.onNext(location);
            }
        });
    }
}

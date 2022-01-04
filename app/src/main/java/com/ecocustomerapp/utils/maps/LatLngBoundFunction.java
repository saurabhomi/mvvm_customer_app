package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

public class LatLngBoundFunction implements Function<GoogleMap, Observable<LatLngBounds>> {

    private final LatLngBounds bounds;
    private final int width;
    private final int height;


    LatLngBoundFunction(LatLngBounds bounds, int width, int height) {
        this.bounds = bounds;
        this.width = width;
        this.height = height;
    }

    @Override
    public Observable<LatLngBounds> apply(GoogleMap googleMap) throws Exception {
        return Observable.create(new ObservableOnSubscribe<LatLngBounds>() {
            @Override
            public void subscribe(ObservableEmitter<LatLngBounds> emitter) throws Exception {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 100), 1000, null);
            }
        });
    }
}

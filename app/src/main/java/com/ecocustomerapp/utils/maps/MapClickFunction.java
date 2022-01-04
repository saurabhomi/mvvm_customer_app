package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class MapClickFunction implements Function<GoogleMap, io.reactivex.Observable<LatLng>> {


    @Override
    public io.reactivex.Observable<LatLng> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<LatLng>() {
            @Override
            public void subscribe(ObservableEmitter<LatLng> emitter) throws Exception {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng value) {
                        emitter.onNext(value);
                    }
                });
            }
        });
    }
}

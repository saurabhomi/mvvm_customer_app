package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PointOfInterest;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class POIClickFunction implements Function<GoogleMap, io.reactivex.Observable<PointOfInterest>> {


    @Override
    public io.reactivex.Observable<PointOfInterest> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<PointOfInterest>() {
            @Override
            public void subscribe(ObservableEmitter<PointOfInterest> emitter) throws Exception {
                googleMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                    @Override
                    public void onPoiClick(PointOfInterest pointOfInterest) {
                        emitter.onNext(pointOfInterest);
                    }
                });
            }
        });
    }
}

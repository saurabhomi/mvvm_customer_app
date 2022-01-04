package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class PolygonClickFunction implements Function<GoogleMap, io.reactivex.Observable<Polygon>> {


    @Override
    public io.reactivex.Observable<Polygon> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Polygon>() {
            @Override
            public void subscribe(ObservableEmitter<Polygon> emitter) throws Exception {
                googleMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                    @Override
                    public void onPolygonClick(Polygon polygon) {
                        emitter.onNext(polygon);
                    }
                });
            }
        });
    }
}
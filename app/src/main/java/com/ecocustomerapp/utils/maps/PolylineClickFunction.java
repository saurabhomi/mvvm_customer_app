package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class PolylineClickFunction implements Function<GoogleMap, io.reactivex.Observable<Polyline>> {


    @Override
    public io.reactivex.Observable<Polyline> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Polyline>() {
            @Override
            public void subscribe(ObservableEmitter<Polyline> emitter) throws Exception {
                googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                    @Override
                    public void onPolylineClick(Polyline polyline) {
                        emitter.onNext(polyline);
                    }
                });
            }
        });
    }
}

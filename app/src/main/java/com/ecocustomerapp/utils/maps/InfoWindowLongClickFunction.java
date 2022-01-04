package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class InfoWindowLongClickFunction implements Function<GoogleMap, io.reactivex.Observable<Marker>> {


    @Override
    public io.reactivex.Observable<Marker> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Marker>() {
            @Override
            public void subscribe(ObservableEmitter<Marker> emitter) throws Exception {
                googleMap.setOnInfoWindowLongClickListener(
                        new GoogleMap.OnInfoWindowLongClickListener() {
                            @Override
                            public void onInfoWindowLongClick(Marker marker) {
                                emitter.onNext(marker);
                            }
                        });
            }
        });
    }
}

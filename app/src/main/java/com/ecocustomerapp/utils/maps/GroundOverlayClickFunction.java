package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class GroundOverlayClickFunction implements Function<GoogleMap, io.reactivex.Observable<GroundOverlay>> {


    @Override
    public io.reactivex.Observable<GroundOverlay> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<GroundOverlay>() {
            @Override
            public void subscribe(ObservableEmitter<GroundOverlay> emitter) throws Exception {
                googleMap.setOnGroundOverlayClickListener(
                        new GoogleMap.OnGroundOverlayClickListener() {
                            @Override
                            public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                                emitter.onNext(groundOverlay);
                            }
                        });
            }
        });
    }
}
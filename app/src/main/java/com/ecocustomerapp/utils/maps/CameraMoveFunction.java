package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class CameraMoveFunction implements Function<GoogleMap, io.reactivex.Observable<Void>> {


    @Override
    public io.reactivex.Observable<Void> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

                    @Override
                    public void onCameraMove() {
                        emitter.onNext(null);
                    }

                });
            }
        });
    }
}

package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class CameraPositionFunction implements Function<GoogleMap, io.reactivex.Observable<CameraPosition>> {


    @Override
    public io.reactivex.Observable<CameraPosition> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<CameraPosition>() {
            @Override
            public void subscribe(ObservableEmitter<CameraPosition> emitter) throws Exception {
                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        emitter.onNext(cameraPosition);
                    }
                });
            }
        });
    }
}

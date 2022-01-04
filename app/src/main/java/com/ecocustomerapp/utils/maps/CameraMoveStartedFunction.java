package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class CameraMoveStartedFunction implements Function<GoogleMap, io.reactivex.Observable<Integer>> {


    @Override
    public io.reactivex.Observable<Integer> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

                    @Override
                    public void onCameraMoveStarted(int i) {
                        emitter.onNext(i);
                    }

                });
            }
        });
    }
}

package com.ecocustomerapp.utils.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class CircleClickFunction implements Function<GoogleMap, io.reactivex.Observable<Circle>> {

    @Override
    public io.reactivex.Observable<Circle> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Circle>() {
            @Override
            public void subscribe(ObservableEmitter<Circle> emitter) throws Exception {
                googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                    @Override
                    public void onCircleClick(Circle circle) {
                        emitter.onNext(circle);
                    }
                });
            }
        });
    }
}

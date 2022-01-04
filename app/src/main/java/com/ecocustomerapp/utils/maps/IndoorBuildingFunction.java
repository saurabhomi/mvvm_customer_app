package com.ecocustomerapp.utils.maps;

import com.ecocustomerapp.utils.maps.events.IndoorBuildingEvent;
import com.ecocustomerapp.utils.maps.events.IndoorLevelActivatedEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.IndoorBuilding;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class IndoorBuildingFunction implements Function<GoogleMap, io.reactivex.Observable<IndoorBuildingEvent>> {


    @Override
    public io.reactivex.Observable<IndoorBuildingEvent> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<IndoorBuildingEvent>() {
            @Override
            public void subscribe(ObservableEmitter<IndoorBuildingEvent> emitter) throws Exception {
                googleMap.setOnIndoorStateChangeListener(
                        new GoogleMap.OnIndoorStateChangeListener() {
                            @Override
                            public void onIndoorBuildingFocused() {
                                emitter.onNext(new IndoorBuildingEvent());
                            }

                            @Override
                            public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
                                emitter.onNext(new IndoorLevelActivatedEvent(indoorBuilding));
                            }
                        });
            }
        });
    }
}
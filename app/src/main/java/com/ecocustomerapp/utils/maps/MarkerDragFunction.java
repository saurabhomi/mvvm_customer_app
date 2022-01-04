package com.ecocustomerapp.utils.maps;

import com.ecocustomerapp.utils.maps.events.DragEndEvent;
import com.ecocustomerapp.utils.maps.events.DragEvent;
import com.ecocustomerapp.utils.maps.events.DragStartEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;


class MarkerDragFunction implements Function<GoogleMap, Observable<DragEvent>> {

    @Override
    public io.reactivex.Observable<DragEvent> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<DragEvent>() {
            @Override
            public void subscribe(ObservableEmitter<DragEvent> emitter) throws Exception {
                GoogleMap.OnMarkerDragListener markerDragListener =
                        new GoogleMap.OnMarkerDragListener() {

                            @Override
                            public void onMarkerDragStart(Marker marker) {
                                emitter.onNext(new DragStartEvent(marker));
                            }

                            @Override
                            public void onMarkerDrag(Marker marker) {
                                emitter.onNext(new DragEvent(marker));
                            }

                            @Override
                            public void onMarkerDragEnd(Marker marker) {
                                emitter.onNext(new DragEndEvent(marker));
                            }
                        };
                googleMap.setOnMarkerDragListener(markerDragListener);
            }
        });
    }
}

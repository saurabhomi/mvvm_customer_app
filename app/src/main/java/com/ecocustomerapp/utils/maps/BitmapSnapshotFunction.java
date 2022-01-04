package com.ecocustomerapp.utils.maps;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

class BitmapSnapshotFunction implements Function<GoogleMap, io.reactivex.Observable<Bitmap>> {

    private final Bitmap bitmap;

    public BitmapSnapshotFunction(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    @Override
    public io.reactivex.Observable<Bitmap> apply(GoogleMap googleMap) throws Exception {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        emitter.onNext(bitmap);
                    }
                }, bitmap);
            }
        });
    }
}
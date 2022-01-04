package com.ecocustomerapp.utils.maps;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.location.Location;
import android.location.LocationManager;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

public class MarkerAnimatorFunction implements Function<GoogleMap, Observable<Marker>>, ValueAnimator.AnimatorUpdateListener {
    /**
     * Method is used to interpolate the SymbolLayer icon animation.
     */
    private final TypeEvaluator<Location> latLngEvaluator = new TypeEvaluator<Location>() {

        private final Location location = new Location(LocationManager.GPS_PROVIDER);

        @Override
        public Location evaluate(float fraction, Location startValue, Location endValue) {
            location.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            location.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return location;

        }
    };
    private final Location location;
    private final Location markerLocation;
    private ValueAnimator markerIconAnimator;
    private final Marker marker;
    private GoogleMap googleMap;


    MarkerAnimatorFunction(Marker marker, Location locations) {
        this.location = locations;
        this.marker = marker;
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        this.markerLocation = location;
    }

    @Override
    public Observable<Marker> apply(GoogleMap googleMap) throws Exception {
        this.googleMap = googleMap;
        return Observable.create(new ObservableOnSubscribe<Marker>() {
            @Override
            public void subscribe(ObservableEmitter<Marker> emitter) throws Exception {
                markerIconAnimator = ObjectAnimator.ofObject(latLngEvaluator, markerLocation, location);
                markerIconAnimator.setDuration(1100);
                markerIconAnimator.setInterpolator(new LinearInterpolator());
                markerIconAnimator.addUpdateListener(MarkerAnimatorFunction.this);
                markerIconAnimator.start();
                emitter.onNext(marker);

            }
        });
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Location destination = (Location) animation.getAnimatedValue();
        LatLng newPos = new LatLng(destination.getLatitude(), destination.getLongitude());
        marker.setPosition(newPos);
        marker.setAnchor(0.5f, 0.5f);
        marker.setRotation(location.getBearing());
    }
}

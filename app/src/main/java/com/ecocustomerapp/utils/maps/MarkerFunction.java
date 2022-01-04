package com.ecocustomerapp.utils.maps;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MarkerFunction implements Function<GoogleMap, Observable<Marker>> {
    private final Location location;
    private final String title;
    private Marker marker;
//    private Circle circle;

    MarkerFunction(Location location, String title) {
        this.location = location;
        this.title = title;
    }

    @Override
    public Observable<Marker> apply(GoogleMap googleMap) throws Exception {
        return Observable.create(emitter -> {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.title(title);
//            CircleOptions co = new CircleOptions();
//            co.center(latLng);
//            co.radius(location.getAccuracy());
//            co.fillColor(R.color.map_fill);
//            co.strokeColor(R.color.penRoyalBlue);
//            co.strokeWidth(4.0f);

            marker = googleMap.addMarker(options);
//            circle = googleMap.addCircle(co);
            emitter.onNext(marker);
        });
    }


//    private void animateCarOnMap(final List<LatLng> latLngs) {
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (LatLng latLng : latLngs) {
//            builder.include(latLng);
//        }
//        LatLngBounds bounds = builder.build();
//        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
//        mMap.animateCamera(mCameraUpdate);
//        if (emission == 1) {
//            marker = mMap.addMarker(new MarkerOptions().position(latLngs.get(0))
//                    .flat(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
//        }
//        marker.setPosition(latLngs.get(0));
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.setDuration(1000);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                v = valueAnimator.getAnimatedFraction();
//                double lng = v * latLngs.get(1).longitude + (1 - v)
//                        * latLngs.get(0).longitude;
//                double lat = v * latLngs.get(1).latitude + (1 - v)
//                        * latLngs.get(0).latitude;
//                LatLng newPos = new LatLng(lat, lng);
//                marker.setPosition(newPos);
//                marker.setAnchor(0.5f, 0.5f);
//                marker.setRotation(getBearing(latLngs.get(0), newPos));
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition
//                        (new CameraPosition.Builder().target(newPos)
//                                .zoom(15.5f).build()));
//            }
//        });
//        valueAnimator.start();
//    }
}

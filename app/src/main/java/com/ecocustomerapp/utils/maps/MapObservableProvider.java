package com.ecocustomerapp.utils.maps;

import android.graphics.Bitmap;
import android.location.Location;


import com.ecocustomerapp.utils.maps.events.DragEvent;
import com.ecocustomerapp.utils.maps.events.IndoorBuildingEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;


public class MapObservableProvider {


    private final io.reactivex.subjects.Subject<GoogleMap> mapSubject = io.reactivex.subjects.BehaviorSubject.create();

    public MapObservableProvider(final SupportMapFragment supportMapFragment) {

        Observable.create((ObservableOnSubscribe<GoogleMap>) emitter -> {
            OnMapReadyCallback mapReadyCallback = googleMap -> {
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                emitter.onNext(googleMap);
            };
            supportMapFragment.getMapAsync(mapReadyCallback);
        }).subscribe(mapSubject);
    }


    public Observable<GoogleMap> getMapReadyObservable() {
        return mapSubject;
    }

    public Observable<Marker> getMarkerObservable(Location location, String title) {
        return mapSubject.flatMap(new MarkerFunction(location, title));
    }

    public Observable<Marker> getMarkerAnimatorObservable(Marker marker, Location location) {
        return mapSubject.flatMap(new MarkerAnimatorFunction(marker, location));
    }

    public Observable<Location> getCameraAnimatorObservable(Location location) {
        return mapSubject.flatMap(new AnimateCameraFunction(location));
    }

    public Observable<LatLngBounds> getBoundsObservable(LatLngBounds bounds, int width, int height) {
        return mapSubject.flatMap(new LatLngBoundFunction(bounds, width, height));
    }

    public Observable<LatLng> getMapClickObservable() {
        return mapSubject.flatMap(new MapClickFunction());
    }

    public Observable<LatLng> getMapLongClickObservable() {
        return mapSubject.flatMap(new MapLongClickFunction());
    }

    public Observable<DragEvent> getDragObservable() {
        return mapSubject.flatMap(new MarkerDragFunction());
    }

    public Observable<Marker> getMarkerClickObservable() {
        return mapSubject.flatMap(new MarkerClickFunction());
    }

    public Observable<Marker> getInfoWindowClickObservable() {
        return mapSubject.flatMap(new InfoWindowClickFunction());
    }

    public Observable<Marker> getInfoWindowLongClickObservable() {
        return mapSubject.flatMap(new InfoWindowLongClickFunction());
    }

    public Observable<Marker> getInfoWindowCloseObservable() {
        return mapSubject.flatMap(new InfoWindowCloseFunction());
    }

    @Deprecated
    public Observable<CameraPosition> getCameraChangeObservable() {
        return mapSubject.flatMap(new CameraPositionFunction());
    }

    public Observable<Void> getCameraIdleObservable() {
        return mapSubject.flatMap(new CameraIdleFunction());
    }

    public Observable<Void> getCameraMoveObservable() {
        return mapSubject.flatMap(new CameraMoveFunction());
    }

    public Observable<Void> getCameraMoveCanceledObservable() {
        return mapSubject.flatMap(new CameraMoveCanceledFunction());
    }

    public Observable<Integer> getCameraMoveStartedObservable() {
        return mapSubject.flatMap(new CameraMoveStartedFunction());
    }

    public Observable<IndoorBuildingEvent> getIndoorBuildingObservable() {
        return mapSubject.flatMap(new IndoorBuildingFunction());
    }

    public Observable<Polyline> getPolylineClickObservable() {
        return mapSubject.flatMap(new PolylineClickFunction());
    }

    public Observable<Polygon> getPolygonClickObservable() {
        return mapSubject.flatMap(new PolygonClickFunction());
    }

    public Observable<Circle> getCircleClickObservable() {
        return mapSubject.flatMap(new CircleClickFunction());
    }

    public Observable<PointOfInterest> getPOIClickObservable() {
        return mapSubject.flatMap(new POIClickFunction());
    }

    public Observable<GroundOverlay> getGroundOverlayObservable() {
        return mapSubject.flatMap(new GroundOverlayClickFunction());
    }

    public Observable<Bitmap> getSnapshotObservable() {
        return mapSubject.flatMap(new SnapshotFunction());
    }

    public Observable<Bitmap> getSnapshotObservable(Bitmap bitmap) {
        return mapSubject.flatMap(new BitmapSnapshotFunction(bitmap));
    }

}

package com.ecocustomerapp.utils.maps.events;

import com.google.android.gms.maps.model.Marker;

public class DragEvent {

    private final Marker marker;

    public DragEvent(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }
}

package com.ecocustomerapp.utils.maps.events;

import com.google.android.gms.maps.model.Marker;

public class DragStartEvent extends DragEvent {

    public DragStartEvent(Marker marker) {
        super(marker);
    }

}

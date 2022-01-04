package com.ecocustomerapp.utils.maps;


import android.location.Location;

import com.ecocustomerapp.data.manager.DataManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Retrofit to access the Google Direction API and return the routing data.
 * Created by Saurabh Srivastava on 27/11/18.
 */
public class Routing extends RetroRouting {

    private final TravelMode travelMode;
    private final boolean alternativeRoutes;
    private final List<LatLng> waypoints;
    private final int avoidKinds;
    private final boolean optimize;
    private final String language;
    private final String key;
    private final String signature;
    private final double latitude;
    private final double longitude;
    private final Location location;
    private final boolean sensor;
    private final Units units;


    private Routing(RouteBuilder routeBuilder) {
        super(routeBuilder.listener, routeBuilder.dataManager, routeBuilder.disposable);
        this.travelMode = routeBuilder.travelMode;
        this.waypoints = routeBuilder.waypoints;
        this.avoidKinds = routeBuilder.avoidKinds;
        this.optimize = routeBuilder.optimize;
        this.alternativeRoutes = routeBuilder.alternativeRoutes;
        this.language = routeBuilder.language;
        this.latitude = routeBuilder.latitude;
        this.longitude = routeBuilder.longitude;
        this.location = routeBuilder.location;
        this.sensor = routeBuilder.sensor;
        this.units = routeBuilder.units;
        this.signature = routeBuilder.signature;
        this.key = routeBuilder.key;

    }

    protected Map<String, String> constructDirectionURL() {
        Map<String, String> url_map = new HashMap<>();

        final StringBuilder stringBuilder = new StringBuilder();

        // origin
        final LatLng origin = waypoints.get(0);
        stringBuilder.append("origin=")
                .append(origin.latitude)
                .append(',')
                .append(origin.longitude);

        url_map.put("origin", origin.latitude + "," + origin.longitude);


        // destination
        final LatLng destination = waypoints.get(waypoints.size() - 1);
        stringBuilder.append("&destination=")
                .append(destination.latitude)
                .append(',')
                .append(destination.longitude);
        url_map.put("destination", destination.latitude + "," + destination.longitude);

        // travel
        stringBuilder.append("&mode=").append(travelMode.getValue());

        url_map.put("mode", travelMode.getValue());

        // WayPoints

        if (waypoints.size() > 2) {
            StringBuilder url_builder = new StringBuilder();
            stringBuilder.append("&waypoints=");
            if (optimize)
                stringBuilder.append("optimize:true|");
            url_builder.append("optimize:true|");
            for (int i = 1; i < waypoints.size() - 1; i++) {
                final LatLng p = waypoints.get(i);
                stringBuilder.append("via:"); // we don't want to parse the resulting JSON for 'legs'.
                url_builder.append("via:"); // we don't want to parse the resulting JSON for 'legs'.
                stringBuilder.append(p.latitude);
                url_builder.append(p.latitude);
                stringBuilder.append(',');
                url_builder.append(',');
                stringBuilder.append(p.longitude);
                url_builder.append(p.longitude);
                stringBuilder.append('|');
                url_builder.append('|');
            }

            url_map.put("waypoints", url_builder.toString());
        }


        // avoid
        if (avoidKinds > 0) {
            stringBuilder.append("&avoid=");
            stringBuilder.append(AvoidKind.getRequestParam(avoidKinds));
            url_map.put("avoid", AvoidKind.getRequestParam(avoidKinds));
        }

        if (alternativeRoutes) {
            stringBuilder.append("&alternatives=true");
            url_map.put("alternatives", "true");
        }

        // sensor
        stringBuilder.append("&sensor=true");
        url_map.put("sensor", "true");

        // language
        if (language != null) {
            stringBuilder.append("&language=").append(language);
            url_map.put("language", language);
        }

        // API key
        if (key != null) {
            stringBuilder.append("&key=").append(key);
            url_map.put("key", key);
        }
        // Digital signature for premium key
        if (signature != null) {
            stringBuilder.append("&signature=").append(signature);
            url_map.put("signature", signature);
        }
        return url_map;
    }

    @Override
    Map<String, String> constructDistanceURL() {
        Map<String, String> url_map = new HashMap<>();
        url_map.put("units", units.getUnit());
        url_map.put("origins", location.getLatitude() + "," + location.getLongitude());
        url_map.put("destinations", latitude + "," + longitude);
        url_map.put("key", key);

        return url_map;
    }

    @Override
    Map<String, String> constructAddressURL() {
        Map<String, String> urlMap = new HashMap<>();
        if (location != null) {
            urlMap.put("latlng", location.getLatitude() + "," + location.getLongitude());
        } else {
            urlMap.put("latlng", latitude + "," + longitude);
        }

        if (sensor) {
            urlMap.put("sensor", "true");
        }
        // API key
        if (key != null) {
            urlMap.put("key", key);
        }
        // Digital signature for premium key
        if (signature != null) {
            urlMap.put("signature", signature);
        }


        return urlMap;
    }

    public void executeForDirection() {
        executeDirection();
    }

    public void executeForAddress() {
        executeFindAddress();
    }

    public void executeForDistance() {
        executeDistance();
    }

    public enum TravelMode {
        BIKING("bicycling"),
        DRIVING("driving"),
        WALKING("walking"),
        TRANSIT("transit");

        final String sValue;

        TravelMode(String sValue) {
            this.sValue = sValue;
        }

        String getValue() {
            return sValue;
        }
    }

    public enum Units {
        METRIC("metric"),
        IMPERIAL("imperial");

        final String unit;

        Units(String unit) {
            this.unit = unit;
        }

        String getUnit() {
            return unit;
        }
    }

    public static class RouteBuilder {


        private RetroRoutingListener listener;
        private TravelMode travelMode;
        private boolean alternativeRoutes;
        private List<LatLng> waypoints;
        private int avoidKinds;
        private boolean optimize;
        private String language;
        private double latitude;
        private double longitude;
        private Location location;
        private boolean sensor;
        private String key;
        private String signature;
        private Units units;
        private DataManager dataManager;
        private CompositeDisposable disposable;

        public RouteBuilder() {
            this.travelMode = TravelMode.DRIVING;
            this.alternativeRoutes = false;
            this.waypoints = new ArrayList<>();
            this.avoidKinds = 0;
            this.listener = null;
            this.optimize = false;
            this.language = null;
            this.latitude = 0;
            this.longitude = 0;
            this.location = null;
            this.sensor = false;
            this.key = null;
            this.signature = null;
            this.units = Units.METRIC;
        }

        public RouteBuilder travelMode(TravelMode travelMode) {
            this.travelMode = travelMode;
            return this;
        }

        public RouteBuilder alternativeRoutes(boolean alternativeRoutes) {
            this.alternativeRoutes = alternativeRoutes;
            return this;
        }

        public RouteBuilder wayPoints(LatLng... points) {
            waypoints.clear();
            Collections.addAll(waypoints, points);
            return this;
        }

        public RouteBuilder waypoints(List<LatLng> waypoints) {
            this.waypoints = new ArrayList<>(waypoints);
            return this;
        }

        public RouteBuilder optimize(boolean optimize) {
            this.optimize = optimize;
            return this;
        }

        public RouteBuilder avoid(AvoidKind... avoids) {
            for (AvoidKind avoidKind : avoids) {
                this.avoidKinds |= avoidKind.getBitValue();
            }
            return this;
        }

        public RouteBuilder language(String language) {
            this.language = language;
            return this;
        }

        public RouteBuilder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RouteBuilder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RouteBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public RouteBuilder sensor(boolean sensor) {
            this.sensor = sensor;
            return this;
        }

        public RouteBuilder unit(Units units) {
            this.units = units;
            return this;
        }

        public RouteBuilder key(String key) {
            this.key = key;
            return this;
        }

        public RouteBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public RouteBuilder withListener(RetroRoutingListener listener) {
            this.listener = listener;
            return this;
        }

        public RouteBuilder withDataManager(DataManager dataManager) {
            this.dataManager = dataManager;
            return this;
        }

        public RouteBuilder withDisposable(CompositeDisposable disposable) {
            this.disposable = disposable;
            return this;
        }

        public Routing build() {
            return new Routing(this);
        }


    }

}

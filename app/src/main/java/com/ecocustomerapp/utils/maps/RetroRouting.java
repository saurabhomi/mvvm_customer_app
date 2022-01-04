package com.ecocustomerapp.utils.maps;


import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.remote.GoogleConnection;


import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SAURABH SRIVASTAVA
 */

abstract class RetroRouting extends GoogleConnection {
    private final RetroRoutingListener routeListener;
    private final DataManager dataManager;
    private final CompositeDisposable disposable;
    /* Private member variable that will hold the RouteException instance created in the background thread */

    RetroRouting(RetroRoutingListener listener, DataManager dataManager, CompositeDisposable disposable) {
        this.routeListener = listener;
        this.dataManager = dataManager;
        this.disposable = disposable;

    }


    synchronized void executeDirection() {


    }

    synchronized void executeFindAddress() {

    }

    synchronized void executeDistance() {

    }

    abstract Map<String, String> constructDirectionURL();

    abstract Map<String, String> constructDistanceURL();

    abstract Map<String, String> constructAddressURL();


    enum AvoidKind {
        TOLLS(1, "tolls"),
        HIGHWAYS(1 << 1, "highways"),
        FERRIES(1 << 2, "ferries");

        private final String sRequestParam;
        private final int sBitValue;

        AvoidKind(int bit, String param) {
            this.sBitValue = bit;
            this.sRequestParam = param;
        }

        static String getRequestParam(int bit) {
            StringBuilder ret = new StringBuilder();
            for (AvoidKind kind : AvoidKind.values()) {
                if ((bit & kind.sBitValue) == kind.sBitValue) {
                    ret.append(kind.sRequestParam).append('|');
                }
            }
            return ret.toString();
        }

        int getBitValue() {
            return sBitValue;
        }
    }


}

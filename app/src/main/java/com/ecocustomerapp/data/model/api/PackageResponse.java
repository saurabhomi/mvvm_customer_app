package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PackageResponse extends BaseModel {

    public List<Package> getPackageList() {

        Collections.sort(packageList, new Comparator<Package>() {
            @Override
            public int compare(Package aPackage, Package bPackage) {
                return aPackage.rate()-bPackage.rate();
            }
        });
        return packageList;
    }

    @Expose
    @SerializedName("Package")
    private List<Package> packageList;

    public List<Package> getHourly() {
        return hourly;
    }

    public void setHourly(List<Package> hourly) {
        this.hourly = hourly;
    }

    @Expose
    @SerializedName("hourly")
    private List<Package> hourly;

}

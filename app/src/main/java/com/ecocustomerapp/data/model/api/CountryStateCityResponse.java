package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryStateCityResponse extends BaseModel {

    public List<Data> getDataList() {
        return dataList;
    }

    @Expose
    @SerializedName("")
    List<Data> dataList;
}

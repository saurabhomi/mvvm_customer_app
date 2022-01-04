package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verification extends BaseModel {

    public Entities getEntities() {
        return entities;
    }

    @Expose
    @SerializedName("")
    private Entities entities;
}

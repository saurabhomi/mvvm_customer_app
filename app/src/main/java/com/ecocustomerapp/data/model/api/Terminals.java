package com.ecocustomerapp.data.model.api;

import com.ecocustomerapp.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Terminals extends BaseModel {

    public List<TerminalData> getTerminals() {
        return terminalData;
    }

    @Expose
    @SerializedName("")
    private List<TerminalData> terminalData;
}

package com.ecocustomerapp.data.model.api;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "entities")
public class Entities {

    public List<String> getList() {
        return list;
    }


    public void setList(List<String> list) {
        this.list = list;
    }

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @Expose
    @SerializedName("CustomerList")
    @ColumnInfo(name = "CustomerList")
    private List<String> list;
}

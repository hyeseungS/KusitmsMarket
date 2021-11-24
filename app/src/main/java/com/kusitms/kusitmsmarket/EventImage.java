package com.kusitms.kusitmsmarket;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventImage {
    @SerializedName("data")
    public List<String> data = null;

    @SerializedName("data")
    public List<String> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<String> data) {
        this.data = data;
    }

}

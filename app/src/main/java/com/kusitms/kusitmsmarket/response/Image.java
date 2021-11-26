package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    @SerializedName("count")
    public int count = 0;

    @SerializedName("data")
    public List<String> data = null;

    @SerializedName("count")
    public int getCount() { return count; }

    @SerializedName("count")
    public void setCount(int count) { this.count = count; }

    @SerializedName("data")
    public List<String> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<String> data) {
        this.data = data;
    }

}

package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventImageResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<String> eventImageList;

    public List<String> getEventImageList() {
        return eventImageList;
    }

    public void setEventImageList(List<String> eventImageList) {
        this.eventImageList = eventImageList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

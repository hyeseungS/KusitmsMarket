package com.kusitms.kusitmsmarket.model;

public class MarketItem {
    String name;
    int resourceId;

    public MarketItem(int resourceId, String name) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}

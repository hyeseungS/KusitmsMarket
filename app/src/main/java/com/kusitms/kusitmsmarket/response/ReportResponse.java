package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

public class ReportResponse {

    @SerializedName("marketName")
    private String marketName;

    @SerializedName("storeAddress")
    private String storeAddress;

    @SerializedName("storeCategory")
    private String storeCategory;

    @SerializedName("storeGiftcard")
    private boolean storeGiftcard;

    @SerializedName("storeLink")
    private String storeLink;

    @SerializedName("storeName")
    private String storeName;

    @SerializedName("storePhone")
    private String storePhone;

    @SerializedName("storeScore")
    private String storeScore;

    @SerializedName("storeTime")
    private String storeTime;

    @SerializedName("userName")
    private String userName;

    public ReportResponse() {
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public boolean isStoreGiftcard() {
        return storeGiftcard;
    }

    public void setStoreGiftcard(boolean storeGiftcard) {
        this.storeGiftcard = storeGiftcard;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }

    public String getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(String storeScore) {
        this.storeScore = storeScore;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }
}

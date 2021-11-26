package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreList {
    @SerializedName("data")
    public List<StoreData> data = null;

    @SerializedName("data")
    public List<StoreData> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<StoreData> data) {
        this.data = data;
    }
    public class StoreData {

        @SerializedName("storeName")
        private String storeName;
        @SerializedName("storeAddress")
        private String storeAddress;
        @SerializedName("storePhone")
        private String storePhone;
        @SerializedName("storeCategory")
        private String storeCategory;
        @SerializedName("marketName")
        private String marketName;
        @SerializedName("storeGiftcard")
        private boolean storeGiftcard;
        @SerializedName("userName")
        private String userName;
        @SerializedName("storeScore")
        private double storeScore;
        @SerializedName("storeLink")
        private String storeLink;
        @SerializedName("storeTime")
        private String storeTime;

        public StoreData(String storeName, String storeAddress, String storePhone, String storeCategory,
                         String marketName, boolean storeGiftcard, String userName, double storeScore, String storeLink, String storeTime) {
            this.storeName = storeName;
            this.storeAddress = storeAddress;
            this.storePhone = storePhone;
            this.storeCategory = storeCategory;
            this.marketName = marketName;
            this.storeGiftcard = storeGiftcard;
            this.userName = userName;
            this.storeScore = storeScore;
            this.storeLink = storeLink;
            this.storeTime = storeTime;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public String getStoreCategory() {
            return storeCategory;
        }

        public void setStoreCategory(String storeCategory) {
            this.storeCategory = storeCategory;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public boolean getStoreGiftcard() {
            return storeGiftcard;
        }

        public void setStoreGiftcard(boolean storeGiftcard) {
            this.storeGiftcard = storeGiftcard;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public double getStoreScore() { return storeScore; }

        public void setStoreScore(double storeScore) {
            this.storeScore = storeScore;
        }

        public String getStoreLink() {
            return storeLink;
        }

        public void setStoreLink(String storeLink) {
            this.storeLink = storeLink;
        }

        public String getStoreTime() {
            return storeTime;
        }

        public void setStoreTime(String storeTime) {
            this.storeTime = storeTime;
        }
    }
}


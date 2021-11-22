package com.kusitms.kusitmsmarket;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class marketList {
    @SerializedName("data")
    public List<marketData> data = null;

    @SerializedName("data")
    public List<marketData> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<marketData> data) {
        this.data = data;
    }
    public class marketData {

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

        public marketData(String storeName, String storeAddress, String storePhone, String storeCategory,
                          String marketName, boolean storeGiftcard, String userName) {
            this.storeName = storeName;
            this.storeAddress = storeAddress;
            this.storePhone = storePhone;
            this.storeCategory = storeCategory;
            this.marketName = marketName;
            this.storeGiftcard = storeGiftcard;
            this.userName = userName;
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
    }
}


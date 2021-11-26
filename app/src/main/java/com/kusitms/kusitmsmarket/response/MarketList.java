package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketList {
    @SerializedName("data")
    public List<MarketData> data = null;

    @SerializedName("data")
    public List<MarketData> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<MarketData> data) {
        this.data = data;
    }

    public class MarketData {

        @SerializedName("marketName")
        private String marketName;
        @SerializedName("storeCnt")
        private int storeCnt;
        @SerializedName("marketAddress")
        private String marketAddress;

        public MarketData(String marketName, int storeCnt, String marketAddress) {
            this.marketName = marketName;
            this.storeCnt = storeCnt;
            this.marketAddress= marketAddress;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public int getStoreCnt() {
            return storeCnt;
        }

        public void setStoreCnt(int storeCnt) {
            this.storeCnt = storeCnt;
        }

        public String getMarketAddress() { return marketAddress; }

        public void setMarketAddress(int storeCnt) {
            this.marketAddress = marketAddress;
        }
    }
}

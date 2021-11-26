package com.kusitms.kusitmsmarket.request;

public class MarketNameRequest {
    private String marketname;

    public MarketNameRequest(String marketname) {
        this.marketname = marketname;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    @Override
    public String toString() {
        return "MarketNameRequest{" +
                "marketname='" + marketname + '\'' +
                '}';
    }
}

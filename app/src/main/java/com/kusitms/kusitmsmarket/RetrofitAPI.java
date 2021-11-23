package com.kusitms.kusitmsmarket;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    @GET("/market/filter/gift")
    Call<StoreList> getTicketStoreData();

    @GET("/market/filter1/0")
    Call<MarketList> getEverydayMarketData();

    @GET("/market/filter1/1")
    Call<MarketList> getFiveDaysMarketData();

    @GET("/market/filter2/big")
    Call<MarketList> getBigMarketData();

    @GET("/market/filter2/medium")
    Call<MarketList> getNormalMarketData();

    @GET("/market/filter2/small")
    Call<MarketList> getSmallMarketData();
}

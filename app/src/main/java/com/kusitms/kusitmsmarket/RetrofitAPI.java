package com.kusitms.kusitmsmarket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("/market/filter/gift/data/")
    Call<marketList> getMarketData();
}

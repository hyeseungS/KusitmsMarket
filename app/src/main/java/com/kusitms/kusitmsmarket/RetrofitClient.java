package com.kusitms.kusitmsmarket;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://3.36.163.80:8080"; // 서버 URL
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static RetrofitAPI getAPIService() {
        return getInstance().create(RetrofitAPI.class);
    }

    public static Retrofit getInstance() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        return retrofit;

    }
}

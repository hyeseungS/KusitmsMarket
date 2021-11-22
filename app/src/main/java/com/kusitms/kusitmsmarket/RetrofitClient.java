package com.kusitms.kusitmsmarket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://3.36.163.80:8080/"; // 서버 URL
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static RetrofitAPI getAPIService() {
        return getInstance().create(RetrofitAPI.class);
    }

    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

    }
}

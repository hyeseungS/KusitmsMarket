package com.kusitms.kusitmsmarket;

import com.kusitms.kusitmsmarket.request.LoginRequest;
import com.kusitms.kusitmsmarket.request.SignUpRequest;
import com.kusitms.kusitmsmarket.response.DeleteResponse;
import com.kusitms.kusitmsmarket.response.NoticeResponse;
import com.kusitms.kusitmsmarket.response.QuestionResponse;
import com.kusitms.kusitmsmarket.response.SignUpResponse;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;
import com.kusitms.kusitmsmarket.response.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("/market/getOne")
    Call<MarketList> getSearchMarketData(@Query("name") String name);

    @GET("/store/getOne")
    Call<StoreList> getSearchStoreData(@Query("name") String name);
    
    @GET("/market")
    Call<MarketList> getMarketData();

    @GET("/store")
    Call<StoreList> getStoreData();

    @GET("/")
    Call<MarketList> getAll();

    // 로그인
    @POST("/api/authenticate")
    Call<UserToken> postUserTokenData(@Body LoginRequest request);

    // validate
    @GET("/api/check-duplicate/id/{username}")
    Call<String> getValidateUsername(@Path("username") String username);

    @GET("/api/check-duplicate/nickname/{nickname}")
    Call<String> getValidateNickname(@Path("nickname") String nickname);

    // 회원가입
    @POST("/api/signup")
    Call<SignUpResponse> postSingUp(@Body SignUpRequest request);


    // 마이페이지
    @GET("/mypage/notice")
    Call<List<NoticeResponse>> getNoticeData();


// 유저정보
    @GET("/api/aboutme")
    Call<UserInfoResponse> getUserInfoData(@Header("X-AUTH-TOKEN") String authorization);

// 로그아웃
    @PUT("/api/logout")
    Call<String> putUserInfo(@Header("X-AUTH-TOKEN") String authorization, @Query("cnt") Integer cnt);

// 회원탈퇴
    @DELETE("/api/delete/user")
    Call<DeleteResponse> deleteUserInfo(@Header("X-AUTH-TOKEN") String authorization);

// 푸시알람 on/off
    @GET("/notification/onoff_alarm/{username}")
    Call<Boolean> onOffPushAlarm(@Path("username") String username);

    // 질의응답
    @GET("/mypage/question")
    Call<List<QuestionResponse>> getQuestion();

    @GET("/api/store/like")
    Call<StoreList> getStoreLike(@Header("X-AUTH-TOKEN") String token);

    @GET("/eventImage/pay")
    Call<Image> getEventImage();

    @GET("/storeImage/store")
    Call<Image> getStoreImgData(@Query("storeName") String storeName);

    @GET("/store/getReview")
    Call<Review> getStoreReviewData(@Query("storeName") String storeName);
}

package com.kusitms.kusitmsmarket;

import com.kusitms.kusitmsmarket.model.EventItem;
import com.kusitms.kusitmsmarket.response.EventImageResponse;
import com.kusitms.kusitmsmarket.response.Image;
import com.kusitms.kusitmsmarket.response.MarketList;
import com.kusitms.kusitmsmarket.response.Review;
import com.kusitms.kusitmsmarket.response.StoreList;
import com.kusitms.kusitmsmarket.request.ANameRequest;
import com.kusitms.kusitmsmarket.request.LoginRequest;
import com.kusitms.kusitmsmarket.request.MarketNameRequest;
import com.kusitms.kusitmsmarket.request.ReportRequest;
import com.kusitms.kusitmsmarket.request.SignUpRequest;
import com.kusitms.kusitmsmarket.response.DeleteResponse;
import com.kusitms.kusitmsmarket.response.NoticeResponse;
import com.kusitms.kusitmsmarket.response.QuestionResponse;
import com.kusitms.kusitmsmarket.response.QuoteResponse;
import com.kusitms.kusitmsmarket.response.ReportResponse;
import com.kusitms.kusitmsmarket.response.SignUpResponse;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;
import com.kusitms.kusitmsmarket.response.UserToken;
import com.kusitms.kusitmsmarket.response.ValidateResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitAPI {

    // 온누리 상품권
    @GET("/market/filter/gift")
    Call<StoreList> getTicketStoreData();

    // 상설시장
    @GET("/market/filter1/0")
    Call<MarketList> getEverydayMarketData();

    // 비상설시장
    @GET("/market/filter1/1")
    Call<MarketList> getFiveDaysMarketData();

    // 대
    @GET("/market/filter2/big")
    Call<MarketList> getBigMarketData();

    // 중
    @GET("/market/filter2/medium")
    Call<MarketList> getNormalMarketData();

    // 소
    @GET("/market/filter2/small")
    Call<MarketList> getSmallMarketData();

    // 시장 찾기
    @GET("/market/getOne")
    Call<MarketList> getSearchMarketData(@Query("name") String name);

    // 점포 찾기
    @GET("/store/getOne")
    Call<StoreList> getSearchStoreData(@Query("name") String name);

    // 전체 시장
    @GET("/market")
    Call<MarketList> getMarketData();

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

    // 단골집
    @GET("/api/store/like")
    Call<StoreList> getStoreLike(@Header("X-AUTH-TOKEN") String token);

    // 이벤트 사진
    @GET("/eventImage/pay")
    Call<Image> getEventImage();

    // 가게 사진
    @GET("/storeImage/store")
    Call<Image> getStoreImgData(@Query("storeName") String storeName);

    // 리뷰 가져오기
    @GET("/store/getReview")
    Call<Review> getStoreReviewData(@Query("storeName") String storeName);

    // 가게 정보 수정
    @FormUrlEncoded
    @POST("/store/writeStoreModify")
    Call<Void> setStoreModify(
            @Header("X-AUTH-TOKEN") String authorization,
            @Field("memo") String memo,
            @Field("storeName") String storeName
    );
    // 리뷰 등록
    @FormUrlEncoded
    @POST("/store/writeStoreReview")
    Call<Void> setStoreReview(
            @Header("X-AUTH-TOKEN") String authorization,
            @Field("memo") String memo,
            @Field("score") double score,
            @Field("storeName") String storeName
    );
    // 신고하기
    @FormUrlEncoded
    @POST("/store/writeStoreReviewReport")
    Call<Void> setStoreReviewReport(
            @Header("X-AUTH-TOKEN") String authorization,
            @Field("memo") String memo,
            @Field("reviewMemo") String reviewMemo
    );

    // Hot 시장
    @GET("/popular/store")
    Call<MarketList> getHotMarket();

    // 제보하기
    @POST("/mypage/report-store")
    Call<ReportResponse> postReport(@Header("X-AUTH-TOKEN") String token,
                                    @Body ReportRequest request);

    // 시장 시세 제공 api (시장별)
    @POST("/quote/m-name")
    Call<QuoteResponse> postQuoteMarket(@Body MarketNameRequest marketNameRequest);

    // 시장 시세 제공 api (품목별)
    @POST("/quote/a-name")
    Call<QuoteResponse> postQuoteAName(@Body ANameRequest aNameRequest);

    // 닉네임 중복확인
    @GET("/api/check-duplicate/nickname/{nickname}")
    Call<ValidateResponse> getCheckDuplicateNickname(@Path("nickname") String nickname);


    // 휴대전화 인증
    @POST("/sendSMS")
    Call<ResponseBody> postSendSMS(@QueryMap Map<String,String> query);


    // 유저 정보 업데이트
    @PUT("/api/modify-nickname/")
    Call<ResponseBody> putModifyNickname(@Header("X-AUTH-TOKEN") String token,
                                         @Query("nickname") String nickname);

    // 마이페이지 이벤트 불러오기
    @GET("/eventImage/all")
    Call<EventImageResponse> getMyPageEventImage();


}

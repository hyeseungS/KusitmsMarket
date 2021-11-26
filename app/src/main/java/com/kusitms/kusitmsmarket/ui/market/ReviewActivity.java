package com.kusitms.kusitmsmarket.ui.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.response.Review;
import com.kusitms.kusitmsmarket.model.ReviewItem;
import com.kusitms.kusitmsmarket.adapter.ReviewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private static String token;
    private static String storeName;
    private static double storeScore = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        LinearLayout linearLayout = findViewById(R.id.reviewLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        // 리뷰 등록 버튼 (가게 이름 넘겨주기)
        Button registerReview = findViewById(R.id.registerReview);
        registerReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("storeName", storeName);
                FragmentReviewDialog dialogFragment = new FragmentReviewDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "show_review_dialog");

            }
        });

        // 리뷰 리사이클러뷰
        RecyclerView mRecyclerView = findViewById(R.id.reviewList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /* initiate adapter */
        ReviewRecyclerAdapter mRecyclerAdapter = new ReviewRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RatingBar totalRating = findViewById(R.id.totalRating);
        totalRating.setRating(Float.parseFloat(String.valueOf(storeScore)));
        TextView reviewCnt = findViewById(R.id.reviewCnt);
        ArrayList<ReviewItem> mReviewItems = new ArrayList<>();

        // 리뷰 불러오기
        RetrofitClient.getAPIService().getStoreReviewData(storeName).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    Review resource = response.body();
                    List<Review.ReviewData> dataList = resource.data;
                    reviewCnt.setText("리뷰(" + resource.count + "개)");
                    int i=1;
                    for (Review.ReviewData data : dataList) {
                        mReviewItems.add(new ReviewItem(i++, data.getReviewUserName(), data.getReviewScore(), data.getReviewMemo()));
                    }

                    Log.d("test", "성공");
                }
                mRecyclerAdapter.setFriendList(mReviewItems);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        mRecyclerAdapter.setFriendList(mReviewItems);
    }

    public String getUserToken() {
        return token;
    }

    public static void setUserToken(String t) {
        token = t;
    }

    public static void setStoreName(String n) {
        storeName = n;
    }

    public static void setStoreScore(double score) {
        storeScore = score;
    }
    //status bar의 높이 계산
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}
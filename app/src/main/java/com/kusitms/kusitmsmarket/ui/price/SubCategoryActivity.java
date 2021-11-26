package com.kusitms.kusitmsmarket.ui.price;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kusitms.kusitmsmarket.AppTest;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.adapter.ListViewAdapter;
import com.kusitms.kusitmsmarket.adapter.ListViewCategoryAdapter;
import com.kusitms.kusitmsmarket.request.ANameRequest;
import com.kusitms.kusitmsmarket.response.NoticeResponse;
import com.kusitms.kusitmsmarket.response.QuoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {

    ListView listView;
    ImageView ivCategory;
    String categoryName;
    int categoryImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        // cnt 업데이트
        ((AppTest) getApplication()).update();
        if(((AppTest) getApplication()).getCount() == 0) {

            //광고 불러오는 코드
        }

        LinearLayout linearLayout = findViewById(R.id.subCategoryLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("category");
        categoryImageId = intent.getIntExtra("image",R.drawable.ic_user);

        listView = findViewById(R.id.sub_category_lv);
        ivCategory = findViewById(R.id.sub_category_iv_category);

        ivCategory.setImageResource(categoryImageId);

        ListViewCategoryAdapter adapter = new ListViewCategoryAdapter();
        listView.setAdapter(adapter);

        // api
        ANameRequest aNameRequest = new ANameRequest(categoryName);
        Call<QuoteResponse> call = RetrofitClient.getAPIService().postQuoteAName(aNameRequest);

        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {

                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    QuoteResponse quoteResponse = response.body();

                    List<QuoteResponse.Quote> quoteList = quoteResponse.getQuotes();

                    for (QuoteResponse.Quote quote :
                            quoteList) {
                        adapter.addItem(quote.getA_name(), quote.getUnit(),quote.getPrice(), quote.getM_name());
                        adapter.notifyDataSetChanged();
                    }
                    System.out.println("작동완료");
                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }

            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {

            }
        });


       /* Call<QuoteResponse> call = RetrofitClient.getAPIService().getNoticeData();

        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    QuoteResponse noticeResponseList = response.body();

                    for (NoticeResponse notify:
                            noticeResponseList) {
                        // list형식으로 받고 저장하기
                        adapter.addItem(notify.getTitle(), notify.getContent(),
                                getDrawable(R.drawable.ic_baseline_arrow_forward_ios_24));

                        adapter.notifyDataSetChanged();
                    }
                    System.out.println("작동완료");
                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NoticeResponse>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });*/

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
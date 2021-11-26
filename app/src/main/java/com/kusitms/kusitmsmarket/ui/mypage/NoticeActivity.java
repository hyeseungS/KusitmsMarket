package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kusitms.kusitmsmarket.adapter.ListViewAdapter;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.model.ListVIewNoticeItem;
import com.kusitms.kusitmsmarket.response.NoticeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//공지사항 Activity
public class NoticeActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        listView = findViewById(R.id.listview_notice);

        ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        LinearLayout linearLayout = findViewById(R.id.noticeLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        // api
        Call<List<NoticeResponse>> call = RetrofitClient.getAPIService().getNoticeData();

        call.enqueue(new Callback<List<NoticeResponse>>() {
            @Override
            public void onResponse(Call<List<NoticeResponse>> call, Response<List<NoticeResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    List<NoticeResponse> noticeResponseList = response.body();

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
        });



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
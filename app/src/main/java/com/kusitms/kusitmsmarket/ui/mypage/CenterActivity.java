package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.adapter.ListViewAdapter;
import com.kusitms.kusitmsmarket.adapter.ListViewQuestionAdapter;
import com.kusitms.kusitmsmarket.response.NoticeResponse;
import com.kusitms.kusitmsmarket.response.QuestionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterActivity extends AppCompatActivity {

    ListView listView;

    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        listView = findViewById(R.id.listview_center);

        ListViewQuestionAdapter adapter = new ListViewQuestionAdapter();
        listView.setAdapter(adapter);

        LinearLayout linearLayout = findViewById(R.id.centerLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        Call<List<QuestionResponse>> call = RetrofitClient.getAPIService().getQuestion();

        call.enqueue(new Callback<List<QuestionResponse>>() {
            @Override
            public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    List<QuestionResponse> questionResponseList = response.body();

                    for (QuestionResponse question:
                            questionResponseList) {
                        // list형식으로 받고 저장하기
                        adapter.addItem(""+(number++), question.getContent());

                        adapter.notifyDataSetChanged();
                    }
                    System.out.println("작동완료");
                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
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
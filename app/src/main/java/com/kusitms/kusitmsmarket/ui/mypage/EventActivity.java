package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.adapter.EventMypageApater;
import com.kusitms.kusitmsmarket.adapter.ListViewAdapter;
import com.kusitms.kusitmsmarket.adapter.ViewPagerAdapter;
import com.kusitms.kusitmsmarket.response.EventImageResponse;
import com.kusitms.kusitmsmarket.response.Image;
import com.kusitms.kusitmsmarket.response.NoticeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    ListView listView;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        listView = findViewById(R.id.event_list);
        imageView = findViewById(R.id.item_mypage_event);
        EventMypageApater adapter = new EventMypageApater(this);
        listView.setAdapter(adapter);


        Call<EventImageResponse> call = RetrofitClient.getAPIService().getMyPageEventImage();

        call.enqueue(new Callback<EventImageResponse>() {
            @Override
            public void onResponse(Call<EventImageResponse> call, Response<EventImageResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    List<String> imgurl= response.body().getEventImageList();

                    for (String str:
                         imgurl) {

                            adapter.addItem(str);
//
                        /* ===========문자열 받아오는 것까지는 가능한데 어떻게 띄우는지 모르겠어!!========*/
                        /*Thread uThread = new Thread() {
                            @Override
                            public void run(){
                                try{
                                    URL url = new URL(str);

                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                                    conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로 만듦

                                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)

                                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기

                                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 반환

                                }catch (MalformedURLException e){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        };

                        uThread.start(); // 작업 Thread 실행
                        try{
                            uThread.join();
                            imageView.setImageBitmap(bitmap);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }*/
                    }

                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EventImageResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // api
        /*Call<List<NoticeResponse>> call = RetrofitClient.getAPIService().getNoticeData();

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
        });*/

    }
}
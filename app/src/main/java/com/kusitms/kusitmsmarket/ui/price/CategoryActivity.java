package com.kusitms.kusitmsmarket.ui.price;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.request.ANameRequest;
import com.kusitms.kusitmsmarket.response.QuoteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    ImageButton btnSquid, btnPumpkin, btnPork, btnApple, btnOnion, btnCabbage, btnEgg, btnSweetPotato, btnChicken;

    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ANameRequest aNameRequest = new ANameRequest("");

        // 연동
        btnSquid = findViewById(R.id.category_squid);
        btnPumpkin = findViewById(R.id.category_pumpkin);
        btnPork = findViewById(R.id.category_pork);
        btnApple = findViewById(R.id.category_apple);
        btnOnion = findViewById(R.id.category_onion);
        btnCabbage = findViewById(R.id.category_cabbage);
        btnEgg = findViewById(R.id.category_egg);
        btnSweetPotato = findViewById(R.id.category_sweet_potato);
        btnChicken = findViewById(R.id.category_chicken);

        btnSquid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=1;
                aNameRequest.setCategory("오징어");
            }
        });

        btnPumpkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=2;
                aNameRequest.setCategory("호박");
                clickCategory(aNameRequest);
            }
        });

        btnPork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=3;
                aNameRequest.setCategory("돼지고기");
                clickCategory(aNameRequest);
            }
        });

        btnApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=4;
                aNameRequest.setCategory("사과");
                clickCategory(aNameRequest);
            }
        });

        btnOnion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=5;
                aNameRequest.setCategory("양파");
                clickCategory(aNameRequest);
            }
        });

        btnCabbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=6;
                aNameRequest.setCategory("배추");
                clickCategory(aNameRequest);
            }
        });

        btnEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=7;
                aNameRequest.setCategory("달걀");
                clickCategory(aNameRequest);
            }
        });

        btnSweetPotato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=8;
                aNameRequest.setCategory("고구마");
                clickCategory(aNameRequest);
            }
        });

        btnChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=9;
                aNameRequest.setCategory("닭고기");
                clickCategory(aNameRequest);
            }
        });




    }

    public void clickCategory(ANameRequest aNameRequest) {
        Call<QuoteResponse> call = RetrofitClient.getAPIService().postQuoteAName(aNameRequest);

        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    // 화면 만들어지면 하기

                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
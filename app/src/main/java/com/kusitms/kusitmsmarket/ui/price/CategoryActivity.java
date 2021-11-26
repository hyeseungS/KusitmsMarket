package com.kusitms.kusitmsmarket.ui.price;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

        Intent subCategoryActivity = new Intent(this, SubCategoryActivity.class);

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

                Bundle bundle = new Bundle();
                bundle.putString("category", "오징어");
                bundle.putInt("imageId", R.drawable.ic_category_squid);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);
            }
        });

        btnPumpkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=2;

                Bundle bundle = new Bundle();
                subCategoryActivity.putExtra("category", "호박");
                subCategoryActivity.putExtra("image", R.drawable.ic_category_pumpkin);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);

            }
        });

        btnPork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=3;

                Bundle bundle = new Bundle();
                bundle.putString("category", "돼지고기");
                bundle.putInt("image", R.drawable.ic_category_pork);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);

            }
        });

        btnApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=4;

                Bundle bundle = new Bundle();
                bundle.putString("category", "사과");
                bundle.putInt("image", R.drawable.ic_category_apple);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);

            }
        });

        btnOnion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=5;

                Bundle bundle = new Bundle();
                bundle.putString("category", "양파");
                bundle.putInt("image", R.drawable.ic_category_onion);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);


            }
        });

        btnCabbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=6;

                Bundle bundle = new Bundle();
                bundle.putString("category", "배추");
                bundle.putInt("image", R.drawable.ic_category_cabbage);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);

            }
        });

        btnEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=7;

                Bundle bundle = new Bundle();
                bundle.putString("category", "달걀");
                bundle.putInt("image", R.drawable.ic_category_egg);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);

            }
        });

        btnSweetPotato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=8;

                Bundle bundle = new Bundle();
                bundle.putString("category", "고구마");
                bundle.putInt("image", R.drawable.ic_category_sweet_potato);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);


            }
        });

        btnChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=9;

                Bundle bundle = new Bundle();
                bundle.putString("category", "닭고기");
                bundle.putInt("image", R.drawable.ic_category_chicken);
                subCategoryActivity.putExtras(bundle);

                startActivity(subCategoryActivity);


//
//                aNameRequest.setCategory("닭고기");
//                clickCategory(aNameRequest);
            }
        });




    }


}
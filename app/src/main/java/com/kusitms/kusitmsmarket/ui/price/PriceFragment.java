package com.kusitms.kusitmsmarket.ui.price;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.AppTest;
import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.adapter.PriceAdapter;
import com.kusitms.kusitmsmarket.databinding.FragmentPriceBinding;
import com.kusitms.kusitmsmarket.model.PriceData;
import com.kusitms.kusitmsmarket.request.MarketNameRequest;
import com.kusitms.kusitmsmarket.response.QuoteResponse;
import com.kusitms.kusitmsmarket.ui.market.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*@Override
public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
        if(response.isSuccessful()) {
        Log.d("연결이 성공적 : ", response.body().toString());

        } else {
        Log.e("연결이 비정상적 : ", "error code : " + response.code());
        }
        }

@Override
public void onFailure(Call<QuoteResponse> call, Throwable t) {
        Log.e("연결실패", t.getMessage());
        }*/
public class PriceFragment extends Fragment {

    // 뷰 설정
    private RecyclerView mVerticalView;
    private PriceAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int MAX_ITEM_COUNT = 10;

    // 응답 내용
    int img;
    String category;
    String unit;
    String price;
    String marketName;

    // spinner 설정
    String[] location = {"서울특별시", "부산광역시", "대구광역시", "인천광역시", "서울특별시",
            "광주광역시", "대전광역시", "울산광역시", "세종특별자치시"};

    String[] gu = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구",
            "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구",
            "송파구", "양천구", "영등포구", "용산구", "은평구", "중구", "중랑구"};

    // 지역구 관련
    String selectedLoc, selectedGu;

    private PriceViewModel priceViewModel;
    private FragmentPriceBinding binding;

    // 속성 변수
    EditText etMarket;
    Button btnMarket, btnCategory;
    Spinner spinnerLoc, spinnerGu;

    Button test;

    // 품목 액티비티


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        priceViewModel =
                new ViewModelProvider(this).get(PriceViewModel.class);

        binding = FragmentPriceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent categoryIntent = new Intent(getContext(), CategoryActivity.class);

        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.priceLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

       /* final TextView textView = binding.textPrice;
        priceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        mVerticalView = (RecyclerView) root.findViewById(R.id.price_rv);
        spinnerLoc = root.findViewById(R.id.spinner_price_loc);
        spinnerGu = root.findViewById(R.id.spinner_price_gu);
        btnMarket = root.findViewById(R.id.btn_price_market);
        etMarket = root.findViewById(R.id.et_price_market);
        btnCategory = root.findViewById(R.id.btn_price_category);


        // init Data
        ArrayList<PriceData> data = new ArrayList<>();


        // spinner adapter
        ArrayAdapter<String> adapterLoc = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, location
        );

        ArrayAdapter<String> adapterGu = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, gu
        );

        adapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterGu.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerLoc.setAdapter(adapterLoc);
        spinnerGu.setAdapter(adapterGu);

        spinnerLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLoc = location[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGu = gu[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 초기화면
        img = R.mipmap.ic_launcher;
        category = "사과";
        unit = "개";
        price = "1000";

        // 가져와서 받기

        int i = 0;
        /*int img, String category, String unit, String price*/
        while (i < MAX_ITEM_COUNT) {
            data.add(new PriceData(img, category, unit, price));
            i++;
        }

        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        // setLayoutManager
        mVerticalView.setLayoutManager(mLayoutManager);

        // init Adapter
        mAdapter = new PriceAdapter();

        // set Data
        mAdapter.setPriceData(data);

        // set Adapter
        mVerticalView.setAdapter(mAdapter);

        MarketNameRequest marketNameRequest = new MarketNameRequest("시장");


        Call<QuoteResponse> callMain = RetrofitClient.getAPIService().postQuoteMarket(marketNameRequest);

        callMain.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    QuoteResponse quoteResponse = response.body();

                    List<QuoteResponse.Quote> quotes = quoteResponse.getQuotes();

                    data.clear(); // 데이터 다 지우기

                    for (QuoteResponse.Quote quote :
                            quotes) {
                        // 데이터 임시 설정
                        price = quote.getPrice();
                        category = quote.getA_name();
                        unit = quote.getUnit();

                        String str = category;
                        String result1 = str.substring(0, str.lastIndexOf("(") + 1);

                        System.out.println(result1);


                        img = showCategoryImage(result1);

                        // 데이터 추가
                        data.add(new PriceData(img, category, unit, price));
                    }

                    mAdapter.notifyDataSetChanged();

                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        // 검색을 누르면,
        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marketName = etMarket.getText().toString();

                // cnt 업데이트

                ((AppTest) getActivity().getApplication()).update();
                if(((AppTest) getActivity().getApplication()).getCount() == 0) {

                    //광고 불러오는 코드

                }

                MarketNameRequest marketNameRequest = new MarketNameRequest(marketName);


                Call<QuoteResponse> call = RetrofitClient.getAPIService().postQuoteMarket(marketNameRequest);


                call.enqueue(new Callback<QuoteResponse>() {
                    @Override
                    public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                            QuoteResponse quoteResponse = response.body();

                            List<QuoteResponse.Quote> quotes = quoteResponse.getQuotes();

                            data.clear(); // 데이터 다 지우기

                            for (QuoteResponse.Quote quote :
                                    quotes) {
                                // 데이터 임시 설정
                                price = quote.getPrice();
                                category = quote.getA_name();
                                unit = quote.getUnit();

                                String str = category;
                                String result1 = str.substring(0, str.lastIndexOf("(") + 1);

                                System.out.println(result1);


                                img = showCategoryImage(result1);

                                if (img == R.drawable.ic_user) {
                                    img = showCategoryImage(category);
                                }

                                // 데이터 추가
                                data.add(new PriceData(img, category, unit, price));
                            }

                            mAdapter.notifyDataSetChanged();

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
        });

        // 품목 버튼을 클릭하면
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(categoryIntent);
            }
        });


        return root;
    }

    //status bar의 높이 계산
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    public int showCategoryImage(String category) {
        switch (category) {
            case "오징어":
                return R.drawable.ic_category_squid;
            case "호박":
                return R.drawable.ic_category_pumpkin;
            case "돼지고기":
                return R.drawable.ic_category_pork;
            case "사과":
                return R.drawable.ic_category_apple;
            case "양파":
                return R.drawable.ic_category_onion;
            case "배추":
                return R.drawable.ic_category_cabbage;
            case "달걀":
                return R.drawable.ic_category_egg;
            case "고구마":
                return R.drawable.ic_category_sweet_potato;
            case "닭고기":
                return R.drawable.ic_category_chicken;
            case "오징어(":
                return R.drawable.ic_category_squid;
            case "호박(":
                return R.drawable.ic_category_pumpkin;
            case "돼지고기(":
                return R.drawable.ic_category_pork;
            case "사과(":
                return R.drawable.ic_category_apple;
            case "양파(":
                return R.drawable.ic_category_onion;
            case "배추(":
                return R.drawable.ic_category_cabbage;
            case "달걀(":
                return R.drawable.ic_category_egg;
            case "고구마(":
                return R.drawable.ic_category_sweet_potato;
            case "닭고기(":
                return R.drawable.ic_category_chicken;
        }
        return R.drawable.ic_category_pumpkin;
    }
}
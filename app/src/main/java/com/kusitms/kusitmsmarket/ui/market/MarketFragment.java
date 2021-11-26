package com.kusitms.kusitmsmarket.ui.market;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.adapter.HotMarketRecyclerAdapter;
import com.kusitms.kusitmsmarket.adapter.ViewPagerAdapter;
import com.kusitms.kusitmsmarket.model.MarketItem;
import com.kusitms.kusitmsmarket.adapter.MarketRecyclerAdapter;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.response.Image;
import com.kusitms.kusitmsmarket.response.MarketList;
import com.kusitms.kusitmsmarket.response.StoreList;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;
    private FragmentMarketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marketViewModel =
                new ViewModelProvider(this).get(MarketViewModel.class);

        binding = FragmentMarketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView imageView1 = (ImageView) root.findViewById(R.id.eventImage1);
        ImageView imageView2 = (ImageView) root.findViewById(R.id.eventImage2);
        ImageView imageView3 = (ImageView) root.findViewById(R.id.eventImage3);

        RetrofitClient.getAPIService().getEventImage().enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    Image resource = response.body();
                    List<String> img = resource.data;
                    Glide.with(getContext())
                            .load(img.get(0))
                            .into(imageView1);

                    Glide.with(getContext())
                            .load(img.get(1))
                            .into(imageView2);

                    Glide.with(getContext())
                            .load(img.get(2))
                            .into(imageView3);

                    Log.d("test", "성공");
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }

        });

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.myMarketList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        /* initiate adapter */
        MarketRecyclerAdapter mRecyclerAdapter = new MarketRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<MarketItem> mMarketItems = new ArrayList<>();
        String token = ((MainActivity) getActivity()).getUserToken();
        RetrofitClient.getAPIService().getStoreLike(token).enqueue(new Callback<StoreList>() {
            @Override
            public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    StoreList resource = response.body();
                    List<StoreList.StoreData> dataList = resource.data;
                    int i=1;
                    for (StoreList.StoreData data : dataList) {
                        mMarketItems.add(new MarketItem(i++, data.getStoreName()));
                    }
                    Log.d("test", "성공");
                }
                mRecyclerAdapter.setFriendList(mMarketItems);
            }

            @Override
            public void onFailure(Call<StoreList> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        RecyclerView mRecyclerHotView = (RecyclerView) root.findViewById(R.id.myHotMarketList);
        mRecyclerHotView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        /* initiate adapter */
        HotMarketRecyclerAdapter mRecyclerHotAdapter = new HotMarketRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerHotView.setAdapter(mRecyclerHotAdapter);
        mRecyclerHotView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<MarketItem> mHotMarketItems = new ArrayList<>();
        RetrofitClient.getAPIService().getHotMarket().enqueue(new Callback<MarketList>() {
            @Override
            public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    MarketList resource = response.body();
                    List<MarketList.MarketData> dataList = resource.data;
                    int i=1;
                    for (MarketList.MarketData data : dataList) {
                        if(data.getMarketName() != null)
                            mHotMarketItems.add(new MarketItem(i++, data.getMarketName()));
                    }
                    Log.d("test", "성공");
                }
                mRecyclerHotAdapter.setFriendList(mHotMarketItems);
            }

            @Override
            public void onFailure(Call<MarketList> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        mRecyclerAdapter.setFriendList(mMarketItems);
        mRecyclerHotAdapter.setFriendList(mHotMarketItems);
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.myMarketLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

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
}
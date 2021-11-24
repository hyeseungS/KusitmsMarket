package com.kusitms.kusitmsmarket.ui.market;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.MarketItem;
import com.kusitms.kusitmsmarket.MarketList;
import com.kusitms.kusitmsmarket.MarketRecyclerAdapter;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.StoreList;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;

import java.io.IOException;
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

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.myMarketList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
//        DividerItemDecorator spaceDecoration = new DividerItemDecorator(10);
//        mRecyclerView.addItemDecoration(spaceDecoration);

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

        mRecyclerAdapter.setFriendList(mMarketItems);

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
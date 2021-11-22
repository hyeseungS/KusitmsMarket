package com.kusitms.kusitmsmarket.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.MarketItem;
import com.kusitms.kusitmsmarket.MarketRecyclerAdapter;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketBinding;

import java.util.ArrayList;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;
    private FragmentMarketBinding binding;
    static final String[] LIST_MENU = {"다은이네 호떡", "미경이네 떡볶이", "서영이네 사과 농장", "의찬이 노래교실", "준이네 카페"};

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

        /* adapt data */
        ArrayList<MarketItem> mMarketItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mMarketItems.add(new MarketItem(i, i + "번째 식당"));
        }
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
}
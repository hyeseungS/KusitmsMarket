package com.kusitms.kusitmsmarket.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketDetailBinding;

public class MarketDetailFragment extends Fragment {

    private FragmentMarketDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMarketDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.detailInfo);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        return root;
    }

    //status bar의 높이 계산
    public int getStatusBarHeight()
    {
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

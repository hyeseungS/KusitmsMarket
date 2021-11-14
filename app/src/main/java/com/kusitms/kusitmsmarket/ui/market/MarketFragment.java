package com.kusitms.kusitmsmarket.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketBinding;

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

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) root.findViewById(R.id.myMarketList) ;
        listview.setAdapter(adapter);

        RelativeLayout relativeLayout = (RelativeLayout) root.findViewById(R.id.myMarketLayout);
        relativeLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                MainActivity activity = (MainActivity) getActivity();
                activity.moveToDetail();
            }
        });
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
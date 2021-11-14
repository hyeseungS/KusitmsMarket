package com.kusitms.kusitmsmarket.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kusitms.kusitmsmarket.databinding.FragmentMarketBinding;

public class MyPageFragment extends Fragment {

    private MyPageViewModel myPageViewModel;
    private FragmentMarketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPageViewModel =
                new ViewModelProvider(this).get(MyPageViewModel.class);

        binding = FragmentMarketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        myPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

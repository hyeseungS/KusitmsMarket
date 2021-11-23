package com.kusitms.kusitmsmarket.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentMypageBinding;
import com.naver.maps.map.LocationTrackingMode;

public class MyPageFragment extends Fragment {

    private MyPageViewModel myPageViewModel;
    private FragmentMypageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPageViewModel =
                new ViewModelProvider(this).get(MyPageViewModel.class);

        binding = FragmentMypageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button reportButton = (Button) root.findViewById(R.id.report_btn);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportLocationActivity.class);
                startActivity(intent);
            }
        });

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

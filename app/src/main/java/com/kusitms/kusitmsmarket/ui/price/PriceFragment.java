package com.kusitms.kusitmsmarket.ui.price;

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

import com.kusitms.kusitmsmarket.databinding.FragmentPriceBinding;

public class PriceFragment extends Fragment {

    private PriceViewModel priceViewModel;
    private FragmentPriceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        priceViewModel =
                new ViewModelProvider(this).get(PriceViewModel.class);

        binding = FragmentPriceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPrice;
        priceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.kusitms.kusitmsmarket.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketDetailBinding;

public class MarketDetailFragment extends Fragment implements View.OnClickListener {

    private FragmentMarketDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMarketDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        AppCompatButton open = (AppCompatButton) root.findViewById(R.id.edit_button);
        open.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.edit_button:

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentEditDialog dialogFragment = new FragmentEditDialog();
                dialogFragment.show(fm, "fragment_dialog_test");

                break;

        }

    }

}

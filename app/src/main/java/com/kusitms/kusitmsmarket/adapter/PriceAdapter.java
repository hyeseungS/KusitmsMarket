package com.kusitms.kusitmsmarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.model.PriceData;

import java.util.ArrayList;

public class PriceAdapter extends RecyclerView.Adapter<PriceViewHolder> {


    private ArrayList<PriceData> priceData;

    public void setPriceData(ArrayList<PriceData> priceData) {
        this.priceData = priceData;
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_price_category, parent, false);

        PriceViewHolder holder = new PriceViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {

        PriceData data = priceData.get(position);

        holder.icon.setImageResource(data.getImg());
        holder.category.setText(data.getCategory());
        holder.price.setText(data.getPrice());
        holder.unit.setText(data.getUnit());

    }

    @Override
    public int getItemCount() {
        return  priceData.size();
    }
}

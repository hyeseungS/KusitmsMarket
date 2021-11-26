package com.kusitms.kusitmsmarket.adapter;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.R;

public class PriceViewHolder extends RecyclerView.ViewHolder{

    public ImageView icon;

    public TextView category;
    public TextView unit;
    public TextView price;

    public Button btnPrice;



    public PriceViewHolder(View itemView) {
        super(itemView);

        // 바인딩딩
       icon = itemView.findViewById(R.id.item_price_iv);
        category = itemView.findViewById(R.id.item_price_tv_category);
        unit = itemView.findViewById(R.id.item_price_tv_unit);
        price = itemView.findViewById(R.id.item_price_tv_price);
        btnPrice = itemView.findViewById(R.id.item_price_btn_price);

    }
}

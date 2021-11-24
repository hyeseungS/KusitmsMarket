package com.kusitms.kusitmsmarket;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.ui.market.MarketDetailFragment;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketRecyclerAdapter extends RecyclerView.Adapter<MarketRecyclerAdapter.ViewHolder> {

    private ArrayList<MarketItem> mMarketList;

    @NonNull
    @Override
    public MarketRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mymarket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mMarketList.get(position));
    }

    public void setFriendList(ArrayList<MarketItem> list){
        this.mMarketList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMarketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(this);
        }

        void onBind(MarketItem item){
            name.setText(item.getName());
        }
        @Override
        public void onClick(View view) {
            //프레그먼트끼리 Store data 넘기기 위한 bundle
            MarketDetailFragment.setStoreName(name.getText().toString());
            ((MainActivity)MainActivity.mContext).moveToDetail();
        }
    }
}


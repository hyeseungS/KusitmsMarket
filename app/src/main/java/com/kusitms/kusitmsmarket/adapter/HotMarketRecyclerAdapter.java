package com.kusitms.kusitmsmarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.model.MarketItem;
import com.kusitms.kusitmsmarket.ui.market.MarketDetailFragment;

import java.util.ArrayList;

public class HotMarketRecyclerAdapter extends RecyclerView.Adapter<HotMarketRecyclerAdapter.ViewHolder> {

    private ArrayList<MarketItem> mHotMarketList;

    @NonNull
    @Override
    public HotMarketRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_hotmarket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotMarketRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mHotMarketList.get(position));
    }

    public void setFriendList(ArrayList<MarketItem> list){
        this.mHotMarketList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHotMarketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView index;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            index = (TextView) itemView.findViewById(R.id.index);
            name = (TextView) itemView.findViewById(R.id.hotMarketItem);

            itemView.setOnClickListener(this);
        }

        void onBind(MarketItem item){
            index.setText(String.valueOf(item.getResourceId()));
            name.setText(item.getName());
        }
        @Override
        public void onClick(View view) {
        }
    }
}


package com.kusitms.kusitmsmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.model.ChatData;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<ChatData> chatDataList;
    private String myNickName;

    public void setMyNickName(String myNickName) {
        this.myNickName = myNickName;
    }

    public void setChatDataList(List<ChatData> chatDataList) {
        this.chatDataList = chatDataList;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        private TextView tvMsg;
        private View rootView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_nickname);
            tvMsg = itemView.findViewById(R.id.tv_item_msg);
            rootView = itemView;
        }
    }


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatData chat = chatDataList.get(position);

        holder.tvName.setText(chat.getNickname());
        holder.tvMsg.setText(chat.getMsg());

        // 오른쪽 왼쪽 설정
        if(chat.getNickname().equals(this.myNickName)){
            holder.tvMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.tvName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.tvMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.tvName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }


    @Override
    public int getItemCount() {
        return  chatDataList != null ? 0 : null;
    }

    public ChatData getChat(int position) {
        return chatDataList != null ? chatDataList.get(position) : null;
    }

    // 채팅 갱신
    public void addChat(ChatData chat) {
        chatDataList.add(chat);
        notifyItemInserted(chatDataList.size()-1);
    }
}

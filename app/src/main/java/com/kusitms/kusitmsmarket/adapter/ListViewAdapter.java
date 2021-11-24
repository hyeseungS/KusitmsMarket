package com.kusitms.kusitmsmarket.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.model.ListVIewNoticeItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVIewNoticeItem> listViewItemList = new ArrayList<ListVIewNoticeItem>();

    public ListViewAdapter() {  }

    // listviewitem 항목개수
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position 위치의 item 값을 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // position 위치의 item 의 row id값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position 위치의 item 항목을 View 형식으로 얻어온다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // listview_item의 layout을 inflate하여 xml을 view로 만들고 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_notice, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tv_notice_title);
        TextView descTextView = (TextView) convertView.findViewById(R.id.tv_notice_date);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.notice_move_content);

        // Data Set (listViewItemList) 에서 position에 위치한 데이터참조 획득
        ListVIewNoticeItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDate());
        iconImageView.setImageDrawable(listViewItem.getIconDrawable());

        return convertView;
    }

    // item 데이터 추가
    public void addItem(String title, String date, Drawable icon) {
        ListVIewNoticeItem item = new ListVIewNoticeItem();
        item.setIconDrawable(icon);
        item.setTitle(title);
        item.setDate(date);
        listViewItemList.add(item);
    }

    // item 삭제
    public void delItem(int position) {
        listViewItemList.remove(position);
    }
}
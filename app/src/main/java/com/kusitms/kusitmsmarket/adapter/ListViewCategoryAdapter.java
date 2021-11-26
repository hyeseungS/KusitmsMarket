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
import com.kusitms.kusitmsmarket.model.CategoryItem;
import com.kusitms.kusitmsmarket.model.ListVIewNoticeItem;

import java.util.ArrayList;

public class ListViewCategoryAdapter extends BaseAdapter {
    private ArrayList<CategoryItem> listViewItemList = new ArrayList<CategoryItem>();

    public ListViewCategoryAdapter() {  }

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
            convertView = inflater.inflate(R.layout.item_sub_category, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tvCategory = (TextView) convertView.findViewById(R.id.item_sub_category_category);
        TextView tvUnit = (TextView) convertView.findViewById(R.id.item_sub_category_unit);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.item_sub_category_price);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.item_sub_category_location);

        // Data Set (listViewItemList) 에서 position에 위치한 데이터참조 획득
        CategoryItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tvCategory.setText(listViewItem.getCategory());
        tvUnit.setText(listViewItem.getUnit());
        tvPrice.setText(listViewItem.getPrice());
        tvLocation.setText(listViewItem.getLoc());

        return convertView;
    }

    // item 데이터 추가
    public void addItem(String category, String unit, String price, String loc) {
        CategoryItem item = new CategoryItem();
        item.setCategory(category);
        item.setUnit(unit);
        item.setPrice(price);
        item.setLoc(loc);
        listViewItemList.add(item);
    }

    // item 삭제
    public void delItem(int position) {
        listViewItemList.remove(position);
    }

}

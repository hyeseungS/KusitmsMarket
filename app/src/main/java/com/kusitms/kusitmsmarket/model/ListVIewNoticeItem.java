package com.kusitms.kusitmsmarket.model;

import android.graphics.drawable.Drawable;


// 공지사항의 리스트뷰 item 객체 모델
public class ListVIewNoticeItem {

    private String title;
    private String date;
    private Drawable iconDrawable;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }
}

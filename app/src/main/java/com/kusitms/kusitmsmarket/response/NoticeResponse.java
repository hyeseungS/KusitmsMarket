package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeResponse {

    @SerializedName("content")
    private String content;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NoticeResponse{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}

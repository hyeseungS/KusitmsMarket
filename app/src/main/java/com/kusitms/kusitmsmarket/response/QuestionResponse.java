package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

//
//{
//        "title": "질문1",
//        "content": "질문내용1",
//        "username": "eun"
//        },
public class QuestionResponse {

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("username")
    private String username;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "QuestionResponse{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

public class UserToken {

    @SerializedName("token")
    private String token;

    @SerializedName("click_cnt")
    private int click_cnt;

    @Override
    public String toString() {
        return "UserToken{" +
                "token='" + token + '\'' +
                ", click_cnt=" + click_cnt +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getClick_cnt() {
        return click_cnt;
    }

    public void setClick_cnt(int click_cnt) {
        this.click_cnt = click_cnt;
    }
}

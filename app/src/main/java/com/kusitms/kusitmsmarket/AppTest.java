package com.kusitms.kusitmsmarket;

import android.app.Application;

public class AppTest extends Application {

    private int count = 0;

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int update() {

        count++;

        if(count == 10) {
            count = 0;
        }

        return count;
    }
}

package com.kusitms.kusitmsmarket;

import android.app.Application;

public class AppTest extends Application {

    private int count = 0;

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

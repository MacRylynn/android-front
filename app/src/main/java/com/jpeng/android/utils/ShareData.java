package com.jpeng.android.utils;

import android.app.Application;

/**
 * @ClassName ShareData
 * @Description 用来在全局传递数据
 * @Author: lijiao73
 * @Date: 2019/11/27 0027 下午 7:24
 */
public class ShareData extends Application {
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

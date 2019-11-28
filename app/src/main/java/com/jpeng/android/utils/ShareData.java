package com.jpeng.android.utils;

import android.app.Application;

/**
 * @ClassName ShareData
 * @Description 用来在全局传递数据
 * @Author: lijiao73
 * @Date: 2019/11/27 0027 下午 7:24
 */
public class ShareData extends Application {
    private long accountId;
    private long userId;
    private String userCode;
    private String checkResult;

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}

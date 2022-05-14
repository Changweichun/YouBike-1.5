package com.android.youbike;

import android.app.Application;

public class CookieString extends Application {
    private String cookieStr;
    private boolean flag = false;
    public void OnCreate(){
        super.onCreate();
        setCookieStr(COOKIE);
        setFlag(FLAG);
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private static final String COOKIE = "";
    private static final boolean FLAG = false;
}

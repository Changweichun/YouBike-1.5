package com.android.youbike;

import android.app.Application;

public class CookieString extends Application {
    private String cookieStr;

    public void OnCreate(){
        super.onCreate();
        setCookieStr(COOKIE);
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    private static final String COOKIE = "";
}

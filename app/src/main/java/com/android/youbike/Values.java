package com.android.youbike;

import android.app.Application;

public class Values extends Application {
    private String cookieStr; // 獲取到的 cookie 值
    private String account; // 登入帳號
    private int login_state; // 登入狀態 ( 0為一般用戶登入   1為管理者帳號登入   2為尚未登入)
    public void OnCreate(){
        super.onCreate();
        setCookieStr(COOKIE);
        setAccount(ACCOUNT);
        setLogin_state(LOGIN_STATE);
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getLogin_state() {
        return login_state;
    }

    public void setLogin_state(int login_state) {
        this.login_state = login_state;
    }

    private static final String COOKIE = "";
    private static final String ACCOUNT = "";
    private static final Integer LOGIN_STATE = 2;
}

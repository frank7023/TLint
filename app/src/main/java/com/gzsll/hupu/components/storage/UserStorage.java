package com.gzsll.hupu.components.storage;


import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.gzsll.hupu.db.User;
import com.gzsll.hupu.helper.SettingPrefHelper;

/**
 * Created by sll on 2015/7/11.
 */

public class UserStorage {

    private SettingPrefHelper mSettingPrefHelper;
    private Context mContext;


    public UserStorage(SettingPrefHelper mSettingPrefHelper, Context mContext) {
        this.mSettingPrefHelper = mSettingPrefHelper;
        this.mContext = mContext;
    }


    private String cookie;
    private String token;

    private User user;

    public User getUser() {
        return user;
    }


    public void login(User user) {
        this.user = user;
        mSettingPrefHelper.setLoginUid(user.getUid());
    }


    public void logout() {
        if (user.getUid().equals(mSettingPrefHelper.getLoginUid())) {
            mSettingPrefHelper.setLoginUid("");
        }
        user = null;
        cookie = "";
        token = "";
        removeCookie();
    }

    private void removeCookie() {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public boolean isLogin() {
        return user != null && mSettingPrefHelper.getLoginUid().equals(user.getUid());
    }

    public String getToken() {
        if (!isLogin()) {
            return token;
        }
        return user.getToken();
    }

    public String getUid() {
        if (!isLogin()) {
            return "";
        }
        return user.getUid();
    }


    public String getCookie() {
        if (isLogin()) {
            return user.getCookie();
        }
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

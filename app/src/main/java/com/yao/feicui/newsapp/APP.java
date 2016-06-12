package com.yao.feicui.newsapp;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 16245 on 2016/06/12.
 */
public class APP extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
         //调用极光推送接口
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}

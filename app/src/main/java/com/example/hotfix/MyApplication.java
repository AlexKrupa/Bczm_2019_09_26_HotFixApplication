package com.example.hotfix;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author zhangjun
 * @date 2019-9-26
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化
        MultiDex.install(this);
    }
}

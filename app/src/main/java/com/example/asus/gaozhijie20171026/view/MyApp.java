package com.example.asus.gaozhijie20171026.view;

import android.app.Application;
import android.content.Context;

import com.example.asus.gaozhijie20171026.mode.net.MyOkhttps;

/**
 * 创建时间  2017/10/26 14:53
 * 创建人
 * 类描述    app类
 */
public class MyApp extends Application{
    private static Context appContext;
    //得到ok的对象
    private static MyOkhttps okhttp;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        okhttp = new MyOkhttps();

    }

    /**
     * get方法
     * @return
     */
    public static Context getAppContext(){
        return appContext;
    }

    public static MyOkhttps getOkhttp() {
        return okhttp;
    }
}

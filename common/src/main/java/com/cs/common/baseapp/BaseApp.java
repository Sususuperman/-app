package com.cs.common.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.cs.android.network.NetworkMng;
import com.cs.common.basedao.DataBase;
import com.cs.common.utils.CrashUtils;

import java.util.Hashtable;

/**
 * APPLICATION
 */
public class BaseApp extends Application {

    private static BaseApp baseApp;
    private DataBase dataBase;// 数据库
    private NetworkMng networkMng;// 网络监控

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        dataBase = new DataBase(this);

        // 开启网络监控
        this.networkMng = new NetworkMng(this);
        this.networkMng.startReceive();
        // 注册crashUtils
        CrashUtils crashHandler = CrashUtils.getInstance();
        crashHandler.init(getApplicationContext());

//        SpeechUtility.createUtility(getAppContext(), SpeechConstant.APPID + "=5743b779");
    }

    public static BaseApp getBaseInstance() {
        return baseApp;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public static Context getAppContext() {
        return baseApp;
    }

    public static Resources getAppResources() {
        return baseApp.getResources();
    }

    @Override
    public void onTerminate() {
        dataBase.close();
        this.networkMng.stopReceive();
        super.onTerminate();
    }

    public NetworkMng getNetworkMng() {
        return networkMng;
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


}

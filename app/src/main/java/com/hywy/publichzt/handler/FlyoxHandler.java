package com.hywy.publichzt.handler;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.cs.common.utils.Logger;
import com.hywy.publichzt.activity.LoginActivity;
import com.hywy.publichzt.activity.MainActivity;
import com.hywy.publichzt.dao.AccountDao;
import com.hywy.publichzt.entity.Account;

public class FlyoxHandler extends Handler {
    private static final String TAG = "FlyoxHandler";

    public static final int wBegin = 1;//开始
    public static final int wCheckCard = 10;//检测是否存在sdcard
    public static final int wLogin = 100;// 登陆
    public static final int wCheckUser = 101;//非第一次登录
    public static final int wMain = 102;//跳转到主界面
    private Activity activity;

    public FlyoxHandler(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case wBegin:
                wMain();
//                begin();
                break;
            case wCheckCard:
                checkCard();
                break;
            case wCheckUser:
                checkUser();
                break;
            case wLogin:
                finish();
                break;
            case wMain:
                wMain();
                break;
        }
    }


    //判断当前登陆状态是否为在线，如果是，则直接进入主界面,否则继续下面的操作
    private void begin() {
        this.sendEmptyMessage(wCheckCard);
    }

    //检测sdcard是否存在
    private void checkCard() {
        this.sendEmptyMessageDelayed(wCheckUser, 2 * 1000);
    }

    private void checkUser() {
        AccountDao userDao = new AccountDao(activity);
        Account user = userDao.select();
        if (user != null) {
            this.sendEmptyMessage(wMain);
        } else {
            this.sendEmptyMessage(wLogin);
        }
    }


    // 进入登陆界面
    private synchronized void finish() {
        Logger.d(TAG, "完成,进入登陆界面");
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void wMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}

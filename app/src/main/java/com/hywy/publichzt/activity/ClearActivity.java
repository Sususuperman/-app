package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.Window;

import com.hywy.publichzt.AppConfig;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.dao.AccountDao;
import com.hywy.publichzt.dao.AppMenuDao;


public class ClearActivity extends Activity {

    private static final int wClose = 10;
    private static final int wEnd = 15;// 退出程序

    public static final int wStart = 0;// 正常启动
    public static final int wNormalEnd = 2;// 正常退出程序
    public static final int wNoLoginEnd = 3;// 进入主界面未登录，退出程序
    public static final int wDoubleEnd = 4;
    public static final int wClearDown = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        Intent intent = getIntent();
        int isFinish = intent.getIntExtra("isFinish", 0);
        switch (isFinish) {
            case wStart:// 正常启动
                Intent intent1 = null;
                intent1 = new Intent(this, WelcomeActivity.class);
                startActivity(intent1);
                break;
            case wNormalEnd:// 正常退出程序
                exit();
                break;
            case wNoLoginEnd:// 进入主界面未登录，退出程序
                mHandler.sendEmptyMessage(wEnd);
                break;
            case wDoubleEnd:
                finished();
                break;
        }
    }

    private void finished() {
        App app = (App) this.getApplication();
        app.setToken(null);
//		app.setIsDownMainApk(true);
//        PushUtils.clearAllUnread(this);// 清除所有未读
        finish();
    }

    private Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case wClose:
                    stopBKService();
                    break;
                case wEnd:
                    finish();
                    System.exit(0);
                    break;
                case wClearDown:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    // 停止后台所有服务，关闭界面
    private void stopBKService() {
        exit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 退出，将用户登陆状态改为隐身
    private void exit() {
        clearUserData();
        clearMenuData();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    App app = App.getInstance();

    private void clearUserData() {
        AccountDao dao = new AccountDao(this);
        dao.delete();
        app.setToken(null);
        app.setAccount(null);
    }

    /**
     * 包括菜单接口数据库清除，和首页九宫格菜单缓存清除
     */
    private void clearMenuData() {
        AppMenuDao dao = new AppMenuDao(this);
        dao.delete();
        app.setMenu(null);

        app.delFileData(AppConfig.KEY_All);
        app.delFileData(AppConfig.KEY_USER);
    }

    /**
     * 关闭App并且重启
     */
    private void exitAndRestart() {
        Log.e("DeviceInfoAty", Process.myPid() + "");
        Process.killProcess(Process.myPid());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(new Intent("restart.app"));
//                finish();
            }
        }, 3000);
    }

}

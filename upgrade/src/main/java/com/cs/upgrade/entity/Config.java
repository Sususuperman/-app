package com.cs.upgrade.entity;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;

import com.cs.upgrade.OnDownloadingDialogListener;
import com.cs.upgrade.R;

/**
 * Created by Kun on 2017/5/22.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:配置文件
 */

public class Config {
    /**
     * 通知栏标题
     */
    public static String notificationTitle;
    /**
     * 通知栏小图标
     */
    public static int notificaionSmallIconResId = 0;
    /**
     * 通知栏图标
     */
    public static int notificaionIconResId = 0;
    /**
     * 文件下载地址
     */
    public static String downLoadPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/downloads/";

    /**
     * 进度条背景样式
     */
    public static Drawable progressbarDrawable = null;


    /**
     * 进度条顔色
     */
    public static int progressbarColor = 0;

    /**
     * 进度条背景顔色
     */
    public static int progressbarBgColor = 0;


    /**
     * 弹出框标题
     */
    public static String dialogTitle = "软件更新";

    /**
     * 弹出框确认按钮文字
     */
    public static String dialogConfirmText = "确认";

    /**
     * 弹出框取消按钮文字
     */
    public static String dialogCancleText = "取消";


    /**
     * 通知栏是否显示apk文件大小和下载速度
     */
    public static int notificationDownloadVisibility = View.INVISIBLE;

    /**
     * 通知栏点击暂停图标
     */
    public static int notificationPauseResId = R.drawable.ic_pause;

    /**
     * 通知栏点击开始下载图标
     */
    public static int notificationContinueResId = R.drawable.ic_continue;

    /**
     * 必填
     */
    public static String actionEndstr = "";

    /**
     * 开始下载
     */
    public static String ACTION_START = "ACTION_START";
    /**
     * 暂停下载
     */
    public static String ACTION_PAUSE = "ACTION_PAUSE";
    /**
     * 下载完成
     */
    public static String ACTION_FININSHED = "ACTION_FININSHED";
    /**
     * 刷新下载进度
     */
    public static String ACTION_REFRESH = "ACTION_REFRESH";
    /**
     * 按钮点击
     */
    public static String ACTION_BUTTON = "ACTION_BUTTON";

    /**
     * 下载进度监听回调
     */
    public static OnDownloadingDialogListener onDownloadingDialogListener;

    /**
     * 给属性赋值之前调一下clear将默认值重置一下。
     */
    public static void clear() {
        notificationTitle = null;

        notificaionSmallIconResId = 0;

        notificaionIconResId = 0;

        downLoadPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/downloads/";

        progressbarDrawable = null;

        progressbarColor = 0;

        progressbarBgColor = 0;

        dialogTitle = "软件更新";

        dialogConfirmText = "确认";

        dialogCancleText = "取消";

        notificationDownloadVisibility = View.INVISIBLE;

        notificationPauseResId = 0;

        notificationContinueResId = 0;

    }
}

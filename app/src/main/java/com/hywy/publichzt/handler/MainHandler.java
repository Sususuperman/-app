package com.hywy.publichzt.handler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RemoteViews;

import com.cs.android.async.AsyncExecutant;
import com.cs.common.utils.FileUtils;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.DownloadToInstallActivity;
import com.hywy.publichzt.activity.MainActivity;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.async.AsyncDownApk;
import com.hywy.publichzt.entity.Upgrade;
import com.hywy.publichzt.task.UpgradeTask;
import com.hywy.publichzt.utils.UpgradeUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * scy
 */
public class MainHandler extends Handler {
    private Activity mActivity;

    public static final int msgid = 999;//消息下载id
    public static final int wShowNotify = 20;
    public static final int wDownMainApk = 16;
    public static final int wDownMainApkSuccess = 17;
    public static final int wDownMainApkFail = 18;
    public static final int wDownMainApkProgress = 19;
    public static final int wShowUpdateDialog = 15;

    public static final int wRegisterPush = 13;
    public static final int wSendPush = 14;

    public static final int wCheckUpgrade = 21;
    public static final int wCheckUpgradeSuccess = 22;

    public static final int wStatisticsTask = 23;
    public static final int wStatisticsTaskSuccess = 24;
    public static final int wStatisticsTaskFail = 25;

    private NotificationManager manager;
    private Notification notif;
    private App app;
    private Upgrade mUpgrade;
    private AsyncDownApk mDownApk;
    private Fragment fragment;
    private boolean isShowWait = false;

    public MainHandler(Activity mActivity, AsyncDownApk mDownApk, Fragment fragment) {
        this.mActivity = mActivity;
        this.app = App.getInstence();
        this.mDownApk = mDownApk;
        this.fragment = fragment;

    }

    public MainHandler(Activity mActivity, AsyncDownApk mDownApk) {
        this(mActivity, mDownApk, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case wCheckUpgrade:
                wCheckUpgrade((Map<String, Object>) msg.obj);
                break;
            case wCheckUpgradeSuccess:
                wCheckUpgradeSuccess((Map<String, Object>) msg.obj);
                break;
            case wShowUpdateDialog:
                wShowUpdateDialog((Upgrade) msg.obj);
                break;
            case wShowNotify:
                showNotify((Upgrade) msg.obj);
                break;
            case wDownMainApk:
                downMainApk((Upgrade) msg.obj);
                break;
            case wDownMainApkSuccess:
                downMainApkSuccess((Map<String, Object>) msg.obj);
                break;
            case wDownMainApkFail:
                downFail((Map<String, Object>) msg.obj);
                break;
            case wDownMainApkProgress:
                downMainApkProgress(msg.arg1, msg.arg2);
                break;
            case wStatisticsTask:
                wStatisticsTask();
                break;
            case wStatisticsTaskSuccess:
                wStatisticsTaskSuccess();
                break;
            case wStatisticsTaskFail:
                break;
        }
    }

    private void wStatisticsTask() {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("user_id", app.getAccount().getUser_id());
    }

    private void wStatisticsTaskSuccess() {
    }


    public boolean isShowWait() {
        return isShowWait;
    }

    public void setShowWait(boolean isShowWait) {
        this.isShowWait = isShowWait;
    }


    private void wCheckUpgrade(Map<String, Object> params) {
        UpgradeTask task = new UpgradeTask(mActivity, params);
        Integer wait = isShowWait ?  R.string.loading : null;
        AsyncExecutant exe = new AsyncExecutant(mActivity, task,wait, this, wCheckUpgradeSuccess, null);
        exe.execute();

    }

    private void wCheckUpgradeSuccess(Map<String, Object> result) {
        if (result.containsKey("result")) {
            Upgrade upgrade = (Upgrade) result.get("result");
            wShowUpdateDialog(upgrade);
        }
    }

    private void wShowUpdateDialog(final Upgrade upgrade) {
        final MaterialDialog dialog = new MaterialDialog(mActivity);
        dialog.setTitle("软件更新");
        dialog.setMessage("大小:" + StringUtils.formatSize(upgrade.getSize()));
        dialog.setPositiveButton(mActivity.getString(R.string.okButton), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!UpgradeUtils.isCheckVersion(upgrade, mActivity)) {
                    showNotify(upgrade);
                } else {
                    installApk(upgrade, mActivity.getString(R.string.down_end_install_re));
                }
            }
        });
        dialog.setNegativeButton(mActivity.getString(R.string.cancelButton), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * 下载.apk
     */
    public void showNotify(Upgrade upgrade) {
        if (app.getNetworkMng().isCanConnect()) {
//            updateFlag(upgrade,Upgrade.ING_UPGRDE);
            Intent intent = new Intent(mActivity, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(mActivity, upgrade.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
            notif = new Notification();
            notif.icon = R.drawable.ic_logo;
            notif.tickerText = mActivity.getString(R.string.down_office_notify_title);
            //通知栏显示所用到的布局文件
            notif.contentView = new RemoteViews(mActivity.getPackageName(), R.layout.notify_down_view);
            notif.contentIntent = pIntent;
            manager.notify(upgrade.getId(), notif);
            Message msg = new Message();
            msg.what = wDownMainApk;
            msg.obj = upgrade;
            sendMessage(msg);
        } else {
            IToast.toast(R.string.down_plugin_ing);
        }
    }

//    /**
//     * 更新数据库升级状态
//     */
//    public void updateFlag(Upgrade upgrade,int flag){
//        if(upgrade != null && upgrade.getId() != msgid){//-1表示不更新数据库字段
//            UpgradeDao dao = new UpgradeDao(mActivity);
//            upgrade.setUpgrdeFlag(flag);
//            dao.updateFlag(upgrade);
//        }
//    }

    /**
     * 下载
     */
    private void downMainApk(Upgrade upgrade) {
        this.mUpgrade = upgrade;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("downapk", true);
        params.put("client_url", upgrade.getUrl());
        params.put("upgrade", upgrade);
        params.put("handler", this);
        params.put("progress", wDownMainApkProgress);
        params.put("success", wDownMainApkSuccess);
        params.put("failure", wDownMainApkFail);
        app.getIsDownMainApk().set(false);
        mDownApk.loadApk(params, app.getIsDownMainApk());

    }

    /**
     * office应用文件下载中
     *
     * @param size
     * @param client_size
     */
    private void downMainApkProgress(int size, int client_size) {
        int progress = size * 100 / client_size;
        notif.contentView.setTextViewText(R.id.notify_text, progress + "%");
        notif.contentView.setProgressBar(R.id.notify_progress, 100, progress, false);
        String apkName = StringUtils.getFilename(mUpgrade.getUrl());
        notif.contentView.setTextViewText(R.id.notify_text_name, apkName + mActivity.getString(R.string.down_office_downing));
        manager.notify(mUpgrade.getId(), notif);
    }

    /**
     * office软件下载成功
     */
    private void downMainApkSuccess(Map<String, Object> result) {
        if (result.containsKey("upgrade")) {
            Upgrade upgrade = (Upgrade) result.get("upgrade");
            if (result.containsKey("path") && result.get("path") != null) {
                String path = result.get("path").toString();
                IToast.toast(R.string.down_office_success);
                String sdPath = UpgradeUtils.getApkPath(path.toString());
                File file = new File(sdPath);
                if (file.exists()) {
                    file.delete();
                }

                FileUtils.copy(path.toString(), sdPath);
                installApk(upgrade, mActivity.getString(R.string.down_office_success));
                //刷新界面
                if (mActivity != null) {
//                    updateFlag(upgrade, Upgrade.SHOW_UPGRDE);
                }
            } else {
                downFail(result);
            }
        }
    }

    /**
     * office软件下载完成提示安装
     */
    public void installApk(Upgrade upgrade, String content) {
        notif = null;
        Intent intent = new Intent(mActivity, DownloadToInstallActivity.class);
        intent.putExtra("upgrade", upgrade);
        mActivity.startActivity(intent);
        PendingIntent pIntent = PendingIntent.getActivity(mActivity, upgrade.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notif == null)
            notif = new Notification();
        content = upgrade.getName() + content;
        notif.icon = R.drawable.ic_logo;
        notif.tickerText = mActivity.getString(R.string.down_office_installApp_notify_title);
        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        //通知栏显示所用到的布局文件
        notif.contentView = new RemoteViews(mActivity.getPackageName(), R.layout.notify_down_end);
        notif.contentIntent = pIntent;
        notif.contentView.setTextViewText(R.id.notify_text, content);
        manager.notify(upgrade.getId(), notif);
    }

    /**
     * 下载失败提示
     */
    private void downFail(Map<String, Object> result) {
        IToast.toast(R.string.down_error);
        NotificationManager noManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        noManager.cancel(mUpgrade.getId());
        failUpdate(result);
    }

    private void failUpdate(Map<String, Object> result) {
        if (result.containsKey("upgrade")) {
            //刷新界面
            if (mActivity != null) {
                Upgrade upgrade = (Upgrade) result.get("upgrade");
//                updateFlag(upgrade, Upgrade.SHOW_UPGRDE);
            }
        }
    }
}

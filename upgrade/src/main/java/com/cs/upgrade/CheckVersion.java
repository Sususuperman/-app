package com.cs.upgrade;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.cs.android.task.Task;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.StringUtils;
import com.cs.upgrade.entity.Config;
import com.cs.upgrade.entity.FileBean;
import com.cs.upgrade.entity.Upgrade;
import com.cs.upgrade.entity.VersionUpdateConfig;
import com.cs.upgrade.task.UpgradeTask;
import com.cs.upgrade.utils.UpgradeUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 使用方法
 * CheckVersion.builder()
 * .setContext(this)
 * .setBaseUrl(HttpUrl.getUpgradeUrl())
 * .setNotificationTitle(getString(R.string.app_name))
 * .setNotificationIconRes(R.drawable.ic_launcher)
 * .setNotificationDownloadInfoVisibility(View.INVISIBLE)
 * .start();
 * <p>
 * Created by Superman on 2017/10/16.
 */

public class CheckVersion {


    public static CheckVersionBuild builder() {

//        Config.clear();

        return new CheckVersionBuild();
    }

    public static class CheckVersionBuild {
        private Context context;
        private String url;
        private boolean is_show;//显示加载动画
        private OnBackResultListener onBackResultListener;
        private OnDownloadingDialogListener onDownloadingDialogListener;

        public void start() {
            //请求接口
            Map<String, Object> params = new HashMap<>();
            Task task = new UpgradeTask(context, params, url);

            SpringViewHandler handler = new SpringViewHandler(context);
            handler.request(params, task);
            handler.setBuilder(new BaseHandler_.Builder()
                    .isWait(is_show)
                    .isShowToast(false));
            handler.setListener(new OnPostListenter() {
                @Override
                public void OnPostSuccess(Map<String, Object> result) {
                    checkUpgradeSuccess(result);
                }

                @Override
                public void OnPostFail(Map<String, Object> result) {
                }
            });
        }


        private void checkUpgradeSuccess(Map<String, Object> result) {
            if (result.containsKey("result")) {
                Upgrade upgrade = (Upgrade) result.get("result");
                if (onBackResultListener != null) {
                    onBackResultListener.OnResultSuccess(upgrade);
                }
                showUpdateDialog(upgrade);
            }
        }


        private void showUpdateDialog(final Upgrade upgrade) {
            final MaterialDialog dialog = new MaterialDialog(context);
            dialog.setTitle(Config.dialogTitle);
//            "大小:" + StringUtils.formatSize(upgrade.getSize()) +"\n" +
            dialog.setMessage(upgrade.getContent());
            dialog.setPositiveButton(Config.dialogConfirmText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //判断是否已经下载过该版本的apk文件
                    if (!UpgradeUtils.isCheckVersion(upgrade, context)) {
                        VersionUpdateConfig.getInstance()
                                .setContext(context)
                                .setDownLoadURL(upgrade.getUrl())
                                .startDownLoad();
                    } else {
                        installPage(UpgradeUtils.getApkPath(upgrade.getUrl()));
                    }
                }
            });
            dialog.setNegativeButton(Config.dialogCancleText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    System.exit(0);
                }
            });

            dialog.show();
        }

        /**
         * 自动安装
         */
        private void installPage(String filepath) {
            File file = new File(filepath);
            if (!file.exists()) {
                throw new NullPointerException(
                        "The package cannot be found");
            }
            Intent install = new Intent(Intent.ACTION_VIEW);
            // 调用系统自带安装环境
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Log.w("CCC", "pageName" + context.getPackageName());
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                install.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(install);
            System.exit(0);
        }

        /**
         * 设置请求链接
         *
         * @param url
         * @return
         */
        public CheckVersionBuild setBaseUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置上下文
         *
         * @param context
         * @return
         */
        public CheckVersionBuild setContext(Context context) {
            this.context = context;
            return this;
        }

        /**
         * 设置检测版本返回值回调，不设置不返回。
         *
         * @param onBackResultListener
         * @return
         */
        public CheckVersionBuild setCallbackListener(OnBackResultListener onBackResultListener) {
            this.onBackResultListener = onBackResultListener;
            return this;
        }

        /**
         * 设置progressdialog下载进度监听回调
         */
        public CheckVersionBuild setDownloadingDialogListener(OnDownloadingDialogListener onDownloadingDialogListener) {
            this.onDownloadingDialogListener = onDownloadingDialogListener;
            Config.onDownloadingDialogListener = onDownloadingDialogListener;
            return this;
        }


        /**
         * 必填
         * 设置广播意图，区分，
         *
         * @param str
         * @return
         */
        public CheckVersionBuild setAction(String str) {
            Config.ACTION_START = "ACTION_START_" + str;
            Config.ACTION_PAUSE = "ACTION_PAUSE_" + str;
            Config.ACTION_REFRESH = "ACTION_REFRESH_" + str;
            Config.ACTION_FININSHED = "ACTION_FININSHED_" + str;
            Config.ACTION_BUTTON = "ACTION_BUTTON_" + str;
            return this;
        }

        /**
         * 设置文件保存路径
         *
         * @param path
         * @return
         */
        public CheckVersionBuild setFileSavePath(String path) {
            Config.downLoadPath = path;
            return this;
        }

        /**
         * 設置进度条背景
         *
         * @param drawable
         * @return
         */
        public CheckVersionBuild setProgressbarDrawable(Drawable drawable) {
            Config.progressbarDrawable = drawable;
            return this;
        }
//
//        /**
//         * 設置进度条颜色
//         *
//         * @param color
//         * @return
//         */
//        public CheckVersionBuild setProgressbarColor(int color) {
//            Config.progressbarColor = color;
//            return this;
//        }

//        /**
//         * 設置进度条背景颜色
//         *
//         * @param color
//         * @return
//         */
//        public CheckVersionBuild setProgressbarBgColor(int color) {
//            Config.progressbarBgColor = color;
//            return this;
//        }

        /**
         * 是否显示请求加载框
         *
         * @param isshow
         * @return
         */
        public CheckVersionBuild setDialogWaitShow(boolean isshow) {
            this.is_show = isshow;
            return this;
        }

        /**
         * 设置弹出框标题
         *
         * @param title
         * @return
         */
        public CheckVersionBuild setDialogTitle(String title) {
            Config.dialogTitle = title;
            return this;
        }

        /**
         * 设置弹出框确认按钮文字
         *
         * @param str
         * @return
         */
        public CheckVersionBuild setDialogConfirmText(String str) {
            Config.dialogConfirmText = str;
            return this;
        }

        /**
         * 设置弹出框取消按钮文字
         *
         * @param str
         * @return
         */
        public CheckVersionBuild setDialogCancleText(String str) {
            Config.dialogCancleText = str;
            return this;
        }

        /**
         * 设置通知标题
         */
        public CheckVersionBuild setNotificationTitle(String title) {
            Config.notificationTitle = title;
            return this;
        }

        /**
         * 设置通知图标
         */
        public CheckVersionBuild setNotificationIconRes(int resId) {
            Config.notificaionIconResId = resId;
            return this;
        }

        /**
         * 设置通知小图标
         */
        public CheckVersionBuild setNotificationSmallIconRes(int resId) {
            Config.notificaionSmallIconResId = resId;
            return this;
        }

        /**
         * 设置通知暂停图标
         */
        public CheckVersionBuild setNotificationPauseIconRes(int resId) {
            Config.notificationPauseResId = resId;
            return this;
        }

        /**
         * 设置通知继续下载图标
         */
        public CheckVersionBuild setNotificationContinueIconRes(int resId) {
            Config.notificationContinueResId = resId;
            return this;
        }

        /**
         * 设置是否显示通知下载信息
         */
        public CheckVersionBuild setNotificationDownloadInfoVisibility(int visibility) {
            Config.notificationDownloadVisibility = visibility;
            return this;
        }


    }
}

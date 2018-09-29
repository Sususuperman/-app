package com.hywy.publichzt.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.cs.common.base.BaseActivity;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.baserx.RxAction;
import com.cs.common.utils.IToast;
import com.cs.common.utils.Logger;
import com.cs.upgrade.CheckVersion;
import com.cs.upgrade.OnDownloadingDialogListener;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.fragment.ChildFragment1;
import com.hywy.publichzt.activity.fragment.ChildFragment2;
import com.hywy.publichzt.activity.fragment.ChildFragment3;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.utils.StartIntent;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.hywy.publichzt.action.RxAction.MAIN_FRAGMENT_1_REFRESH;

public class MainActivity extends BaseToolbarActivity implements OnDownloadingDialogListener {

    @Bind(R.id.bottom_bar1)
    RadioButton bottomBar1;
    private ChildFragment1 mFragment1;
    private ChildFragment2 mFragment2;
    private ChildFragment3 mFragment3;
    //    private ChildFragment3 mFragment4;
    private String[] mainTabs;
    private int position;
    private App app;
    private ProgressDialog progressDialog;

    public static final void startAction(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("currentItem");
        }
        init();
        initListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentItem", position);
    }

    private void init() {
        setTitleBulider(new Bulider().title("六安河长公众版"));
        mToolbar.setNavigationIcon(null);
        app = App.getInstance();
        mainTabs = getResources().getStringArray(R.array.main_tabs);
        setCurSelTab(0);
//        down();
    }

    private void down() {
        CheckVersion.builder()
                .setContext(this)
                .setAction("LUANHZT")
                .setBaseUrl(HttpUrl.getUpgradeUrl())
                .setFileSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/luanhzt/")
                .setNotificationTitle(this.getString(R.string.app_name))
                .setNotificationIconRes(R.drawable.ic_logo)
                .setNotificationDownloadInfoVisibility(View.INVISIBLE)
                .setDialogWaitShow(false)
                .setDialogConfirmText("立即更新")
                .setDialogCancleText("暂不更新")
                .setDownloadingDialogListener(this)
                .start();
    }

    @Override
    public void onPreUpdate() {
        showDownLoadDialog();
    }

    @Override
    public void onProgressUpdate(int progress) {
        if (progressDialog.isShowing())
            progressDialog.setProgress(progress);
    }

    /**
     * 强制退出
     */
    @Override
    public void onFinishedUpdate() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            StartIntent.startExitApp(this);
            finish();
        }
    }

    /***
     * 下载进度框
     */
    private void showDownLoadDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("下载");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.show();

    }


    private void initListener() {
        mRxManager.on(RxAction.RELOGIN_ACTION, new Action1<Object>() {
            @Override
            public void call(Object o) {
                StartIntent.startExit(MainActivity.this);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String deviceId = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }


    private void setCurSelTab(int nIndex) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fm);
        String tabname = mainTabs[nIndex];
        switch (nIndex) {
            case 0:
                if (null == mFragment1) {
                    mFragment1 = ChildFragment1.newInstance(tabname);
                    fm.add(R.id.fragment, mFragment1);
                } else {
                    fm.show(mFragment1);
                }

                if (mFragment1.isVisible()) {
                    mRxManager.post(MAIN_FRAGMENT_1_REFRESH, "");
                }
                break;
            case 1:
                if (null == mFragment2) {
                    mFragment2 = ChildFragment2.newInstance();
                    fm.add(R.id.fragment, mFragment2);
                } else {
                    fm.show(mFragment2);
                }
                break;
            case 2:
                if (null == mFragment3) {
                    mFragment3 = ChildFragment3.newInstance(tabname);
                    fm.add(R.id.fragment, mFragment3);
                } else {
                    fm.show(mFragment3);
                }
                break;
        }
        fm.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }
        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }

    }

    @OnClick({R.id.bottom_bar1, R.id.bottom_bar2, R.id.bottom_bar3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_bar1:
                position = 0;
                break;
            case R.id.bottom_bar2:
                position = 1;
                break;
            case R.id.bottom_bar3:
                position = 2;
                break;
        }
        setCurSelTab(position);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Timer tExit = null;
            if (isExit == false) {
                isExit = true; // 准备退出
                IToast.toast("再按一次退出程序");
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 1000); // 如果1秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

            } else {
                StartIntent.startExitApp(this);
                finish();
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complain, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complain) {
            CreateComplainActivity.startAction(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

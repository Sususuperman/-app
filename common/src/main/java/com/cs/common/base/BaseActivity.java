package com.cs.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cs.common.baserx.RxManager;
import com.cs.common.handler.WaitDialog;

import butterknife.ButterKnife;

/**
 * Activity基类
 *
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected boolean isActivityResult = false;//判断如果调用onActivityResult,且需要关闭当前界面.如需关闭则在oncreate中设置该值为true 反之不需要设置
    protected View loadFailRelativeLayout;
    public final static int request_activity_finish = 10000;
    public RxManager mRxManager;
    protected WaitDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void setResult(boolean result) {
        this.isActivityResult = result;
    }


    public void finish() {
        super.finish();
        System.gc();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
        ButterKnife.unbind(this);
//		AppManager.getAppManager().finishActivity(this);
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            finish();
//        }
//        return false;
//    }

    /**
     * 显示加载失败的提示
     */
    protected void showLoadFail() {
        loadFailRelativeLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏加载失败的提示
     */
    protected void hideLoadFail() {
        loadFailRelativeLayout.setVisibility(View.GONE);
    }

    public void hideKeyboard() {
        if (null != this.getCurrentFocus()) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 显示键盘
     */
    @SuppressWarnings("static-access")
    protected void showSoftInput(EditText et) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);

    }

    /**
     * 隐藏键盘
     */
    @SuppressWarnings("static-access")
    protected void hideSoftInput(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCancel(resultCode) && isActivityResult) {
            switch (requestCode) {
                case request_activity_finish:
                    if (data != null && data.getExtras() != null)
                        this.setResult(RESULT_OK, data);
                    else {
                        this.setResult(RESULT_OK);
                    }
                    this.finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 实际上是取消了动作
     *
     * @param resultCode
     * @return
     */
    @SuppressWarnings("static-access")
    public boolean isCancel(int resultCode) {
        if (resultCode == RESULT_CANCELED)// 实际上是取消了动作
        {
            return false;
        }
        return true;

    }

    protected void showDialog(String msg){
        dialog = new WaitDialog(this,msg);
        dialog.show();
    }

    protected void dismissDialog(){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}

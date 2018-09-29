package com.cs.common.handler;

import android.content.Context;
import android.os.Message;

import com.cs.android.async.AsyncExecutant;
import com.cs.android.common.R;
import com.cs.android.task.Task;
import com.cs.common.HttpConst;
import com.cs.common.baserx.RxAction;
import com.cs.common.baserx.RxManager;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.IToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weifei on 2015/7/2.
 * 该类只适用于可以刷新网络数据，加载下一页数据。本地加载更多不适用.
 */
public class SpringViewHandler extends BaseHandler_<OnPostListenter> {
    private static final int wNetStart = 103;
    private static final int wNetSuccess = 101;
    private static final int wNetFailure = 102;

    private static final String InitKey = "init_key";
    private static final String NextKey = "Next_key";
    private Map<String, Map<String, Object>> paramsMap = new HashMap<String, Map<String, Object>>();
    private Task task;

    public SpringViewHandler(Context c, OnPostListenter listenter) {
        super(c, listenter);
    }

    public SpringViewHandler(Context c) {
        this(c, null);
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case wNetSuccess://成功
                wNetSuccess((Map<String, Object>) msg.obj);
                break;
            case wNetStart://开始
                wNetStart((Map<String, Object>) msg.obj);
                break;
            case wNetFailure://失败
                wNetFailure((Map<String, Object>) msg.obj);
                break;
        }
    }


    private void wNetSuccess(Map<String, Object> result) {
        if (result.containsKey("msg")) {
            String msg = result.get("msg").toString();
            if (msg .equals(HttpConst.Success) ) {
                if (getListener() != null) {
                    getListener().OnPostSuccess(result);
                }
            }
//            else if (ret == HttpConst.ApiTokenError || ret == HttpConst.ApiPwdError || ret == 1) {
//                if (result.containsKey("msg") && builder.isShowToast)
//                    IToast.toast(result.get("msg").toString());
//                //如果是登录界面进来的，就不需要
//                if (!builder.isLogin) {
//                    RxManager manager = new RxManager();
//                    manager.post(RxAction.RELOGIN_ACTION, "");
//                }
//                    StartIntent.s(mContext);
//            }
            else {
                if (result.containsKey("msg") && builder.isShowToast) {
                    IToast.toast(result.get("msg").toString());
                }
                if (getListener() != null) {
                    getListener().OnPostFail(result);
                }
            }
        }
    }

    private void wNetFailure(Map<String, Object> result) {
        if (getListener() != null) {
            getListener().OnPostFail(result);
        }
        if (builder.isShowToast) {
            IToast.toast(R.string.loadfail);
        }
    }

    private void wNetStart(Map<String, Object> params) {
        task.setParams(params);
        Integer wiat = builder.isWait ? R.string.loading : null;
        AsyncExecutant exe = new AsyncExecutant(mContext, task, wiat, this, wNetSuccess, wNetFailure);
        exe.execute();
    }

    public void request(Map params, Task task) {
        this.task = task;
        addParams(params, InitKey);
        MsgHandler.sendMessage(params, this, wNetStart);
    }

    private void addParams(Map<String, Object> params, String key) {
        paramsMap.put(key, params);
    }

    public Map<String, Object> getParams() {
        return paramsMap.get(InitKey);
    }

    public interface onReLoginListener {
        void onReLogin();
    }
}

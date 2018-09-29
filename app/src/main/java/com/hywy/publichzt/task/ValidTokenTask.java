package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.android.task.Task;
import com.hywy.publichzt.Const;
import com.hywy.publichzt.R;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Account;
import com.hywy.publichzt.exceptions.BackGroundLoginException;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有需要登陆后才能访问服务器的网络操作父类,具体操作实现sendData方法
 *
 * @author james
 */
public abstract class ValidTokenTask implements Task {
    protected Context context;
    protected Map<String, Object> params;
    private static final String TAG = "ValidTokenTask";

    public ValidTokenTask(Context context, Map<String, Object> params) {
        super();
        this.context = context;
        this.params = params;
    }

    public ValidTokenTask(Context context) {
        this(context, null);
    }

    @Override
    public Map<String, Object> execute() throws Exception {
        App app = (App) (context.getApplicationContext());
        // 如果网络不能连接，立即返回
        if (!app.getNetworkMng().isCanConnect()) {
            Map<String, Object> r = new HashMap<String, Object>();
            r.put("ret", Const.ApiNoNet);
            r.put("msg", context.getApplicationContext().getString(R.string.netWorkError));
            r.putAll(params);
            return r;
        }

        //当前六安系统无登录操作
//        validateToken();

        Map<String, Object> result = sendData(getCommonParams());

//        // 将最后登陆时间也更新一下,因为刚才的操作在服务器更新了用户最后访问时间 暂时无此操作
//        app.setAccount(app.getAccount());
        return result;
    }

    // 同步方法,确认当前登陆是一个有效的登陆
    private void validateToken() throws Exception {
        App app = App.getInstance();
        synchronized (app) {
            // 如果是有效的登陆，则直接返回
            if (app.isValidToken()) {
                return;
            }

            Account account = app.getAccount();
            // 如果没有用户登陆，则直接返回错误
            if (account == null) {
                throw new BackGroundLoginException();
            }

//            // 现在是登陆已经超时了，并且用户也曾经登陆，所以直接登陆，然后发送数据
//            Map<String, Object> p = new HashMap<String, Object>();
//            p.put("account", account.getAccountId());
//            p.put("password", account.getPassword());
//            Task task = new LoginTask(context, p);
//            p = task.execute();
//            // 后台登陆不成功，所以直接返回错误
//            if (!p.get("ret").equals(Const.ApiSuccess)) {
//                throw new BackGroundLoginException();
//            }
        }
    }


    private Map<String, Object> getCommonParams() {
        Map<String, Object> p = new HashMap<String, Object>();
        if (params != null) {
            p.putAll(params);
        }
        return p;
    }


    @Override
    public Object getParam() {
        return this.params;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 提交服务时,要将此params参数提交给HttpUtils的方法,可以删除或者添加参数.
     *
     * @param params
     * @return
     * @throws Exception
     */
    protected abstract Map<String, Object> sendData(Map<String, Object> params)
            throws Exception;


}

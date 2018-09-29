package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.HttpUtils;
import com.google.gson.Gson;
import com.hywy.publichzt.Const;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.dao.AccountDao;
import com.hywy.publichzt.entity.Account;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆的异步任务，
 *
 * @author james
 */
public class LoginTask extends ValidSourceTask {
    public LoginTask(Context context, Map<String, Object> params) {
        super(context, params);
    }

    public LoginTask(Context context) {
        this(context, null);
    }

    @Override
    protected Map<String, Object> sendData(Map<String, Object> params)
            throws Exception {
        App app = (App) context.getApplicationContext();
        String content = HttpUtils.postString(HttpUrl.getUrl(HttpUrl.RMS_LOGIN_LOGIN), params);
        JSONObject jo = new JSONObject(content);
        Map<String, Object> result = new HashMap<String, Object>();
        String pass = params.remove("password").toString();
//        IToast.toast(jo.toString());
        if (jo.has("msg")) {
            if (jo.getString("msg").equals(Const.SUCCESS)) {// 成功
                Account account = new Gson().fromJson(jo.toString(), Account.class);
                account.setPassword(pass);
                account.setLast_login_time(System.currentTimeMillis());
                app.setAccount(account);
//            app.setToken(account.getToken());
                AccountDao dao = new AccountDao(context);
                dao.replace(account);
                result.put("msg", jo.getString("msg"));
            } else {// 用户名或密码错误
                result.put("msg", jo.getString("msg"));// 失败原因
            }
        } else {
            Account account = new Gson().fromJson(jo.toString(), Account.class);
            account.setPassword(pass);
            account.setLast_login_time(System.currentTimeMillis());
            app.setAccount(account);
//            app.setToken(account.getToken());
            AccountDao dao = new AccountDao(context);
            dao.replace(account);
            result.put("msg", "err");
        }

        return result;
    }
}

package com.hywy.publichzt.task;

import android.content.Context;
import android.util.Log;

import com.hywy.publichzt.Const;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.utils.HttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charmingsoft on 2015/12/14.
 */
public abstract class BaseRequestTask extends ValidTokenTask {

    protected Map<String, Object> result;

    public BaseRequestTask(Context context, Map<String, Object> params) {
        super(context, params);
        result = new HashMap<String, Object>();
    }

    public BaseRequestTask(Context context) {
        this(context, null);
    }

    @Override
    protected Map<String, Object> sendData(Map<String, Object> params) throws Exception {
        String url = url();
        Log.i("url", url + "");
        if (url == null) {
            throw new NullPointerException(" 'url' can not be empty!");
        }

        postAfter(params);

        String content = "";
        if (isPost()) {
            content = HttpUtils.postString(url, params);
        } else {
            content = HttpUtils.getString(url, params);
        }
        Log.d("http", url + "");
        Log.e("content", content);

        JSONObject jo = new JSONObject(content);
        if (jo.has(Key.MSG)) {
            result.put(Key.MSG, jo.getString(Key.MSG));
            String msg = jo.getString(Key.MSG);
            if (msg.equals(Const.SUCCESS)) {
                if (!jo.isNull("Data")) {
                    result.put(Key.RESULT, request(jo.getString("Data")));
                } else if (!jo.isNull(Key.DATA)) {
                    result.put(Key.RESULT, request(jo.getString(Key.DATA)));
                }
            } else {
                requestException(msg);
                result.put(Key.MSG, jo.getString(Key.MSG));
            }
        } else {
            if (!jo.isNull("Data")) {
                result.put(Key.RESULT, request(jo.getString("Data")));
                result.put(Key.MSG, Const.SUCCESS);
            } else if (!jo.isNull(Key.DATA)) {
                result.put(Key.RESULT, request(jo.getString(Key.DATA)));
                result.put(Key.MSG, Const.ERROR);
            }
        }

        return result;
    }

    /**
     * 返回true为post请求，返回false为get请求
     * 默认为get
     *
     * @return
     */
    public abstract String url();

    /**
     * 返回true为post请求，返回false为get请求
     * 默认为get
     *
     * @return
     */
    public abstract boolean isPost();

    /**
     * 请求成功进行解析
     *
     * @param json
     * @return
     * @throws Exception
     */
    public abstract Object request(String json) throws Exception;

    /**
     * 请求失败后调用
     *
     * @param msg
     */
    public void requestException(String msg) {

    }

    /**
     * 请求前可以对参数进行更改
     *
     * @param params
     */
    public void postAfter(Map<String, Object> params) {

    }

    protected String getJsonArrays(String json, String strjson) {
        try {
            JSONObject js = new JSONObject(json);
            return js.getString(strjson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

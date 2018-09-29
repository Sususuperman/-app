package com.cs.upgrade.task;

import android.content.Context;

import com.cs.common.HttpConst;
import com.cs.common.Key;
import com.cs.common.utils.HttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charmingsoft on 2015/12/14.
 */
public abstract class BaseRequestTask extends ValidSourceTask {
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
//        Log.e("content",content);

        JSONObject jo = new JSONObject(content);
        if (jo.has(Key.RET)) {
            result.put(Key.RET, jo.getInt(Key.RET));
            int ret = jo.getInt(Key.RET);
            if (ret == HttpConst.ApiSuccess || ret == HttpConst.ApiNoData) {
                if (!jo.isNull(Key.DATA)) {
                    result.put(Key.RESULT, request(jo.getString(Key.DATA)));
                    if (ret == HttpConst.ApiNoData) {
                        if (jo.has(Key.MSG))
                            result.put(Key.MSG, jo.get(Key.MSG));
                    }
                } else {
                    if (ret == HttpConst.ApiNoData) {
                        if (jo.has(Key.MSG)) {
                            result.put(Key.MSG, jo.get(Key.MSG));
                        }
                    }
                }
            } else {
                requestException(ret);
                if (jo.has(Key.MSG))
                    result.put(Key.MSG, jo.get(Key.MSG));
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
    public void requestException(int msg) {

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

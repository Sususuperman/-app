package com.hywy.publichzt.task;

import android.content.Context;

import com.hywy.publichzt.HttpUrl;

import java.util.Map;


/**
 * 新建投诉建议
 *
 * @author Superman
 */
public class PostCreateComplainTask extends BaseRequestTask {
    public PostCreateComplainTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_COMPLAIN_APPSAVE);
    }

    @Override
    public void postAfter(Map<String, Object> params) {
    }

    @Override
    public boolean isPost() {
        return true;
    }

    @Override
    public Object request(String json) throws Exception {
//        String js = new JSONObject(json).getString("data");
//        Organ organ = new Gson().fromJson(json,Organ.class);
        return json;
    }
}

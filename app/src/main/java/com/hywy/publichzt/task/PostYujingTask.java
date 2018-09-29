package com.hywy.publichzt.task;

import android.content.Context;

import com.hywy.publichzt.HttpUrl;

import java.util.Map;


/**
 * 预警发布
 *
 * @author Superman
 */
public class PostYujingTask extends BaseRequestTask {
    public PostYujingTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_WARN_R_APPSAVE);
    }

    @Override
    public void postAfter(Map<String, Object> params) {
        params.remove("USER_ID");
    }

    @Override
    public boolean isPost() {
        return true;
    }

    @Override
    public Object request(String json) throws Exception {
        return json;
    }
}

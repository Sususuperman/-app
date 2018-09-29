package com.hywy.publichzt.task;

import android.content.Context;

import com.hywy.publichzt.HttpUrl;

import java.util.Map;


/**
 * 转交下一步
 *
 * @author Superman
 */
public class PostCreateEventTask extends BaseRequestTask {
    public PostCreateEventTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_EVENT_PROCESS_APPDONEXT);
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
//        String js = new JSONObject(json).getString("data");
//        Organ organ = new Gson().fromJson(json,Organ.class);
        return json;
    }
}

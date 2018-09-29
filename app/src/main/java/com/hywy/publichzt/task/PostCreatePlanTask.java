package com.hywy.publichzt.task;

import android.content.Context;

import com.hywy.publichzt.HttpUrl;

import java.util.Map;


/**
 * 路线上报
 *
 * @author Superman
 */
public class PostCreatePlanTask extends BaseRequestTask {
    public PostCreatePlanTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PATROL_LINE_APPSAVE);
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

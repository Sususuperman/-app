package com.hywy.publichzt.task;

import android.content.Context;

import com.hywy.publichzt.HttpUrl;

import java.util.Map;


/**
 * 催办
 *
 * @author Superman
 */
public class PostCuibanEventTask extends BaseRequestTask {
    public PostCuibanEventTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_EVENT_PROCESS_SENDMSG);
    }

    @Override
    public void postAfter(Map<String, Object> params) {
        params.remove("USER_ID");
    }

    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        return json;
    }
}

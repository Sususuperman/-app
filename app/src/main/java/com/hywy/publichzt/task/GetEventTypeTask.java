package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.EventSupervise;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 事件类型列表
 *
 * @author Superman
 */

public class GetEventTypeTask extends BaseRequestTask {

    public GetEventTypeTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_EVENTANALY_FINDEVENTTYPE);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<EventSupervise>>() {
        }.getType();
        List<EventSupervise> list = new Gson().fromJson(json, type);

        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

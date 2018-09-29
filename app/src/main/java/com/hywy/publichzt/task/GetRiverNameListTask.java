package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.River;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 河道名称列表
 *
 * @author Superman
 */

public class GetRiverNameListTask extends BaseRequestTask {

    public GetRiverNameListTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PATROL_PLAN_FINDREACHBASE);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<River>>() {
        }.getType();
        List<River> list = new Gson().fromJson(json, type);

        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.River;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 根据行政区划查询河段
 *
 * @author Superman
 */

public class GetRiverListByAdcdTask extends BaseRequestTask {

    public GetRiverListByAdcdTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_REACH_FINDLISTBYADCD);
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

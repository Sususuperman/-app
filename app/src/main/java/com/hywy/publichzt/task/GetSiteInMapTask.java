package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.WaterRain;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 获取一张图测站信息列表
 *
 * @author Superman
 */

public class GetSiteInMapTask extends BaseRequestTask {

    public GetSiteInMapTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_MAP_LISTSTION);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<WaterRain>>() {
        }.getType();
        List<WaterRain> list = new Gson().fromJson(json, type);
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

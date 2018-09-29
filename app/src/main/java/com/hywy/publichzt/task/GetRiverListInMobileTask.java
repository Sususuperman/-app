package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.River;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 移动巡查~创建日志选择河道~无巡查计划
 *
 * @author Superman
 */

public class GetRiverListInMobileTask extends BaseRequestTask {

    public GetRiverListInMobileTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_ATROL_PLAN_FINDREACHBASE);
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

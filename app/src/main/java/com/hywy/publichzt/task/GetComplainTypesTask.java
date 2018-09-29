package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.EventListItem;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.entity.EventSupervise;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉类型列表task
 *
 * @author Superman
 */

public class GetComplainTypesTask extends BaseRequestTask {

    public GetComplainTypesTask(Context context) {
        super(context);
    }
    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_COMPLAIN_GETTYPE);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Complain>>() {
        }.getType();
        List<Complain> list = new Gson().fromJson(json, type);
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

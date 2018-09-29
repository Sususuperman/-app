package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.Upgrade;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class Upgrade2Task extends BaseRequestTask {

    public Upgrade2Task(Context context, Map<String, Object> params) {
        super(context, params);
    }
    public Upgrade2Task(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUpgradeUrl();
    }

    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Upgrade>>() {
        }.getType();
        List<Upgrade> list = new Gson().fromJson(json, type);
        return list;
    }

}

package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.PlanItem;
import com.hywy.publichzt.entity.Plan;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择巡查计划列表
 *
 * @author Superman
 */

public class GetPlanListTask extends BaseRequestTask {
    private Map<String, River> titleMap = new HashMap<>();
    private String id = "";

    public GetPlanListTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PATROL_PLAN_GETPLANLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> list = new Gson().fromJson(json, type);
        List<PlanItem> items = new ArrayList<>();
        for (Plan plan : list) {
            PlanItem item = new PlanItem(plan);
            items.add(item);
    }
        result.put(Key.ITEMS, items);
        return list;
}

    @Override
    public boolean isPost() {
        return false;
    }
}

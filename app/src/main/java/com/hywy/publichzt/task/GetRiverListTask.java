package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.MapClassifyHeaderItem;
import com.hywy.publichzt.adapter.item.RiverItem;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的河道列表
 *
 * @author Superman
 */

public class GetRiverListTask extends BaseRequestTask {
    private Map<String, River> titleMap = new HashMap<>();
    private String id = "";

    public GetRiverListTask(Context context) {
        super(context);
    }

    public GetRiverListTask(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_RIVERWAY_RIVERWAYLISTTREE);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<River>>() {
        }.getType();
        List<River> list = new Gson().fromJson(json, type);
        List<RiverItem> items = new ArrayList<>();
        for (River river : list) {
            RiverItem riverItem = new RiverItem(river, new MapClassifyHeaderItem(river.getRV_NAME()));
            items.add(riverItem);
            List<River> childs = river.getChild();
        }
        result.put(Key.ITEMS, items);
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

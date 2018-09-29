package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.EventListItem;
import com.hywy.publichzt.entity.EventSupervise;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件督办列表task
 *
 * @author Superman
 */

public class GetEventListTask extends BaseRequestTask {

    private int key;

    public GetEventListTask(Context context) {
        super(context);
    }

    public GetEventListTask(Context context, int key) {
        super(context);
        this.key = key;
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_EVENT_CREATE_APPLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<EventSupervise>>() {
        }.getType();
        List<EventSupervise> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<EventListItem> items = new ArrayList<>();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                EventSupervise r = list.get(i);
                EventListItem item = new EventListItem(r);
                items.add(item);
            }
            result.put(Key.ITEMS, items);
        }
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

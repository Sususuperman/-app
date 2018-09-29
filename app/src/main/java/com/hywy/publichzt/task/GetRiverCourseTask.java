package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.RiverCourseItem;
import com.hywy.publichzt.entity.RiverCourse;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 河道水文监测
 *
 * @author Superman
 */

public class GetRiverCourseTask extends BaseRequestTask {

    public GetRiverCourseTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_APP_STRIVER_STLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<RiverCourse>>() {
        }.getType();
        List<RiverCourse> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<RiverCourseItem> items = new ArrayList<>();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                RiverCourse r = list.get(i);
                RiverCourseItem item = new RiverCourseItem(r);
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

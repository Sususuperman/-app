package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.WaterAndRainItem;
import com.hywy.publichzt.entity.WaterRain;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 水雨情监测
 *
 * @author Superman
 */

public class GetWaterAndRainTask extends BaseRequestTask {

    public GetWaterAndRainTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PPTN_R_FINDSTPPTNRS);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<WaterRain>>() {
        }.getType();
        List<WaterRain> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<WaterAndRainItem> items = new ArrayList<>();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                WaterRain r = list.get(i);
                WaterAndRainItem item = new WaterAndRainItem(r);
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

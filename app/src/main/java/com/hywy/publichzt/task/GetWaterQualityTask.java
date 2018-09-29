package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.WaterQualityItem;
import com.hywy.publichzt.entity.WaterQuality;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 水质监测
 *
 * @author Superman
 */

public class GetWaterQualityTask extends BaseRequestTask {

    public GetWaterQualityTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_AWQMD_WQLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<WaterQuality>>() {
        }.getType();
        List<WaterQuality> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<WaterQualityItem> items = new ArrayList<>();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                WaterQuality r = list.get(i);
                WaterQualityItem item = new WaterQualityItem(r);
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

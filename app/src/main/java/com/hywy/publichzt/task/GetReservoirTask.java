package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.ReservoirItem;
import com.hywy.publichzt.entity.Reservoir;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 水库水文监测
 *
 * @author Superman
 */

public class GetReservoirTask extends BaseRequestTask {

    public GetReservoirTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_RSVR_R_RSLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Reservoir>>() {
        }.getType();
        List<Reservoir> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<ReservoirItem> items = new ArrayList<>();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Reservoir r = list.get(i);
                ReservoirItem item = new ReservoirItem(r);
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

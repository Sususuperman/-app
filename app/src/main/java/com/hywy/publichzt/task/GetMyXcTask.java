package com.hywy.publichzt.task;

import android.content.Context;
import android.util.Log;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.InspectionItem;
import com.hywy.publichzt.entity.Inspection;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Superman
 */
public class GetMyXcTask extends BaseRequestTask {
    private int index;

    public GetMyXcTask(Context context, int index) {
        super(context);
        this.index = index;
    }

    public GetMyXcTask(Context context) {
        super(context);
    }

    @Override

    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PATROL_LOG_LIST);
    }


    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Inspection>>() {
        }.getType();
        List<Inspection> list = new Gson().fromJson(json, type);

        List<InspectionItem> items = new ArrayList<>();
        if (StringUtils.isNotNullList(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Inspection r = list.get(i);
                Log.i("points", r.getPoints().size() + "");
                InspectionItem item = new InspectionItem(r);
                items.add(item);
            }
        }
        List<InspectionItem> start_items;
        if (items.size() > 5) {
            start_items = items.subList(0, 5);//截取前五条数据
        } else {
            start_items = items;
        }
        if (index != 0) {
            result.put(Key.ITEMS, start_items);
        } else {
            result.put(Key.ITEMS, items);
        }
        return result;
    }
}

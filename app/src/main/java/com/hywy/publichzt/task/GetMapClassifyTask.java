package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.MapClassify;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 一张图类别接口
 *
 * @author Superman
 */

public class GetMapClassifyTask extends BaseRequestTask {

    public GetMapClassifyTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_ROLESYSTEM_APPMAPZTREEQX);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<MapClassify>>() {
        }.getType();
        List<MapClassify> list = new Gson().fromJson(json, type);
//        if (StringUtils.isNotNullList(list)) {
//            List<MapClassifyItem> items = new ArrayList<>();
//            int size = list.size();
//            for (int i = 0; i < size; i++) {
//                MapClassify r = list.get(i);
//                MapClassifyItem item = new MapClassifyItem(r);
//                items.add(item);
//            }
//            result.put(Key.ITEMS, items);
//        }
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

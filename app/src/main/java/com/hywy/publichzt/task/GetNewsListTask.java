package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.NewsItem;
import com.hywy.publichzt.adapter.item.NotifyItem;
import com.hywy.publichzt.entity.News;
import com.hywy.publichzt.entity.Notify;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表
 *
 * @author Superman
 */

public class GetNewsListTask extends BaseRequestTask {

    public GetNewsListTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_IMG_LIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<News>>() {
        }.getType();
        List<News> list = new Gson().fromJson(json, type);
        List<NewsItem> items = new ArrayList<>();
        for (News news : list) {
            NewsItem item = new NewsItem(news);
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

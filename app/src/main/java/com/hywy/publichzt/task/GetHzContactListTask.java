package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.HzContactItem;
import com.hywy.publichzt.adapter.item.MapClassifyHeaderItem;
import com.hywy.publichzt.entity.HzContact;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 河长通讯录列表task
 *
 * @author Superman
 */

public class GetHzContactListTask extends BaseRequestTask {


    public GetHzContactListTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_TXL_HZLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<HzContact>>() {
        }.getType();
        List<HzContact> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<HzContactItem> items = new ArrayList<>();
            for (HzContact contact : list) {
                List<HzContact.DealsBean> dealsBeans = contact.getDeals();
                if (StringUtils.isNotNullList(dealsBeans)) {
                    for (int i = 0; i < dealsBeans.size(); i++) {
                        HzContactItem item = null;
                        HzContact.DealsBean cdb = dealsBeans.get(i);
                        if (i == 0) {
                            item = new HzContactItem(cdb, new MapClassifyHeaderItem(contact.getADNM()));
                        } else {
                            item = new HzContactItem(cdb);
                        }
                        items.add(item);
                    }
                }
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

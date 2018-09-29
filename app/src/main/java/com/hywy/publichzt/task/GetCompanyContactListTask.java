package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.CompanyContactItem;
import com.hywy.publichzt.adapter.item.MapClassifyHeaderItem;
import com.hywy.publichzt.entity.CompanyContact;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 协作单位通讯录列表task
 *
 * @author Superman
 */

public class GetCompanyContactListTask extends BaseRequestTask {


    public GetCompanyContactListTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_TXL_LISTMAIN);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<CompanyContact>>() {
        }.getType();
        List<CompanyContact> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<CompanyContactItem> items = new ArrayList<>();
            for (CompanyContact contact : list) {
                List<CompanyContact.DealsBean> dealsBeans = contact.getDeals();
                if (StringUtils.isNotNullList(dealsBeans)) {
                    for (int i = 0; i < dealsBeans.size(); i++) {
                        CompanyContactItem item = null;
                        CompanyContact.DealsBean cdb = dealsBeans.get(i);
                        if (i == 0) {
                            item = new CompanyContactItem(cdb, new MapClassifyHeaderItem(contact.getDEPT_NAME()));
                        } else {
                            item = new CompanyContactItem(cdb);
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

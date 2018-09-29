package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.ContactItem;
import com.hywy.publichzt.adapter.item.MapClassifyHeaderItem;
import com.hywy.publichzt.entity.Contact;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 河长办通讯录列表task
 *
 * @author Superman
 */

public class GetContactListTask extends BaseRequestTask {


    public GetContactListTask(Context context) {
        super(context);
    }

    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_TXL_LIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Contact>>() {
        }.getType();
        List<Contact> list = new Gson().fromJson(json, type);
        if (StringUtils.isNotNullList(list)) {
            List<ContactItem> items = new ArrayList<>();
            for (Contact contact : list) {
                List<Contact.DealsBean> dealsBeans = contact.getDeals();
                if (StringUtils.isNotNullList(dealsBeans)) {
                    for (int i = 0; i < dealsBeans.size(); i++) {
                        ContactItem item = null;
                        Contact.DealsBean cdb = dealsBeans.get(i);
                        if (i == 0) {
                            item = new ContactItem(cdb, new MapClassifyHeaderItem(contact.getROLE_NAME()));
                        } else {
                            item = new ContactItem(cdb);
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

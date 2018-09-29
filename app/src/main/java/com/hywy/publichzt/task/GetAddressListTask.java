package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.Adnm;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 行政区域列表
 *
 * @author Superman
 */

public class GetAddressListTask extends BaseRequestTask {

    public GetAddressListTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_ERMISSIONS_GETQXLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<Adnm>>() {
        }.getType();
        List<Adnm> list = new Gson().fromJson(json, type);

        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

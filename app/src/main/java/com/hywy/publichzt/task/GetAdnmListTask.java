package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.Adnm;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 下级区县列表
 *
 * @author Superman
 */

public class GetAdnmListTask extends BaseRequestTask {
    private int type;

    public GetAdnmListTask(Context context) {
        super(context);
    }

    public GetAdnmListTask(Context context, int type) {
        super(context);
        this.type = type;//0:区县市区，1：下级行政区划
    }


    @Override
    public String url() {
        if(type==0){
            return HttpUrl.getUrl(HttpUrl.RMS_APP_ADCD_FINDQXADCD);
        }else {
            return HttpUrl.getUrl(HttpUrl.RMS_APP_ADCD_FINDADCDBYUPADCD );
        }
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

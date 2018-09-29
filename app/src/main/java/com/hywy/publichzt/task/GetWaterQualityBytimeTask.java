package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.WaterQualityChart;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.List;


/**
 * 水质日月时查询
 *
 * @author Superman
 */
public class GetWaterQualityBytimeTask extends BaseRequestTask {


    public GetWaterQualityBytimeTask(Context context) {
        super(context);
    }

    @Override

    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_AWQMD_FXLIST);
    }


    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<WaterQualityChart>>() {
        }.getType();
        result.put(Key.CHART,"WaterQualityChart");
        List<WaterQualityChart> list = new Gson().fromJson(json, type);
        return list;
    }
}

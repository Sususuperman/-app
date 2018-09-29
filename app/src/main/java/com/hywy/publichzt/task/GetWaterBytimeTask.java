package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.WaterRainChart;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.List;


/**
 * 水雨情日月时查询
 *
 * @author Superman
 */
public class GetWaterBytimeTask extends BaseRequestTask {
    private int index;

    public GetWaterBytimeTask(Context context, int index) {
        super(context);
        this.index = index;
    }

    public GetWaterBytimeTask(Context context) {
        super(context);
    }

    @Override

    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PPTN_R_FXLIST);
    }


    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<WaterRainChart>>() {
        }.getType();
        result.put(Key.CHART, "WaterRainChart");
        List<WaterRainChart> list = new Gson().fromJson(json, type);
        return list;
    }
}

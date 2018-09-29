package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.RiverCourseChart;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.List;


/**
 * 河道水文日月时查询
 *
 * @author Superman
 */
public class GetRiverCourseBytimeTask extends BaseRequestTask {


    public GetRiverCourseBytimeTask(Context context) {
        super(context);
    }

    @Override

    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_STRIVER_FXLIST);
    }


    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<RiverCourseChart>>() {
        }.getType();
        result.put(Key.CHART,"RiverCourseChart");
        List<RiverCourseChart> list = new Gson().fromJson(json, type);
        return list;
    }
}

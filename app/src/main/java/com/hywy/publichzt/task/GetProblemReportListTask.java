package com.hywy.publichzt.task;

import android.content.Context;

import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.adapter.item.ProblemReportItem;
import com.hywy.publichzt.dao.ProblemDao;
import com.hywy.publichzt.entity.ProblemReport;
import com.hywy.publichzt.key.Key;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取问题上报列表
 *
 * @author Superman
 */

public class GetProblemReportListTask extends BaseRequestTask {

    public GetProblemReportListTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_PATROL_EVENT_USERLIST);
    }

    @Override
    public Object request(String json) throws Exception {
        Type type = new TypeToken<List<ProblemReport>>() {
        }.getType();
        List<ProblemReport> list = new Gson().fromJson(json, type);
        //查询数据库追加list
        ProblemDao dao = new ProblemDao(context);
        if (StringUtils.isNotNullList(dao.select())) {
            if (StringUtils.isNotNullList(list)) {
                list.addAll(0, dao.select());
            } else {
                list = dao.select();
            }
        }
        /**********************************/;
        List<ProblemReportItem> items = new ArrayList<>();
        for (ProblemReport problemReport : list) {
            ProblemReportItem item = new ProblemReportItem(problemReport);
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

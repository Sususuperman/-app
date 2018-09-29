package com.hywy.publichzt.dao;

import android.content.Context;

import com.cs.common.basedao.BaseDao;
import com.cs.common.utils.SharedUtils;
import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.ProblemReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Superman on 2018/8/13.
 */

public class ProblemDao extends BaseDao {
    String tabName = "problemDb";
    String prblemName = "problem";
    App app;
    String userId = "";

    public ProblemDao(Context context) {
        super(context);
        app = App.getInstance();
        userId = app.getAccount().getUserId();
    }

    /**
     * 存储
     *
     * @param list
     */
    public void replace(List<ProblemReport> list) {
        SharedUtils.setString(App.getInstance().getApplicationContext(), tabName, userId + prblemName, new Gson().toJson(list));
    }

    /**
     * 插入
     *
     * @param p
     */
    public void insert(ProblemReport p) {
        List<ProblemReport> list = select();
        if (StringUtils.isNotNullList(list)) {
            list.add(p);
        } else {
            list = new ArrayList<>();
            list.add(p);
        }
        replace(list);
    }


    /**
     * 查询问题上报本地数据,使用userid+name的形式当key
     */
    public List<ProblemReport> select() {
        String name = SharedUtils.getString(App.getInstance().getApplicationContext(), tabName, userId + prblemName, "");
        return new Gson().fromJson(name, new TypeToken<List<ProblemReport>>() {
        }.getType());
    }

    /**
     * 存储问题上报本地数据,使用userid+name的形式当key
     */
    public void update(ProblemReport report) {
        List<ProblemReport> list = select();
        if (StringUtils.isNotNullList(list)) {
            for (int i = 0; i < list.size(); i++) {
                ProblemReport p = list.get(i);
                if (p.getEVENT_ID() == report.getEVENT_ID()) {
                    list.set(i, report);
                }
            }
        }
        replace(list);
    }


    /**
     * 存储问题上报本地数据,使用userid+name的形式当key
     */
    public void delete(ProblemReport report) {
        List<ProblemReport> list = select();
        if (StringUtils.isNotNullList(list)) {
            for (int i = 0; i < list.size(); i++) {
                ProblemReport p = list.get(i);
                if (p.getEVENT_ID() == report.getEVENT_ID()) {
                    list.remove(i);
                }
            }
        }
        replace(list);
    }

}

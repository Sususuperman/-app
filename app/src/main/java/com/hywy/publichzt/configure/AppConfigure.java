package com.hywy.publichzt.configure;

import android.content.Context;
import android.content.Intent;

import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hywy.publichzt.activity.EventListActivity;
import com.hywy.publichzt.activity.EventSuperviseActivity;
import com.hywy.publichzt.activity.MenuManageActivity;
import com.hywy.publichzt.activity.NotifyActivity;
import com.hywy.publichzt.activity.OrganizeInfoActivity;
import com.hywy.publichzt.activity.ProblemReportActivity;
import com.hywy.publichzt.activity.RiverMonitorActivity;
import com.hywy.publichzt.activity.YuJingListActivity;
import com.hywy.publichzt.entity.AppMenu;
import com.hywy.publichzt.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Superman
 */

public class AppConfigure {

    /**
     * 从接口拿到menu信息与本地菜单配置文件进行对比，更新最新的菜单。
     * 本地为最全的菜单列表，从中筛选权限菜单，menujson为json字符串
     *
     * @param menujson
     * @param menus
     * @return
     */
    public static List<MenuEntity> saveMenuEntities(List<AppMenu> menus, String menujson) {
        List<MenuEntity> allMenulist = new ArrayList<>();
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(menujson).getAsJsonArray();
        Gson gson = new Gson();
        if (StringUtils.isNotNullList(menus)) {
            for (AppMenu menu : menus) {
                //加强for循环遍历JsonArray
                for (JsonElement indexArr : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    MenuEntity menuEntity = gson.fromJson(indexArr, MenuEntity.class);
                    if (menu.getMENU_KEY().equals(menuEntity.getId())) {
                        allMenulist.add(menuEntity);
                    }
                }
            }
        }
        return allMenulist;
    }

    public static void startAction(Context context, MenuEntity menuEntity) {
        switch (menuEntity.getId()) {
            case "shiwuduban"://事件督办
                EventSuperviseActivity.startAction(context);
                break;
            case "yidongxuncha"://移动巡查
                break;
            case "yujingfabu"://预警发布
                YuJingListActivity.startAction(context);
                break;
            case "shipinjiankong"://视频监控
                break;
            case "tongzhigonggao"://通知公告
                NotifyActivity.startAction(context);
                break;
            case "hehujiance"://河湖监测
                RiverMonitorActivity.startAction(context);
                break;
            case "zuzhijigou"://组织信息
                OrganizeInfoActivity.startAction(context);
                break;
            case "shijianchuli"://事件处理
                EventListActivity.startAction(context);
                break;
            case "wentishangbao"://问题上报
                ProblemReportActivity.startAction(context);
                break;
            case "all"://更多
                Intent intent = new Intent();
                intent.setClass(context, MenuManageActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    public static void showToast() {
        IToast.toast("正在努力开发中！");
    }

    private static Intent createIntent(Context context, String title, Class activity) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("title", title);
        return intent;
    }
}

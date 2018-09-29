package com.hywy.publichzt.task;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.dao.AppMenuDao;
import com.hywy.publichzt.entity.AppMenu;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 权限菜单接口
 *
 * @author Superman
 */

public class GetAppMenuTask extends BaseRequestTask {

    public GetAppMenuTask(Context context) {
        super(context);
    }


    @Override
    public String url() {
        return HttpUrl.getUrl(HttpUrl.RMS_APP_MENU_APP_MENULIST);
    }

    @Override
    public Object request(String json) throws Exception {
        App app = App.getInstance();
        Type type = new TypeToken<List<AppMenu>>() {
        }.getType();
        List<AppMenu> list = new Gson().fromJson(json, type);
        AppMenuDao dao = new AppMenuDao(context);
        for (AppMenu am : list) {
            if (am.getMENU_KEY().equals("gongzuotai")) {
                if (app.getMenu1() == null) {
                    dao.replaceMenu1(am);
                } else{
                    if (am.getMENU_BS().equals("1")) {//有更新
                        dao.replaceMenu1(am);
                    }
                }
            }else if (am.getMENU_KEY().equals("yizhangtu")) {
                if (app.getMenu2() == null) {
                    dao.replaceMenu2(am);
                } else{
                    if (am.getMENU_BS().equals("1")) {//有更新
                        dao.replaceMenu2(am);
                    }
                }
            }else if (am.getMENU_KEY().equals("wode")) {
                if (app.getMenu3() == null) {
                    dao.replaceMenu3(am);
                } else{
                    if (am.getMENU_BS().equals("1")) {//有更新
                        dao.replaceMenu3(am);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public boolean isPost() {
        return false;
    }
}

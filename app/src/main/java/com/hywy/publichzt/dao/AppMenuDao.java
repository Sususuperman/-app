package com.hywy.publichzt.dao;

import android.content.Context;
import android.util.Log;

import com.cs.common.basedao.BaseDao;
import com.cs.common.utils.SharedUtils;
import com.google.gson.Gson;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.AppMenu;


/**
 * 菜单权限的数据库接口
 *
 * @author james
 */
public class AppMenuDao extends BaseDao {
    String tabName = "appMenuDb";
    String appMenuName = "appmenu";

    public AppMenuDao(Context context) {
        super(context);
    }

    /**
     * 存储菜单权限数据
     *
     * @param menu
     */
    public void replaceMenu1(AppMenu menu) {
        SharedUtils.setString(App.getInstance().getApplicationContext(), tabName, appMenuName + "1", new Gson().toJson(menu) == null ? "" : new Gson().toJson(menu));
    }

    /**
     * 存储菜单权限数据
     */
    public AppMenu selectMenu1() {
        String menu = SharedUtils.getString(App.getInstance().getApplicationContext(), tabName, appMenuName + "1", "");
        return new Gson().fromJson(menu, AppMenu.class);
    }


    /**
     * 菜单2
     *
     * @param menu
     */
    public void replaceMenu2(AppMenu menu) {
        SharedUtils.setString(App.getInstance().getApplicationContext(), tabName, appMenuName + "2", new Gson().toJson(menu) == null ? "" : new Gson().toJson(menu));
    }

    /**
     * 菜单2
     */
    public AppMenu selectMenu2() {
        String menu = SharedUtils.getString(App.getInstance().getApplicationContext(), tabName, appMenuName + "2", "");
        Log.i("menuuuu", menu);
        return new Gson().fromJson(menu, AppMenu.class);
    }

    /**
     * 菜单3
     *
     * @param menu
     */
    public void replaceMenu3(AppMenu menu) {
        SharedUtils.setString(App.getInstance().getApplicationContext(), tabName, appMenuName + "3", new Gson().toJson(menu) == null ? "" : new Gson().toJson(menu));
    }

    /**
     * 菜单3
     */
    public AppMenu selectMenu3() {
        String menu = SharedUtils.getString(App.getInstance().getApplicationContext(), tabName, appMenuName + "3", "");
        return new Gson().fromJson(menu, AppMenu.class);
    }


    /**
     * 删除菜单的信息
     */
    public boolean delete() {
        SharedUtils.clear(App.getInstance().getApplicationContext(), tabName);
//        AppMenu menu = App.getInstance().getMenu2();
//        Log.i("menu",menu.getMENU_NAME()+"");
        return true;
    }

}

package com.hywy.publichzt.entity;

import java.util.List;

/**
 * @author Superman
 * @date 2018/7/24
 */

public class AppMenu {

    /**
     * MENU_BS : 0
     * subMenu : []
     * MENU_ID : 26
     * MENU_NAME : 一张图
     * PARENT_ID : 0
     */

    private String MENU_BS;//0,无更新，1，有更新
    private String MENU_ID;
    private String MENU_NAME;
    private String PARENT_ID;
    private List<AppMenu> subMenu;
    private String MENU_KEY;

    public String getMENU_BS() {
        return MENU_BS;
    }

    public void setMENU_BS(String MENU_BS) {
        this.MENU_BS = MENU_BS;
    }

    public String getMENU_ID() {
        return MENU_ID;
    }

    public void setMENU_ID(String MENU_ID) {
        this.MENU_ID = MENU_ID;
    }

    public String getMENU_NAME() {
        return MENU_NAME;
    }

    public void setMENU_NAME(String MENU_NAME) {
        this.MENU_NAME = MENU_NAME;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public List<AppMenu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<AppMenu> subMenu) {
        this.subMenu = subMenu;
    }

    public String getMENU_KEY() {
        return MENU_KEY;
    }

    public void setMENU_KEY(String MENU_KEY) {
        this.MENU_KEY = MENU_KEY;
    }
}

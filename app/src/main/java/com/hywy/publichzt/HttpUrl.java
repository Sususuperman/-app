package com.hywy.publichzt;

import com.hywy.publichzt.app.App;

/**
 * @author Superman
 */

public class HttpUrl {

    /**
     * 六安河长环境
     */
    public static String getUrl(String url) {
        return App.getInstance().getApiURL() + url;
    }

    /**
     * 一张图arcgis加载图层URL
     */
    public static String getLayerUrl(String type) {
        return "http://182.50.122.179:8399/arcgis/rest/services/liuandjyxt/MapServer/" + type;
    }

    /***************登录接口************/
    public static final String RMS_LOGIN_LOGIN = "/RMS/login_login";
    /***************首页菜单界面************/
    /**
     * 河道列表
     */
    public static final String RMS_APP_RIVERWAY_RIVERWAYLISTTREE = "/RMS/app_riverway/riverwaytree";

    /**
     * 投诉列表
     */
    public static final String RMS_APP_COMPLAIN_LIST = "/RMS/app-complain/list";
    /**
     * 投诉类型列表
     */
    public static final String RMS_APP_COMPLAIN_GETTYPE = "/RMS/app-complain/GetType";

    /**
     * 增加投诉建议
     */
    public static final String RMS_APP_COMPLAIN_APPSAVE = "/RMS/app-complain/appSave";
    /**
     * 新闻列表
     */
    public static final String RMS_APP_IMG_LIST = "/RMS/app_img/list";


    /**
     * 查询市和区县行政区划
     */
    public static final String RMS_APP_ADCD_FINDQXADCD = "/RMS/app_adcd/findqxADCD";

    /**
     * 查询下级联动行政
     */
    public static final String RMS_APP_ADCD_FINDADCDBYUPADCD = "/RMS/app_adcd/findADCDByUPADCD";
    /**
     * 根据行政区划查询河段
     */
    public static final String RMS_APP_REACH_FINDLISTBYADCD = "/RMS/app_reach/findListByADCD";

    /*********************************************************************/

    public static final String RMS_APP_MENU_APP_MENULIST = "/RMS/app-menu/app_menuList";
    /**
     * 获取巡查日志
     */
    public static final String RMS_APP_PATROL_LOG_LIST = "/RMS/app-patrol_log/applist";

    /**
     * 水雨情监测
     */
    public static final String RMS_APP_PPTN_R_FINDSTPPTNRS = "/RMS/app_pptn_r/appPptnlist";

    /**
     * 水库水位监测
     */
    public static final String RMS_APP_RSVR_R_RSLIST = "/RMS/app_rsvr_r/rslist";


    /**
     * 水质监测
     */
    public static final String RMS_APP_AWQMD_WQLIST = "/RMS/app-awqmd/wqlist";

    /**
     * 河道水文
     */
    public static final String RMS_APP_APP_STRIVER_STLIST = "/RMS/app-striver/stlist";

    /**
     * 水雨情日月时查询
     */
    public static final String RMS_APP_PPTN_R_FXLIST = "/RMS/app_pptn_r/fxlist";

    /**
     * 河道水文日月时查询
     */
    public static final String RMS_APP_STRIVER_FXLIST = "/RMS/app-striver/fxlist";

    /**
     * 水库水文日月时查询
     */
    public static final String RMS_APP_RSVR_R_FXLIST = "/RMS/app_rsvr_r/fxlist";
    /**
     * 水质日月时查询
     */
    public static final String RMS_APP_AWQMD_FXLIST = "/RMS/app-awqmd/fxlist";


    /**
     * 一张图菜单接口
     */
    public static final String RMS_APP_ROLESYSTEM_APPMAPZTREEQX = "/RMS/app_roleSystem/appmapztreeqx";
    /**
     * 事物督办列表
     */
    public static final String RMS_APP_EVENT_CREATE_APPLIST = "/RMS/app-event_create/applist";

    /**
     * 创建事件
     */
    public static final String RMS_APP_EVENT_PROCESS_APPDONEXT = "/RMS/app-event_process/AppdoNext";


    /**
     * 河道详情
     */
    public static final String RMS_APP_RIVERWAY_RIVERWAYLIST = "/RMS/app_riverway/riverwaylist";
    /**
     * 移动巡查·选择巡查计划
     */
    public static final String RMS_APP_PATROL_PLAN_GETPLANLIST = "/RMS/app-patrol_plan/getPlanList";

    /**
     * 移动巡查·巡查路线上报
     */
    public static final String RMS_APP_PATROL_LINE_APPSAVE = "/RMS/app-patrol_line/appSave";
    /**
     * 移动巡查.新建巡查日志
     */
    public static final String RMS_APP_TROL_LOG_APPSAVE = "/RMS/app-patrol_log/appSave";


    /**
     * 查询所有河段名称
     */
    public static final String RMS_APP_PATROL_PLAN_FINDREACHBASE = "/RMS/app-patrol_plan/findReachBase";
    /**
     * 查询市和区县行政区划
     */
    public static final String RMS_APP_ERMISSIONS_GETQXLIST = "/RMS/app-permissions/getQxList";

    /**
     * 事件类型
     */
    public static final String RMS_APP_EVENTANALY_FINDEVENTTYPE = "/RMS/app-eventAnaly/findEVENTTYPE";

    /**
     * 移动巡查~问题上报
     */
    public static final String RMS_APP_APP_PATROL_EVENT_APPSAVE = "/RMS/app-patrol_event/appSave";

    /**
     * 通知公告
     */
    public static final String RMS_APP_TZGG_FINDTZGGS = "/RMS/app_tzgg/findTzggs";

    /**
     * 预警发布
     */
    public static final String RMS_APP_WARN_R_APPSAVE = "/RMS/app_warn_r/appSave";


    /**
     * 预警列表
     */
    public static final String RMS_APP_WARN_R_APPFINDALLLIST = "/RMS/app_warn_r/AppfindAllList";

    /**
     * 我的上报列表
     */
    public static final String RMS_APP_PATROL_EVENT_USERLIST = "/RMS/app-patrol_event/Userlist";


    /**
     * 获取其相关用户信息(河长办)
     */
    public static final String RMS_APP_TXL_LIST = "/RMS/app-Txl/list";
    /**
     * 获取其相关用户信息(河长)
     */
    public static final String RMS_APP_TXL_HZLIST = "/RMS/app-Txl/hzlist";
    /**
     * 获取其相关用户信息(协作单位)
     */
    public static final String RMS_APP_TXL_LISTMAIN = "/RMS/app-Txl/listMain";
    /**
     * 获取留言簿列表
     */
    public static final String RMS_APP_TXL_LISTLY = "/RMS/app-Txl/listLy";
    /**
     * 获取留言簿详情列表
     */
    public static final String RMS_APP_TXL_LISTLYXX = "/RMS/app-Txl/listLyxx";
    /**
     * 留言提交
     */
    public static final String RMS_APP_TXL_APPSAVE = "/RMS/app-Txl/appSave";

    /**
     * 视频点查询
     */
    public static final String RMS_APP_VIDEO_VIDEOLIST = "/RMS/app-video/videolist";

    /**
     * 版本更新接口
     */
    public static final String RMS_APP_UPDATE_APPLIST = "/RMS/app-update/Applist";
    /**
     * 移动巡查选择河段列表接口
     */
    public static final String RMS_APP_ATROL_PLAN_FINDREACHBASE = "/RMS/app-patrol_plan/findReachBase";
    /**
     * 下级区县列表接口
     */
    public static final String RMS_APP_APP_ADCD_FINDADCDBYUSER = "/RMS/app_adcd/findADCDByUser";

    /**
     * 查询所有用户
     */
    public static final String RMS_APP_PERMISSIONS_GETPERLIST = "/RMS/app-permissions/getPerList";
    /**
     * 结束事物
     */
    public static final String RMS_APP_EVENT_PROCESS_DOEND = "/RMS/app-event_process/doEnd";

    /**
     * 一张图~查询测站信息
     */
    public static final String RMS_APP_MAP_LISTSTION = "/RMS/app-Map/listStion";

    /**
     * 事件处理~催办
     */
    public static final String RMS_APP_EVENT_PROCESS_SENDMSG = "/RMS/app-event_process/sendMsg";

    /**
     * 我的~意见反馈
     */
    public static final String RMS_APP_QUESTION_APPSAVE = "/RMS/app-question/appSave";

    /**
     * 我的~个人头像更换
     */
    public static final String RMS_APP_USER_APPUPLOADFILE = "/RMS/app_user/appUploadFile";

    /*********************************************************************************************************/
    public static String getServiceUrl(String url) {
        return App.getInstance().getApiURL() + url;
    }

//    /**
//     * 正式版本
//     */
//    public static String getUrl(String url) {
//        return App.getInstance().getInsureApiURL() + url;
//    }
//
//    public static String getServiceUrl(String url) {
//        return App.getInstance().getServiceApiURL() + url;
//    }

    /**
     * 更新地址
     *
     * @return
     */
    public static String getUpgradeUrl() {
        return HttpUrl.getUrl(RMS_APP_UPDATE_APPLIST);
    }


    /**
     * 文件下载地址
     *
     * @param url
     * @return
     */
    public static String getFileDownloadUrl(String url) {
//        if(Logger.DEBUG_STATE){
//            return getUrl(url);
//        }else {
//            return App.getInstence().getTablesURL() + url;
//        }
        return url;
    }


}

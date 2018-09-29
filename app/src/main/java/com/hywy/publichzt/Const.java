package com.hywy.publichzt;

import android.os.Environment;

/**
 * @author Superman
 */
public abstract class Const {

    public static final String SUCCESS="success";
    public static final String ERROR="err";

    public final static String Exception_Url = "/report/exception";//收集异常接口

    public static final String ANDROID_SOURCE = "4ac01f4e77988ba07c2ce9f113148a27";

    public static final int ApiSuccess = 0;// 发送成功
    public static final int ApiTokenError = 10103;// token错误
    public static final int ApiPwdError = 20011;// 帐号密码错误
    public static final int ApiNoNet = -1;// 无网络
    public static final int ApiNoData = 20000;//没有数据

    public static final int PAGESIZE = 20;// 本地分页查询时，每页条数


    public static final int PAGE = 1; //页数

    //项目的根路径
    public static final String PATH = "/sefeadmin/";
    // log日志存放的路径
    public static final String LOGPATH="/sefeadmin/log/";
    public static final String PLUGIN_APK_PATH = "/hywy/apk";
    // 报告文件存放路径
    public static final String SYSTEM_MSG_PATH = "/sefeadmin/sysmessage/";
    // 报告类型文件存放路径
    public static final String TAG_PATH = "/sefeadmin/tag/";
    public static final String FORMS_PATH = "/sefeadmin/forms/";
    public static final String EMERGENCY_PATH = "/sefeadmin/emergency/";
    //用户头像保存路径
    public static final String USER_PATH = "/sefeadmin/user/";
    // 本地图片存放路径
    public static final String IMGAGE_PATH = "images/";
    // 本地音频存放路径
    public static final String AUDIO_PATH = "audio/";
    // 本地视频存放路径
    public static final String VIDEO_PATH = "video/";
    // 本地文件存放路径
    public static final String OTHER_PATH = "other/";
    //文件加密临时文件路径
    public static final String TEMP_PATH = "temp/";
    public static final  String MARKER_PATH="marker/";

    public final static int DisabledUser = 2;//用户状态为禁用
    public final static int AbledUser = 1;//用户状态为可用
    public final static int category_images = 4;//多附件

    public final static int category_image = 1;// 图片
    public final static int category_video = 2;// 视频
    public final static int category_audio = 3;// 音频
    public static final int category_office = 4;//文档
    public final static String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();

    public final static String ENVIRONMENT = "ENVIRONMENT";//环境


    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }


}

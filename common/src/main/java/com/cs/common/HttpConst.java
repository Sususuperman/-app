package com.cs.common;

public abstract class HttpConst {
	public final static String DATEBASE_NAME = "safeadmin.db";//数据库名称

	public static final String ANDROID_SOURCE = "4ac01f4e77988ba07c2ce9f113148a27";

	public static final String Success = "success";// 发送成功

	public static final int ApiSuccess = 0;// 发送成功
	public static final int ApiTokenError = 10103;// token错误
	public static final int ApiPwdError = 20011;// 帐号密码错误
	public static final int ApiNoNet = -1;// 无网络
	public static final int ApiNoData = 20000;//没有数据
	public static final int APINODATA_REGISTER = 20001;//没有数据


	public static final int PAGESIZE = 20;// 本地分页查询时，每页条数
	public static final int PAGE = 1; //页数

	//项目的根路径
	public static final String PATH = "/comment/";

	// 本地音频存放路径
	public static final String AUDIO_PATH = "audio/";

}

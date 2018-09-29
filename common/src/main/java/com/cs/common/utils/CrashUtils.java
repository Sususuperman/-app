package com.cs.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

public class CrashUtils implements UncaughtExceptionHandler
{
    public static final String TAG = "CrashUtils";

    private static CrashUtils instance;
    private UncaughtExceptionHandler defaultExceptionHandler;
    
    private Context context;

    private CrashUtils()
    {
    }


    public static CrashUtils getInstance()
    {
        if (instance == null)
        {
        	instance = new CrashUtils();
        }
        return instance;
    }


    public void init(Context ctx)
    {
        context = ctx;
      	this.defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    // 当UncaughtException发生时会转入该函数来处理
    public void uncaughtException(Thread thread, Throwable ex)
    {
    	Log.e(TAG, "==========");
    	handleException(ex);

    	defaultExceptionHandler.uncaughtException(thread, ex); 
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     * 
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex)
    {
	    if (ex == null)
	    {
	    	return true;
	    }
	    
      String msg = "\n\n出现了错误：" + ex.getLocalizedMessage();
      
      StackTraceElement[] ste = ex.getStackTrace();
      for (int i = 0; i < ste.length; i++)
      {
      	msg += "\n" + ste[i].toString();
      }
      
      // 收集设备信息
      msg += collectDeviceInfo(context);
      msg += "\n\n\n\n";
      // 保存错误报告文件
      Log_Location.logToSDcard("error.txt", 0, msg, context);

      return true;
    }


    /**
     * 收集程序崩溃的设备信息
     */
    private String collectDeviceInfo(Context ctx)
    {
    	String info = "\n\n当前系统信息：";
	    try
	    {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
            PackageManager.GET_ACTIVITIES);
        if (pi != null)
        {
        	info += "\n版本：" + pi.versionName;
        	info += "\n版本号：" + pi.versionCode;
        }
	    }
	    catch (NameNotFoundException e)
	    {
	        Log.e(TAG, "Error while collect package info", e);
	    }
	    
      // 使用反射来收集设备信息.在Build类中包含各种设备信息,
      Field[] fields = Build.class.getDeclaredFields();
      for (Field field : fields)
      {
        try
        {
	        field.setAccessible(true);
	        info += "\n" + field.getName() + "：" + field.get(null);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error while collect crash info", e);
        }
      }
      
      return info;

    }

}

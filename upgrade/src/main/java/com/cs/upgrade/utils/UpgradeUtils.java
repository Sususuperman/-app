package com.cs.upgrade.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;


import com.cs.common.utils.StringUtils;
import com.cs.upgrade.entity.Config;
import com.cs.upgrade.entity.Upgrade;

import java.io.File;

/**
 * 日期工具类
 *
 * @author james
 *
 */
public abstract class UpgradeUtils
{
    /**
     * 返回文件名，去掉路径
     *
     * @param path
     * @return
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(File.separator);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }


	/**
	 * 获得apk路径
	 *
	 * @param url
	 * @return
	 */
	public static String getApkPath(String url)
	{
//	    String sdpath = Environment.getExternalStorageDirectory().toString();//获取跟目录
//        String path = sdpath + Const.PLUGIN_APK_PATH;
		String dest = Config.downLoadPath + StringUtils.getFilename(url);
		return dest;
	}

	/**
	 * 检测sdcard下是否已经下载了最新的版本
	 * @param upgrade
	 * @param context
	 * @return
	 */
	public static boolean isCheckVersion(Upgrade upgrade, Context context)
	{
		PackageManager pm = context.getPackageManager();
		String versionName = null;
		int versionCode = -1;
		String sdPath = getApkPath(upgrade.getUrl());
		File file = new File(sdPath);
		if (file.exists())
		{
			try
			{
				PackageInfo pakinfo = pm.getPackageArchiveInfo(
						getApkPath(upgrade.getUrl()), 0);
				if (pakinfo != null)
				{
					versionName = pakinfo.versionName;
					versionCode = pakinfo.versionCode;
//					if (versionName.equals(upgrade.getVersion_name()))
//					{
						// 存在相同版本就去安装
						if (versionCode == upgrade.getVersion_code())
						{
							return true;
						}
						else
						{// 版本不同去删除
							file.delete();
						}
//					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

//	/**
//	 * 获得已安装应用程序的版本号
//	 *
//	 * @param packgeName
//	 * @return
//	 */
//	public static int getVersion(String packgeName, Context context)
//	{
//		PackageManager packageManager = context.getPackageManager();
//		PackageInfo applicationInfo;
//		int versionCode = 0;
//		try
//		{
//			applicationInfo = packageManager.getPackageInfo(packgeName, 0);
//			versionCode = applicationInfo.versionCode;
//		} catch (NameNotFoundException e)
//		{
//			versionCode = -1;
//			e.printStackTrace();
//		}
//
//		return versionCode;
//	}
//
//	/**
//	 * 判断程序是否安装成功
//	 *
//	 * @param context
//	 * @return
//	 */
//	public static boolean isInStall(Upgrade upgrade, Context context)
//	{
//		PackageManager packageManager = context.getPackageManager();
//		PackageInfo applicationInfo;
//		int versionCode = 0;
//		try
//		{
//			applicationInfo = packageManager
//					.getPackageInfo(context.getPackageName(), 0);
//			versionCode = applicationInfo.versionCode;
//			if (versionCode == upgrade.getVersion_code())
//			{
//				return true;
//			}
//		} catch (NameNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//
//		return false;
//	}
//
//	/**
//	 * 删除sdcard下除最新版本的apk
//	 *
//	 * @param packgeName
//	 * @param context
//	 */
//	public static void deleteotherApk(String packgeName, int version,
//			Context context)
//	{
//		PackageManager packageManager = context.getPackageManager();
//		PackageInfo applicationInfo;
//		String sdpath = Environment.getExternalStorageDirectory().toString();//获取跟目录
//        String path = sdpath + Const.PLUGIN_APK_PATH;
//		File f = new File(path);
//		if (f.isDirectory())
//		{
//			File[] files = f.listFiles();
//			for (int i = 0; i < files.length; i++)
//			{
//				String apkPath = files[i].getAbsolutePath();
//				if (apkPath.endsWith(".apk"))
//				{
//					int versionCode = 0;
//					String verName = "";
//					try
//					{
//						applicationInfo = packageManager.getPackageArchiveInfo(apkPath, 0);
//						versionCode = applicationInfo.versionCode;
//						verName = applicationInfo.packageName;
//						if (verName.equals(packgeName))
//						{
//							if (version > versionCode)
//							{
//								File file = new File(apkPath);
//								if (file.exists()) file.delete();
//							}
//						}
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//						files[i].delete();
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 删除sdcard下除最新版本的apk
//	 *
//	 * @param context
//	 */
//	public static boolean isHaveApk( Context context)
//	{
//		PackageManager packageManager = context.getPackageManager();
//		PackageInfo applicationInfo;
//		String sdpath = Environment.getExternalStorageDirectory().toString();//获取跟目录
//		String path = sdpath + Const.PLUGIN_APK_PATH;
//		File f = new File(path);
//		if (f.isDirectory())
//		{
//			File[] files = f.listFiles();
//			for (int i = 0; i < files.length; i++)
//			{
//				String apkPath = files[i].getAbsolutePath();
//				if (apkPath.endsWith(".apk"))
//				{
//					try
//					{
//						applicationInfo = packageManager.getPackageArchiveInfo(apkPath, 0);
//						int versionCode = applicationInfo.versionCode;
//						String verName = applicationInfo.packageName;
//						if (verName.equals(context.getPackageName()))
//						{
//							return true;
//						}
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//						files[i].delete();
//					}
//				}
//			}
//		}
//		return false;
//	}
//	  /**
//     * 判断是否安装过
//     * @param context
//     * @param packageName
//     * @return
//     */
//	public static boolean isAppInstalled(Context context, String packageName,
//			int versionCode)
//	{
//		PackageManager pm = context.getPackageManager();
//
//		try
//		{
//			PackageInfo info = pm.getPackageInfo(packageName,
//					PackageManager.GET_ACTIVITIES);
//			return info.versionCode >= versionCode;
//
//		} catch (NameNotFoundException e)
//		{
//
//		}
//		return false;
//	}

}

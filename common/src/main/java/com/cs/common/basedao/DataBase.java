package com.cs.common.basedao;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class DataBase extends SQLiteManager {
    public DataBase(Context context) {
        super(context, getDaoName(context)+"_db", getVersion(context));
    }

    //通过android的机制获得版本，这样只需要改配置即可
    private static int getVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }

    private static String getDaoName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String packagename = info.packageName;
            String name[] = packagename.split("\\.");
            return name[name.length -1];
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void deleteDatabase() {
        context.deleteDatabase(getDaoName(context)+"_db");
    }
}

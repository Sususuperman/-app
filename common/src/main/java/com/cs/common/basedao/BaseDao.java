package com.cs.common.basedao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cs.common.baseapp.BaseApp;


/**
 * 所有dao的基类。
 * 两个get方法获得的实际上是App中的DataBase变量的响应方法的结果。
 * 就是说，这个系统实际上只有一个DataBase变量，这样能保证对数据库的锁。
 *
 * @author james
 */
public class BaseDao {
    protected Context context;
    private DataBase dbHelper;

    public BaseDao(Context context) {
        this.context = context;
    }

    public BaseDao(DataBase dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * 获得写数据库接口，注意，使用后，千万不要调用close方法
     *
     * @return
     */
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = null;
        if (context != null) {
            BaseApp app = (BaseApp) context.getApplicationContext();
            db = app.getDataBase().getWritableDatabase();
        } else {
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    /**
     * 获得读数据库接口，注意，使用后，千万不要调用close方法
     *
     * @return
     */
    protected SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = null;
        if (context != null) {
            BaseApp app = (BaseApp) context.getApplicationContext();
            db = app.getDataBase().getReadableDatabase();
        } else {
            db = dbHelper.getReadableDatabase();
        }
        return db;
    }
}

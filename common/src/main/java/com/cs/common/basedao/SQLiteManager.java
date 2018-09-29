package com.cs.common.basedao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs.common.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库管理类，管理数据库的创建和升级 所有的sql都保存在assets下的db目录下，文件的名称为vXXX.sql，其中XXX是版本。
 * 尤其要注意的是assets下的文件的大小不能超过1M。 文件的编码为UTF-8 squall语句可以分行，结束字符为“;”
 *
 * @author james
 */
public abstract class SQLiteManager extends SQLiteOpenHelper {
    protected Context context;
    private int version;

    public SQLiteManager(Context context, String name, int version) {
        super(context, name, null, version);
        this.version = version;
        this.context = context;
    }

    // 创建数据库，第一次安装时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 1; i <= version; i++) {
            this.execSqlFile(db, "db/v" + i + ".sql");
        }
    }

    // 数据库升级，此功能在程序升级时，如果数据库版本变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion + 1; i <= newVersion; i++) {
            this.execSqlFile(db, "db/v" + i + ".sql");
        }
    }

    private void execSqlFile(SQLiteDatabase db, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, "UTF-8");
            // 用";"分割字符串，然后将前面的空白字符清除，如果不是（空白或者注释「--」则执行）
            String[] sqls = StringUtils.delimitedListToStringArray(text, ";");
            for (String sql : sqls) {
                sql = StringUtils.trimLeadingWhitespace(sql);
                // 空白或者是注释
                if (sql.equals("") || sql.startsWith("--")) {
                    continue;
                }
                db.execSQL(sql);
            }
        } catch (IOException e) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void execSqlFile(SQLiteDatabase db, InputStream fileStream) {
        try {
            int size = fileStream.available();
            byte[] buffer = new byte[size];
            fileStream.read(buffer);
            fileStream.close();

            String text = new String(buffer, "UTF-8");
            // 用";"分割字符串，然后将前面的空白字符清除，如果不是（空白或者注释「--」则执行）
            String[] sqls = StringUtils.delimitedListToStringArray(text, ";");
            for (String sql : sqls) {
                sql = StringUtils.trimLeadingWhitespace(sql);

                if (sql.equals("") || sql.startsWith("--"))// 空白或者是注释
                {
                    continue;
                }
                db.execSQL(sql);

//                Logger.e("createDb", sql);
            }
        } catch (IOException e) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

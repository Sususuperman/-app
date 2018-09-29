package com.cs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class SharedUtils {


    /**
     * 首页红点提示
     */
    public static final String INSURANCE_APPLY_SHOW = "insurance_apply_SHOW";
    public static final String APPFUNCTION = "APPFUNCTION";// 首界面功能
    /**
     * 通讯录请求次数
     */
    public static final String REQUEST_COUNT = "requestcount";

    public static final String STRING = "STRING";//字符串
    public static final String BOOLEAN = "BOOLEAN";//boolean类型
    public static final String DOUBLE = "DOUBLE";//double类型
    public static final String INTEGER = "INTEGER";//int类型
    public static final String MOLD = "MOLD";
    public static final String OBJECT = "OBJECT";//float类型

    /**
     * 首页红点提示
     */
    public static final String TASK_SHOW ="task_show";
    public static final String TASKCOMPANY_SHOW ="taskcompany_show";
    public static final String ACCIDENT_SHOW ="accident_show";

    /**
     * 获取String类型的数据
     *
     * @param context
     * @param key
     * @param _default 默认值
     * @return
     */
    public static String getString(Context context, String key, String _default) {
        return getString(context, MOLD, key, _default);
    }

    /**
     * 获取String类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    /**
     * 获取String类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String mode, String key, String _default) {
        SharedPreferences sp = context.getSharedPreferences(mode, Context.MODE_PRIVATE);
        return sp.getString(key, _default);
    }

    /**
     * 获取float类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static float getFloat(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        return sp.getFloat(key, 0f);
    }

    /**
     * 获取long类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static long getLong(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        return sp.getLong(key, 0l);
    }

    /**
     * 获取boolean类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 获取int类型的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 设置String 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setString(Context context, String key, String value) {
        interiorset(context, MOLD, key, value);
    }

    /**
     * 设置String 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setString(Context context, String name, String key, String value) {
        interiorset(context, name, key, value);
    }

    /**
     * 设置Float 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setFloat(Context context, String key, float value) {
        interiorset(context, MOLD, key, value);
    }

    /**
     * 设置Long 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setLong(Context context, String key, long value) {
        interiorset(context, MOLD, key, value);
    }

    /**
     * 设置Int 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setInt(Context context, String key, int value) {
        interiorset(context, MOLD, key, value);
    }

    /**
     * 设置Boolean 类型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setBoolean(Context context, String key, boolean value) {
        interiorset(context, MOLD, key, value);
    }

    /**
     * 设置类型参数
     *
     * @param context
     * @param name
     * @param key
     * @param value
     */
    private static void interiorset(Context context, String name, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, value.toString());
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Float) {
            edit.putFloat(key, Float.parseFloat(value.toString()));
        } else if (value instanceof Integer) {
            edit.putInt(key, Integer.parseInt(value.toString()));
        } else if (value instanceof Long) {
            edit.putLong(key, Long.parseLong(value.toString()));
        }

        edit.commit();
    }

    /**
     * 保存对象
     *
     * @param context
     * @param key
     * @param object
     */
    public static void saveObject(Context context, String key, Object object) {
        SharedPreferences preferences = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(object);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.NO_WRAP));
            Editor editor = preferences.edit();
            editor.putString(key, oAuth_Base64);
            editor.commit();
        } catch (IOException e) {
        }
    }

    /**
     * 获取对象
     *
     * @param context
     * @param key
     * @return
     */
    public static Object readObject(Context context, String key) {
        Object object = null;
        SharedPreferences preferences = context.getSharedPreferences(MOLD, Context.MODE_PRIVATE);
        String productBase64 = preferences.getString(key, "");

        //读取字节
        byte[] base64 = Base64.decode(productBase64, Base64.NO_WRAP);
        if (base64.length > 0) {
            //封装到字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            try {
                //再次封装
                ObjectInputStream bis = new ObjectInputStream(bais);
                try {
                    //读取对象
                    object = bis.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static void remove(Context context, String name,String key) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE + Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE + Context.MODE_MULTI_PROCESS);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}

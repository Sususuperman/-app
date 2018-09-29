package com.hywy.publichzt.utils;

import android.content.Context;
import android.content.Intent;

import com.hywy.publichzt.activity.ClearActivity;


public class StartIntent
{
    /**
     * 退出到登录界面,并清除用户数据
     * @param context
     */
    public static void startExit(Context context){
        Intent intent = new Intent(context, ClearActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isFinish", ClearActivity.wNormalEnd);
        context.startActivity(intent);
    }

    /**
     * 退出app
     * @param context
     */
    public static void startExitApp(Context context){
        Intent intent = new Intent(context, ClearActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isFinish", ClearActivity.wDoubleEnd);
        context.startActivity(intent);
    }

}

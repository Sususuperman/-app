package com.cs.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class PhoneUtil {

    
    public static int getDisplayWidth(Context context){
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      return localDisplayMetrics.widthPixels;
    }

    
    public static int getDisplayHight(Context context){
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      return localDisplayMetrics.heightPixels;
    }


    public static int dp2px(Context context, int paramInt){
        Display localDisplay = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        localDisplay.getMetrics(localDisplayMetrics);
        return (int)(0.5F + paramInt * localDisplayMetrics.density);
    }

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 

}

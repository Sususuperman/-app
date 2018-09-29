package com.cs.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

/**
 * Drawable 工具类
 * @author likeyong
 *
 */
public abstract class DrawableUtils 
{

	/**
	 * 根据指定的宽高设定底部中心点
	 * @param dra Drawable对象
	 * @param width 宽
	 * @param height 高
	 * @return
	 */
	public static Drawable boundBottomCenter(Drawable dra ,int width,int height){
		//重新设置宽高
		if(dra != null)
		{
			dra.setBounds(0, 0,width, height);
			Rect srcRect=dra.getBounds();  
			//设置底部中心点
	        srcRect.offset(-width/2, -height);
	        dra.setBounds(srcRect);	
		}
		return dra;
	}
	/**
	 * 根据指定的宽高设定中心点
	 * @param dra Drawable对象
	 * @param width 宽
	 * @param height 高
	 * @return
	 */
	public static Drawable boundCenter(Drawable dra ,int width,int height){
		//重新设置宽高
		if(dra != null)
		{
			dra.setBounds(0, 0,width, height);
			Rect srcRect=dra.getBounds();  
			//设置底部中心点
	        srcRect.offset(-width, -height);
	        dra.setBounds(srcRect);			
		}
		return dra;
	}
	
	/**
	 * 根据图片路径获取Drawable对象，次方法可有效避免内存溢出
	 * 
	 * @param pathName 图片路径
	 * @param maxNumOfPixels 屏幕分辨率
	 * @return Drawable 对象
	 */
    public static Drawable createFromPath(String pathName,int maxNumOfPixels) {
        if (pathName == null) {
            return null;
        }

        Bitmap bm =FileUtils.loadBitmap(pathName, maxNumOfPixels);
        if (bm != null) {
            return drawableFromBitmap(null, bm, null, null, null, pathName);
        }

        return null;
    } 
	
	private static Drawable drawableFromBitmap(Resources res, Bitmap bm, byte[] np,
            Rect pad, Rect layoutBounds, String srcName) {

        if (np != null) {
            return new NinePatchDrawable(res, bm, np, pad, srcName);
        }

        return new BitmapDrawable(res, bm);
    }
}

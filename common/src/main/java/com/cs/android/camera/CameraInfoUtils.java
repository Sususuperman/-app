package com.cs.android.camera;

import android.os.Build;
import android.view.SurfaceView;


public final class CameraInfoUtils
{

	//在surfaceCreated中被调用
	public static CameraInfo.Parameter openCamera(SurfaceView surfaceView, int paramInt)
	{
		int version = Build.VERSION.SDK_INT;
		if(version >= 9)//简化处理
		{
			return new Level9().open(surfaceView, paramInt);
		}
		else if(version >= 8)
		{
			return new Level8().open(surfaceView, paramInt);
		}
		return new Level7().open(surfaceView, paramInt);
	}
	
}

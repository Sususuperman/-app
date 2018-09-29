package com.cs.android.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.WindowManager;

@TargetApi(9)
public class Level9 implements CameraInfo
{
	@Override
	public int nums()
	{
		return Camera.getNumberOfCameras();
	}

	@Override
	public Parameter open(SurfaceView surfaceView, int paramInt)
	{
		try
		{
			Parameter p = new Parameter();
			p.camera = Camera.open(paramInt);
			Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
      Camera.getCameraInfo(paramInt, localCameraInfo);
      
      WindowManager windowManager = (WindowManager)surfaceView.getContext().getSystemService(Context.WINDOW_SERVICE);

      if (windowManager.getDefaultDisplay().getRotation() == Surface.ROTATION_0 && 
      		localCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
      {
          int j = (360 + localCameraInfo.orientation % 360) % 360;
          p.camera.setDisplayOrientation(j);
          p.orientation = localCameraInfo.orientation;
      }
      
			return p;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}

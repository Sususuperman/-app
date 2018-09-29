package com.cs.android.camera;

import android.hardware.Camera;
import android.view.SurfaceView;

public class Level8 implements CameraInfo
{

	@Override
	public int nums()
	{
		return 0;
	}

	@Override
	public Parameter open(SurfaceView surfaceView, int paramInt)
	{
		Parameter p = new Parameter();
    p.camera = Camera.open();
    p.orientation = 90;
    p.camera.setDisplayOrientation(p.orientation);
    return p;
	}
}

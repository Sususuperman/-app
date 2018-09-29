package com.cs.android.camera;

import android.hardware.Camera;
import android.view.SurfaceView;

public interface CameraInfo
{
  public int nums();

  public Parameter open(SurfaceView surfaceView, int paramInt);
  
  public class Parameter
  {
    public Camera camera;
    public int orientation;
  }
}



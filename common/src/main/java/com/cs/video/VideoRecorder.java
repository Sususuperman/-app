package com.cs.video;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.cs.android.camera.CameraInfo;
import com.cs.android.camera.CameraInfoUtils;
import com.cs.common.utils.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class VideoRecorder implements Camera.PreviewCallback,Callback
{
	private static final String TAG = "VideoRecorder";

	private boolean isShooting = false;//开始摄录
	private boolean isPreviewing = false;//开始预览
	
	private RecorderParam param;
	private Camera camera = null;
	private FileOutputStream outStream;
	private SurfaceView surfaceView;

	public VideoRecorder(SurfaceView surfaceView, RecorderParam param)
	{
		super();
		this.surfaceView = surfaceView;
		this.param = param;
	}

	public boolean init()
	{
		try
		{
			outStream = new FileOutputStream(param.videoFile);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void start()
	{
		param.videoSize = 0;
		isShooting = true;
	}
	
	public void stop()
	{
		isShooting = false;
	}
	
	private void changeSurfaceViewSize()
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager)surfaceView.getContext().getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    
		LayoutParams params = surfaceView.getLayoutParams();
		double ratio = displayMetrics.densityDpi / 160.0;
		params.width = (int)(ratio * param.width);
		params.height = (int)(ratio * param.height);
		surfaceView.setLayoutParams(params);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{		
		if(isPreviewing)
			return;
		
		Camera.Parameters parameters = camera.getParameters();
		
		List<Size> previewSizes = parameters.getSupportedPreviewSizes();
		
		int maxSize = Integer.MAX_VALUE;
		
		for(int i = 0; i < previewSizes.size(); i++)
		{
			Logger.d(TAG," height:" + previewSizes.get(i).height + ",width:"+previewSizes.get(i).width);
		}
		
		//原则是:用最小分辨率但是大于设定高度和宽度的配置
		for(int i = 0; i < previewSizes.size(); i++)
		{
			Size size = previewSizes.get(i);
			int currSize = size.height * size.width;

			//注意图像是翻转90度的.
			if(size.height < param.width || size.width < param.height || currSize > maxSize)
				continue;
			
			param.videoHeight = size.height;
			param.videoWidth = size.width;
			maxSize = currSize;
		}
		
		try
		{
			parameters.setPreviewSize(param.videoWidth, param.videoHeight);
			parameters.setPreviewFormat(ImageFormat.NV21);
			camera.setParameters(parameters);
			camera.setPreviewCallback(this);
			camera.setPreviewDisplay(holder);
			
			camera.startPreview();
			
			isPreviewing = true;
			changeSurfaceViewSize();
			
			
			Logger.d(TAG," previewSize,width:" + param.videoWidth + ",height:" + param.videoHeight);
			
			return;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Logger.d(TAG, "surfaceCreated");
		CameraInfo.Parameter p = CameraInfoUtils.openCamera(surfaceView, 0);
		camera = p.camera;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if (camera != null) 
		{
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		isPreviewing = false;
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera)
	{
		Logger.d(TAG, "data size:" + data.length);
		
		if(isShooting)
		{
			try
			{
				param.videoSize++;
				outStream.write(data, 0, data.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}

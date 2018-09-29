package com.cs.android.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 上传队列消息处理器
 * @author james
 *
 */
public abstract class QueueHandler extends Handler
{
	private final int success = 10;//成功
	private final int failure = 20;//失败
	
	public QueueHandler(Looper looper)
	{
		super(looper);
	}

	void success(Object result, Object param)
	{
		Message msg = new Message();
		msg.obj = new Object[]{result, param};
		msg.what = success;
		this.sendMessage(msg);
	}
	
	void failure(Object param)
	{
		Message msg = new Message();
		msg.obj = param;
		msg.what = failure;
		this.sendMessage(msg);
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		super.handleMessage(msg);
		if(msg.what == success)
		{
			Object[] obj = (Object[])msg.obj;
			finishedSuccess(obj[0], obj[1]);
		}
		else if(msg.what == failure)		
		{
			Object param = msg.obj;
			finishedFailure(param);
		}
	}
	
	/**
	 * 当任务完成后,处理逻辑
	 * @param result 结果
	 * @param param 调用此任务时传递的参数.
	 */
	public abstract void finishedSuccess(Object result, Object param);
	
	/**
	 * 当任务失败后(抛出异常),处理逻辑
	 * @param param 调用此任务时传递的参数.
	 */
	public abstract void finishedFailure(Object param);
}

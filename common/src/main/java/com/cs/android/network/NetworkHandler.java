package com.cs.android.network;

import android.os.Handler;
import android.os.Message;

/**
 * 网络可用性发生变化时的调用
 * @author james
 *
 */
public abstract class NetworkHandler extends Handler
{
	private final int what = 0;//位置变化
	
	@Override
	public void handleMessage(Message msg)
	{
		super.handleMessage(msg);
		if(msg.what == what)
		{
			boolean canConnect = msg.arg1 == 1 ? true:false;
			nwtworkChanged(canConnect);
		}
	}
	
	//位置发生变化时的处理方法
	public abstract void nwtworkChanged(boolean canConnect);
	
	//发送位置变化
	public void changeNetworkStatus(boolean canConnect)
	{
		Message msg = new Message();
		msg.what = what;
		msg.arg1 = canConnect?1:0;
		
		this.sendMessage(msg);
	}

}

package com.cs.android.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cs.common.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class NetworkMng extends BroadcastReceiver 
{
	private Context context;//应用程序
	private ConnectivityManager manager;
	private boolean canConnect = false;
	
	private List<NetworkHandler> handlerList = new ArrayList<NetworkHandler>();
	
	public NetworkMng(Context context)
	{
		super();
		this.context = context;
		
		manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		canConnect = this.dealNetworkInfo();
	}
	
	public boolean isCanConnect()
	{
		canConnect = this.dealNetworkInfo();
		return canConnect;
	}
	
	//注册获得位置变化的通知
	public void registNotify(NetworkHandler networkHandler)
	{
		this.handlerList.add(networkHandler);
	}
	//取消注册获得位置变化的通知
	public void unRegistNotify(NetworkHandler networkHandler)
	{
		this.handlerList.remove(networkHandler);
	}

	//获得网络是否可用
	private boolean dealNetworkInfo() {
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null && info.isConnected()) {
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	//当通知来时处理方法
	@Override
	public void onReceive(Context context, Intent intent)
	{
		//dealNetworkInfo();
		this.canConnect = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		
		for(NetworkHandler networkHandler : handlerList)
		{
			networkHandler.changeNetworkStatus(this.canConnect);
		}
		Logger.d("NetworkMng", "网络状态：" + canConnect);
	}
	
	//开始网络监控
	public void startReceive()
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(this,filter);
	}
	
	//停止网络监控
	public void stopReceive()
	{
		context.unregisterReceiver(this);
	}

}

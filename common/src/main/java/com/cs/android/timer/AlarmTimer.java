package com.cs.android.timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cs.common.utils.StringUtils;


/**
 * 基于alarm提醒到定时任务，此功能可以在系统处于节电状态下使用
 * @author james
 *
 */
public class AlarmTimer
{
	private Context context;
	private BroadcastReceiver broadcastReceiver;
	private String action;
	private long delay;
	private long interval;
	
	/**
	 * 构造一个定时任务
	 * @param context
	 * @param broadcastReceiver	定时被触发时的消息接收器，注意此类的onReceive方法必须在5秒内返回
	 * @param interval					定时间隔，注意是毫秒
	 */
	public AlarmTimer(Context context, BroadcastReceiver broadcastReceiver, long interval)
	{
		this(context, broadcastReceiver, interval, 0);
	}
	
	/**
	 * 
	 * 构造一个定时任务
	 * @param context
	 * @param broadcastReceiver	定时被触发时的消息接收器，注意此类的onReceive方法必须在5秒内返回
	 * @param interval					定时间隔，注意是毫秒
	 * @param delay							延迟时间
	 */
	public AlarmTimer(Context context, BroadcastReceiver broadcastReceiver, long interval, long delay)
	{
		this(context, broadcastReceiver, "com.cs.firecontrol.action." + StringUtils.getUUID(), interval, delay);
	}
	
	/**
	 * 构造一个定时任务
	 * @param context
	 * @param broadcastReceiver	定时被触发时的消息接收器，注意此类的onReceive方法必须在5秒内返回
	 * @param action						receiver对应的action
	 * @param interval					定时间隔，注意是毫秒
	 * @param delay							延迟时间
	 */
	public AlarmTimer(Context context, BroadcastReceiver broadcastReceiver, String action, long interval, long delay)
	{
		super();
		this.context = context.getApplicationContext();
		this.broadcastReceiver = broadcastReceiver;
		this.action = action;
		this.interval = interval;
		this.delay = delay;
	}

	/**
	 * 启动此定时任务，系统会在固定的间隔下，重复发送通知，调用broadcastReceiver
	 */
	public void start()
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);

		context.registerReceiver(broadcastReceiver, filter);
		
		//注册时钟定时通知
		Intent intent = new Intent(action);  
		PendingIntent piSaveLocation = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  

		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);  
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, interval, piSaveLocation);  
	}
	
	/**
	 * 停止定时任务
	 */
	public void stop()
	{
		//取消时钟定时通知
		Intent intent = new Intent(action);  
		PendingIntent piSaveLocation = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
		  
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(piSaveLocation);
		
		//取消ACTION通知接收器
		context.unregisterReceiver(broadcastReceiver);
	}
}

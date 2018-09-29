package com.cs.android.async;

import android.os.HandlerThread;
import android.os.Looper;

import com.cs.android.task.Task;
import com.cs.common.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务队列
 * @author james
 *
 */
public class AsyncQueue
{
	private static final String TAG = "AsyncQueue";

	private List<QueueHandler> handlerList = new ArrayList<QueueHandler>();
	
	private HandlerThread  handlerThread;
	private BlockingQueue<Runnable> queue;
	private ThreadPoolExecutor executor;
	
	/**
	 * 创建一个线程池
	 * @param coreSize	线程池最大线程数量
	 */
	public AsyncQueue(int coreSize)
	{
		super();
		
		queue = new LinkedBlockingQueue<Runnable>();
		// 线程池：最大coreSize条，maximumPoolSize和keepAliveTime在此队列条件下无意义
		executor = new ThreadPoolExecutor(coreSize, coreSize, 1, TimeUnit.SECONDS, queue);
		
		handlerThread = new HandlerThread("AsyncQueue");
		handlerThread.start();
	}
	
	public Looper getQueueLooper()
	{
		return handlerThread.getLooper();
	}

	/**
	 * 停止一个线程池工作
	 */
	public void stop()
	{
		executor.purge();
		executor.shutdown();
	}
	
	/**
	 * 注册队列完成信息处理器
	 * @param handler
	 * @throws Exception
	 */
	public void registNotify(QueueHandler handler) throws Exception 
	{
		if(!handler.getLooper().equals(handlerThread.getLooper()))
		{
			throw new Exception("消息处理器不能被队列驱动");
		}
		this.handlerList.add(handler);
	}

	/**
	 * 取消注册列完成信息处理器
	 * @param handler
	 */
	public void unRegistNotify(QueueHandler handler) 
	{
		this.handlerList.remove(handler);
	}

	/**
	 * 添加一个任务到任务队列
	 * @param task
	 */
	public void addTask(final Task task)
	{
		executor.execute(new Runnable() {
			@Override
			public void run() 
			{
				try
				{
					Map<String, Object> result = task.execute();
					Logger.d(TAG, "成功完成异步任务");
					
					for (QueueHandler handler : handlerList) 
					{
						handler.success(result, task.getParam());
					}
				} 
				catch (Exception e)
				{
					Logger.d(TAG, "异步任务出现异常");
					e.printStackTrace();
					
					for (QueueHandler handler : handlerList) 
					{
						handler.failure(task.getParam());
					}
				}
			}
		});
	}
}

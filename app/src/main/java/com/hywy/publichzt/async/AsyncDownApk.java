package com.hywy.publichzt.async;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.cs.common.utils.HttpUtils;
import com.hywy.publichzt.entity.Upgrade;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 异步的线程池控制的图片下载器。
 * 
 */
public class AsyncDownApk
{
	private static final String TAG = "AsyncDownApk";
	private BlockingQueue<Runnable> queue;// 下载队列
	private ThreadPoolExecutor executor;// 下载线程池
	private Context context;
	AtomicBoolean isStoped;

	public AsyncDownApk(Context context)
	{
		this.context = context;
		// 线程池：最大3条，maximumPoolSize和keepAliveTime在此队列条件下无意义
		queue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(3, 50, 120, TimeUnit.SECONDS, queue);
	}

	public void loadApk(final Map<String, Object> params, AtomicBoolean isStoped)
	{
		this.isStoped = isStoped;
		// 用线程池来做加载或者下载图片的任务
		executor.execute(new Runnable() {
			@Override
			public void run()
			{
				downApk(params);
			}
		});
	}

	public void stop()
	{
		queue.clear();
	}

	private void downApk(Map<String, Object> param)
	{
		String client_url = (String) param.get("client_url");
		Handler handler = (Handler) param.get("handler");
		Integer progress = (Integer) param.get("progress");
		Upgrade upgrade = null;
        if (param.containsKey("upgrade")){
            upgrade = (Upgrade) param.remove("upgrade");
        }
		try
		{
			String path = HttpUtils.getCachedFile(context, client_url, handler, progress, isStoped);

			// 成功了,则将obj参数设置为下载后的文件路径
			Integer success = (Integer) param.get("success");
			Message msg = new Message();
			msg.what = success;

			if (upgrade != null){
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("path", path);
				result.put("upgrade", upgrade);
				msg.obj = result;

			}else{
				msg.obj = path;
			}
			handler.sendMessage(msg);
		} catch (Exception e){
			// 出现异常了，要发一个消息过去，
			Integer failure = (Integer) param.get("failure");
			Message msg = new Message();
			msg.what = failure;
			if (upgrade != null){
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("upgrade", upgrade);
				msg.obj = result;
				handler.sendMessage(msg);
			}else{
				handler.sendEmptyMessage(failure);
			}
		}
	}
}

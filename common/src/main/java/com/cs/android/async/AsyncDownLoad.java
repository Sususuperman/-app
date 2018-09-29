package com.cs.android.async;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.cs.common.listener.OnDownLoadListenter;
import com.cs.common.utils.HttpUtils;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 异步的线程池控制的图片下载器。
 *
 */
public class AsyncDownLoad
{
	public static final int SUCCESS = 100;
	public static final int FAILURE = 101;
	public static final int PROGRESS = 102;

	private static final String TAG = "AsyncDownLoad";
	private BlockingQueue<Runnable> queue;// 下载队列
	private ThreadPoolExecutor executor;// 下载线程池
	private Context context;
	AtomicBoolean isStoped = new AtomicBoolean(false);
	private OnDownLoadListenter onDownLoadListenter;
	public AsyncDownLoad(Context context)
	{
		this.context = context;
		// 线程池：最大3条，maximumPoolSize和keepAliveTime在此队列条件下无意义
		queue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(3, 50, 120, TimeUnit.SECONDS, queue);
	}

	public void loadFile(final String url,OnDownLoadListenter loadListenter) {
		this.onDownLoadListenter = loadListenter;
		// 用线程池来做加载或者下载图片的任务
		executor.execute(new Runnable() {
			@Override
			public void run() {
				downLoad(url);
			}
		});
	}

	public void stop()
	{
		queue.clear();
	}

	private void downLoad(String url) {
		try {
			// 成功了,则将obj参数设置为下载后的文件路径
			String path = HttpUtils.getCachedFile(context, url, handler, PROGRESS, isStoped);
			createMessage(path,SUCCESS);
		} catch (Exception e) {
			createMessage(url,FAILURE);
		}
	}

	private void createMessage(Object obj,int what){
		Message msg = new Message();
		msg.obj = obj;
		msg.what = what;
		handler.sendMessage(msg);
	}


	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case SUCCESS:
					if(onDownLoadListenter != null){
						onDownLoadListenter.onLoadSuccess(msg.obj.toString());
					}
					break;
				case FAILURE:
					if(onDownLoadListenter != null){
						onDownLoadListenter.onLoadFail(msg.obj.toString());
					}
					break;
				case PROGRESS:
					if(onDownLoadListenter != null){
						onDownLoadListenter.onLoadProgress(msg.arg1,msg.arg2);
					}
					break;
			}
		}
	};

	public String getCacheDir(){
		return context.getExternalCacheDir().getPath();
	}

	/**
	 * 判断是否存在
	 * @param url
	 * @return
     */
	public boolean isExists(String url){
		return new File(getPath(url)).exists();
	}

	/**
	 * 获取本地路径
	 * @param url
	 * @return
     */
	public String getPath(String url){
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		String tempFilePath = getCacheDir() + File.separator + fileName;
		return tempFilePath;
	}
}

package com.cs.android.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;

import com.cs.android.task.Task;

public class AsyncExecutant
{
	private AsyncThreadExecutant executant;
	
	public AsyncExecutant(Context context, Task task)
	{
		this(context, task, null);
	}

	public AsyncExecutant(Context context, Task task, Integer watiing)
	{
		this(context, task, watiing, null, null, null);
	}

	public AsyncExecutant(Context context, Task task, Handler handler,
			Integer success, Integer failure)
	{
		this(context, task, null, handler, success, failure);
	}

	public AsyncExecutant(Context context, Task task, Integer watiing,
			Handler handler, Integer success, Integer failure)
	{
		this(context, task, watiing, handler, success, failure, true);
	}


	public AsyncExecutant(Context context, Task task, Integer watiing,
			Handler handler, Integer success, Integer failure, Boolean stauts)
	{
		super();
		executant = new AsyncThreadExecutant(context, task, watiing, handler, success, failure, stauts);

	}

	public void execute()
	{
		int version = Build.VERSION.SDK_INT;
		if(version >= 11)//要用线程处理
		{
			executant.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			executant.execute();
		}
	}
}

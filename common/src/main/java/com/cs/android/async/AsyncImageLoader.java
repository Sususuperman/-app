package com.cs.android.async;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cs.common.utils.FileUtils;
import com.cs.common.utils.HttpUtils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步的线程池控制的图片下载器。
 * @author james
 *
 */
public class AsyncImageLoader 
{
    private static final String TAG = "AsyncImageLoader";

    private HashMap<Object, SoftReference<Drawable>> imageCache;//图片的cache
    private BlockingQueue<Runnable> queue;//下载队列
    private ThreadPoolExecutor executor;//下载线程池

    public AsyncImageLoader() 
    {
        imageCache = new HashMap<Object, SoftReference<Drawable>>();

        // 线程池：最大3条，maximumPoolSize和keepAliveTime在此队列条件下无意义
        queue = new LinkedBlockingQueue<Runnable>();
        executor = new ThreadPoolExecutor(3, 50, 120, TimeUnit.SECONDS, queue);
    }

    public Drawable loadDrawable(final Context context, final Object key,
            final String localPath, final String imageUrl, final ImageCallback imageCallback) 
    {
        //如果当前内存cache中保存了图片，则直接返回
        if (imageCache.containsKey(key)) 
        {
            //修改头像之后，会出现刷新列表之后头像，新的头像不能显示
            File imageFile = new File(localPath);
            if(imageFile.exists())
            {
                SoftReference<Drawable> softReference = imageCache.get(key);
                Drawable drawable = softReference.get();
                if (drawable != null) 
                {
                    return drawable;
                }
            }
            else 
            {
                imageCache.remove(key);
            }
        }
        
        if (localPath == null || imageUrl == null) 
        {
            return null;
        }
        
        //现在，此图片没有在内存中存在，则新建一个消息通知，当收到消息时，表示图片下载完成了。
        final Handler handler = new Handler() 
        {
            public void handleMessage(Message message) 
            {
                Drawable drawable = (Drawable) message.obj;
                imageCallback.imageLoaded(key, drawable);
            }
        };

        // 用线程池来做加载或者下载图片的任务
        executor.execute(new Runnable() {
            @Override
            public void run() 
            {
                Drawable drawable = loadImageFromUrl(context, localPath, imageUrl);
                if(drawable == null)
                {
                    return;
                }
                imageCache.put(key, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        });

        return null;
    }

    // 网络图片先下载到本地cache目录保存，以imagUrl的图片文件名保存。如果有同名文件在本地目录就从本地加载
    private Drawable loadImageFromUrl(Context context, String localPath, String imageUrl) 
    {
        Drawable drawable = null;

        boolean isFileNotExist = false;
        try
        {
            File file = new File(localPath);// 保存文件
            isFileNotExist = !file.exists() || file.length() == 0L;
        }
        catch(Exception e)
        {
        }
        
        if(isFileNotExist) 
        {
            try 
            {
                //下载，并且将文件保存到本地路径
                String tmpPath = HttpUtils.getCachedFile(context, imageUrl);
                FileUtils.copy(tmpPath, localPath);
                drawable = Drawable.createFromPath(localPath);
            } 
            catch (Exception e) 
            {
                Log.e(TAG, e.toString() + "图片下载及保存时出现异常！");
                return null;
            }
        } 
        else
        {
            drawable = Drawable.createFromPath(localPath);
        }
        return drawable;
    }
    
    public void stop()
    {
        queue.clear();
    }

    /**
     * 回调接口，当收到图片获得的消息时，调用此接口方法。
     * key:                         当时调用AsyncImageLoader的loadDrawable传入的相应参数
     * imageDrawable:       加载或者下载图片后的对象
     * @author james
     *
     */
    public interface ImageCallback 
    {
        public void imageLoaded(Object key, Drawable imageDrawable);
    }

}

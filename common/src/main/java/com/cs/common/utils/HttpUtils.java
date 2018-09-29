package com.cs.common.utils;

import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http请求工具类
 *
 * @author james
 */
public abstract class HttpUtils {
    private static final String TAG = "HttpUtils";
    private static OkHttpClient client;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }


    /**
     * 访问http，获得字符串，字符编码由网站决定
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getString(String url, Map<String, Object> params) throws Exception {
        if (params != null) {
            StringBuffer buf = new StringBuffer();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                buf.append("&").append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
            if (url.indexOf("?") != -1)// 已经有参数
            {
                url += buf.toString();
            } else {
                url += "?" + buf.substring(1);
            }
        }
        Log.d(TAG, "getString,url:" + url);

        try {
            Request build = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .addHeader("User-Agent", "android")
                    .build();

            Response response = client.newCall(build).execute();
            String content = response.body().string();
            Log.d(TAG, "getString,content:" + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
    }

    /**
     * 使用http的post方法发放松请求，返回字符串
     *
     * @param url    访问的地址
     * @param params 要提交的参数的集合，注意key是提交的每个参数的名字，value是数据。如果value是文件，则代表其中存在上传的文件
     * @return
     */
    public static String postString(String url, final Map<String, Object> params)
            throws Exception {
        Logger.d(TAG, "postAccessory,url:" + url);
        RequestBody requestBody = null;
        if (isFlieRequest(params)) {
            requestBody = postFile(params);
        } else {
            requestBody = postString(params);
        }

        try {

            Request request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "text/html; charset=utf-8")
                    .addHeader("User-Agent", "android")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("服务器端错误: " + response);
            }

            String content = response.body().string();

            Logger.d(TAG, "postAccessory,content:" + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
    }

    /**
     * 是否是文件上传
     *
     * @param params
     * @return
     */
    private static boolean isFlieRequest(Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object values = entry.getValue();
            if (values instanceof File) {
                return true;
            }
        }
        return false;
    }


    private static RequestBody postFile(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object values = entry.getValue();
            String key = entry.getKey();
            if (values instanceof File) {
                File file = (File) values;
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            } else {
                builder.addFormDataPart(key, values.toString());
            }

            Logger.d(key+"："+values.toString()+"\n");
        }

        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    private static RequestBody postString(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object values = entry.getValue();
            String key = entry.getKey();
            builder.add(key, values.toString());
            Logger.d(key+"："+values.toString()+"\n");
        }
        return builder.build();
    }

    /**
     * 通过get方法，将请求到结果保存为临时文件，返回参数为临时文件全路径。
     *
     * @param url      要下载的文件的路径
     * @param handler  下载过程的消息处理器
     * @param message  下载过程中已经下载字节数的消息编号，
     *                 arg1参数是当前已经下载的大小,
     *                 arg2是整个文件的大小（服务器支持）
     * @param isStoped 下载过程中停止的标志
     * @return
     * @throws Exception
     */
    public static String getCachedFile(Context context, String url, Handler handler, int message, AtomicBoolean isStoped) throws Exception {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Logger.d(TAG, "getTempFile,url:" + url);
        }
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        FileOutputStream fos = null;
        InputStream ios = null;
        try {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String tempFilePath = context.getExternalCacheDir().getPath() + File.separator + fileName;
            ios = response.body().byteStream();

            long fileLength = response.body().contentLength();
            File file = new File(tempFilePath);
            fos = new FileOutputStream(file);
            byte buf[] = new byte[512];
            //收到的总字节数和当前字节数
            int totalSize = 0, size;
            int b = 0;
            while ((size = ios.read(buf)) != -1) {
                if (isStoped != null && isStoped.get()) {
                    return null;
                }

                fos.write(buf, 0, size);
                totalSize += size;
                int a = totalSize * 100 / (int) fileLength;
                if (handler != null && (a - b >= 1 || totalSize == fileLength)) {
                    b = a;
                    Message msg = new Message();
                    msg.arg1 = totalSize;
                    msg.arg2 = (int) fileLength;
                    msg.what = message;
                    handler.sendMessage(msg);
                }

            }
            return tempFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ios.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCachedFile(Context context, String url, Handler handler, int message) throws Exception {
        return getCachedFile(context, url, null, 0, null);
    }

    public static String getCachedFile(Context context, String url) throws Exception {
        return getCachedFile(context, url, null, 0);
    }

}

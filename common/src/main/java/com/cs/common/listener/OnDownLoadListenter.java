package com.cs.common.listener;

/**
 * 网络请求监听状态
 * @author weifei
 *
 */
public interface OnDownLoadListenter extends BaseListener {
    /**
     * 下载成功
     * @param path
     */
    void onLoadSuccess(String path);
    /**
     * 下载失败
     * @param url
     */
    void onLoadFail(String url);

    /**
     * 下载进度
     * @param total
     * @param progress
     */
    void onLoadProgress(int total,int progress);
}

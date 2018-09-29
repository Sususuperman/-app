package com.cs.upgrade;

import com.cs.upgrade.entity.Upgrade;

/**
 * @author Superman
 */

public interface OnDownloadingDialogListener {
    /**
     * 开始更新
     * 此方法为点击下载按钮时的回调方法，在这里可以处理下载开始前的一些逻辑
     * 例如：弹窗显示，toast提示等
     */
    void onPreUpdate();

    /**
     * 下载进度回调，使用时需将progressbar的max值设为100,
     * progress为%值 例如50 就是50%
     *
     * @param progress
     */
    void onProgressUpdate(int progress);

    /**
     * 更新结束回调
     */
    void onFinishedUpdate();
}

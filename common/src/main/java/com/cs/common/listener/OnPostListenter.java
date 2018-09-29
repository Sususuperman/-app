package com.cs.common.listener;

import org.json.JSONException;

import java.util.Map;

/**
 * 网络请求监听状态
 *
 * @author weifei
 */
public interface OnPostListenter extends BaseListener {
    /**
     * 请求成功
     *
     * @param result
     */
    void OnPostSuccess(Map<String, Object> result);

    /**
     * 请求失败
     *
     * @param result
     */
    void OnPostFail(Map<String, Object> result);
}

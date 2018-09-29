package com.cs.upgrade;

import com.cs.upgrade.entity.Upgrade;

import java.util.Map;

/**
 * Created by Superman on 2017/10/18.
 */

public interface OnBackResultListener {
    /**
     * 请求成功
     * @param upgrade
     */
    void OnResultSuccess(Upgrade upgrade);
}

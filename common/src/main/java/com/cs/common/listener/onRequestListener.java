package com.cs.common.listener;

import com.cs.android.task.Task;
import com.cs.common.handler.BaseHandler_;

import java.util.Map;

/**
 * Created by weifei on 16/9/18.
 */
public interface onRequestListener {
    void onRequest(Map<String, Object> params, Task task);

    void setBulider(BaseHandler_.Builder bulider);

    void setListeners(OnPostListenter listener);
}

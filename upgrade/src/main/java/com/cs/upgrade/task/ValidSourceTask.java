package com.cs.upgrade.task;

import android.content.Context;

import com.cs.android.network.NetworkMng;
import com.cs.android.task.Task;
import com.cs.common.HttpConst;
import com.cs.common.baseapp.BaseApp;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有访问非登陆api接口的网络访问任务从此类继承,继承类可以访问保护成员context
 *
 * @author james
 */
public abstract class ValidSourceTask implements Task {
    protected Context context;
    protected Map<String, Object> params;

    public ValidSourceTask(Context context, Map<String, Object> params) {
        super();
        this.context = context;
        this.params = params;
    }

    @Override
    public Map<String, Object> execute() throws Exception {
        NetworkMng networkMng = BaseApp.getBaseInstance().getNetworkMng();
        //如果网络不能连接，立即返回
        if (!networkMng.isCanConnect()) {
            Map<String, Object> r = new HashMap<String, Object>();
            r.put("ret", HttpConst.ApiNoNet);
            return r;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("APPTYPE", 2);
        if (params != null) {
            map.putAll(params);
        }

//		Map<String,Object> result =  sendData(map);
//		if(result != null){
//			params.putAll(result);
//		}
        return sendData(map);
    }

    @Override
    public Object getParam() {
        return params;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 提交服务时,要将此params参数提交给HttpUtils的方法,可以删除或者添加参数.
     *
     * @param params
     * @return
     * @throws Exception
     */
    protected abstract Map<String, Object> sendData(Map<String, Object> params) throws Exception;
}

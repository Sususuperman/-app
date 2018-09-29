package com.cs.common.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.cs.android.common.R;
import com.cs.common.listener.BaseListener;


/**
 * Created by charmingsoft on 2015/7/2.
 */
public abstract class BaseHandler_<L extends BaseListener> extends Handler {
    protected Context mContext;
    protected L listener;
    protected Builder builder;
    public BaseHandler_(Context c, L l){
        this.mContext = c;
        this.listener = l;
        if(builder == null)
            builder = new Builder();
    }
    public BaseHandler_(Activity c){
        this(c,c instanceof BaseListener ? (L)c : null);
    }

    public void setBuilder(Builder builder){
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        protected boolean isWait = true;
        protected Integer loading = R.string.loading;//加载时的提示文字
        protected Integer loadFail = R.string.loadfail;//请求失败后的提示
        protected boolean isShowToast = true;
        protected Integer loadSuccess ;
        protected boolean isLogin;//是否是登录界面请求数据
        public Builder(){

        }
        public Builder isWait(boolean isWait){
            this.isWait = isWait;
            return this;
        }
        public Builder isLogin(boolean isLogin){
            this.isLogin = isLogin;
            return this;
        }

        public Builder isShowToast(boolean isShwoToast){
            this.isShowToast = isShwoToast;
            return this;
        }
        public Builder setLoadSuccess(Integer success){
            this.loadSuccess = success;
            return this;
        }
        public Builder setLoadFail(Integer loadFail){
            this.loadFail = loadFail;
            return this;
        }
        public Builder setLoading(Integer loading){
            this.loading = loading;
            this.isWait = true;
            return this;
        }
    }

    public L getListener(){
        return listener;
    }

    public void setListener(L l){
        listener = l;
    }
}

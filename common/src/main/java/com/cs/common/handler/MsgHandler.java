package com.cs.common.handler;

import android.os.Handler;
import android.os.Message;

public abstract class MsgHandler {

    /**
     * 发送请求
     * @param handler
     * @param what
     */
    public static void sendMessage(Object object,Handler handler,int what){
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        handler.sendMessage(msg);
    }
    /**
     * 发送请求
     * @param handler
     * @param what
     */
    public static void sendMessage(int arg1,Handler handler,int what){
        Message msg = new Message();
        msg.what = what;
        msg.arg1 = arg1;
        handler.sendMessage(msg);
    }



    /**
     * 发送请求
     * @param handler
     * @param what
     */
    public static void sendMessageDelayed(int arg1,Handler handler,int what,long time){
        Message msg = new Message();
        msg.what = what;
        msg.arg1 = arg1;
        handler.sendMessageDelayed(msg,time);
    }

    /**
     * 发送请求
     * @param handler
     * @param what
     */
    public static void sendMessageDelayed(Object object,Handler handler,int what,long time){
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        handler.sendMessageDelayed(msg,time);
    }
}

package com.cs.android.async;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cs.android.task.Task;
import com.cs.common.handler.WaitDialog;
import com.cs.common.utils.Logger;

import java.util.Map;

/**
 * 异步操作类,所有使用线程操作的动作可以直接使用此类 (注意:此类的execute方法调用后并不直接返回结果).
 * 此类的实例化和execute方法的调用必须在UI线程中. 成员变量Task是具体执行的动作
 * 成员变量watiing是显示在界面中提示框的文本,为空则不显示.
 * <p/>
 * 成员变量handler是动作执行完成后执行事件的触发器,为空则不发送事件.
 * <p/>
 * 成员变量success是发送执行成功的事件,为空则不发送事件.(注意:此处的成功指的是task执行无异常)
 * 否则发送消息，此消息的apogee参数为task的执行结果（一般是Map类）。
 * <p/>
 * 成员变量failure是动作执行失败的事件,为空则不发送事件.(注意:此处的失败指的是task执行出现异常)
 * 否则发送消息，此消息的obj参数为task的getParam方法返回的结果（一般是Map类）。
 *
 * @author james
 */
class AsyncThreadExecutant extends AsyncTask<Void, Void, Void>{
    private static final String TAG = "Executant";

    private Dialog waitDialog;
    protected Context context;
    protected Task task;
    protected Integer watiing;

    protected Handler handler;
    protected Integer success;
    protected Integer failure;
    protected Boolean stauts;
    protected boolean dialogShow = true;

    public AsyncThreadExecutant(Context context, Task task) {
        this(context, task, null);
    }

    public AsyncThreadExecutant(Context context, Task task, Integer watiing) {
        this(context, task, watiing, null, null, null);
    }

    public AsyncThreadExecutant(Context context, Task task, Handler handler,
                                Integer success, Integer failure) {
        this(context, task, null, handler, success, failure);
    }

    public AsyncThreadExecutant(Context context, Task task, Integer watiing,
                                Handler handler, Integer success, Integer failure) {
        this(context, task, watiing, handler, success, failure, true);
    }

    public AsyncThreadExecutant(Context context, Task task, Integer watiing,
                                Handler handler, Integer success, Integer failure, Boolean stauts ) {
        super();
        this.context = context;
        this.task = task;
        this.watiing = watiing;
        this.handler = handler;
        this.success = success;
        this.failure = failure;
        this.stauts = stauts;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (waitDialog != null) {
            try {
                waitDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // 初始化提示框
        if (watiing != null) {
            waitDialog = new WaitDialog(context, context.getString(watiing), stauts, dialogShow);
            waitDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    if (stauts) {
                        dialog.dismiss();
                    }
                }
            });
            waitDialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            Map<String, Object> result = task.execute();
            if (dialogShow && handler != null && success != null) {
                Message msg = new Message();
                msg.obj = result;
                msg.what = success;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            Logger.d(TAG, "异步任务出现异常");
            if (dialogShow && handler != null && failure != null) {
                Message msg = new Message();
                msg.obj = task.getParam();
                msg.what = failure;
                handler.sendMessage(msg);
            }
            e.printStackTrace();
        }
        return null;
    }
}

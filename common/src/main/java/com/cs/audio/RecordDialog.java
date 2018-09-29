package com.cs.audio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.android.common.R;

import java.util.Date;

/**
 * 
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to 
 * create and use your own look & feel.
 * 
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * @author antoine vianey
 *
 */
public class RecordDialog extends Dialog {
    private Context mContext;
    public RecordDialog(Context context, int theme) {
        super(context, theme);
    }
 
    public RecordDialog(Context context) {
        super(context);
        this.mContext = context;
    }
 
    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder implements Runnable{
 
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
 
        private OnClickListener
                        positiveButtonClickListener,
                        negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        
        ImageView record_img;
        TextView message_time;
        /**
         * Create the custom dialog
         */
        public RecordDialog create() {
            isStop = true;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final RecordDialog dialog = new RecordDialog(context, R.style.Dialog);
            View mLayout = inflater.inflate(R.layout.dialog_record, null);
            record_img = (ImageView)mLayout.findViewById(R.id.record_img);
            message_time = (TextView)mLayout.findViewById(R.id.message);
            dialog.addContentView(mLayout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            ((TextView) mLayout.findViewById(R.id.title)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) mLayout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) mLayout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                                    isStop =false;
                                    stop();
                                }
                            });
                }
            } else {
                mLayout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                ((Button) mLayout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) mLayout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                                    isStop = false;
                                    
                                    cancel();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                mLayout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) mLayout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) mLayout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) mLayout.findViewById(R.id.content)).addView(contentView,new LayoutParams(
                                        LayoutParams.WRAP_CONTENT, 
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(mLayout);
            this.start();
            return dialog;
        }

        @Override
        public void run() {
            while (isStop) {
                try {
                    Thread.sleep(step);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(UPDATE_IMG);
            }
        }
        public static final int UPDATE_IMG = 10;
        private int record_img_index = 0;//确认调用动画图的变量

        private static final int RecordCompelete = 100;
        @SuppressLint("HandlerLeak")
        private Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_IMG:
                        update();
                        break;
                    case RecordCompelete:
                        String path = msg.obj.toString();
                        ((IProcessRecorderFile) context).onSaveAudioFile(path);
                        if(dialog != null)
                            dialog.dismiss();
                        break;
                }
            }
        };
        
       
        private void update(){
            Date now = new Date();
            if(now.getTime()-last_print_time>step) {
                last_print_time  = now.getTime();
            }
            record_img_index++;
            long mDuration = (now.getTime() - beginDate.getTime())/1000;
            if (record_img_index == 0) {
                record_img.setImageDrawable(context.getResources().getDrawable(R.drawable.record_01));
            } else if (record_img_index == 1) {
                record_img.setImageDrawable(context.getResources().getDrawable(R.drawable.record_02));
            } else {
                record_img.setImageDrawable(context.getResources().getDrawable(R.drawable.record_03));
                record_img_index = -1;
            }
            message_time.setText(this.context.getString(R.string.recorder_alert_prompt_run) +String.format("%02d:%02d", mDuration / 60, mDuration % 60));
        }
        
        private boolean isrun = false;  //是否需要进行
        private Date beginDate;//开始录音的时间
        private RecorderProcess rproc;
        private long last_print_time= 0l;//最后打印的时间
        private boolean isStop;
        private static int step = 500;//动画步进节奏,单位毫秒
        /**
         * 开始录音
         */
        public void start() {
            if (!isrun) {
                beginDate = new Date();
                last_print_time = beginDate.getTime();
                rproc = new RecorderProcess();
                isrun=rproc.startRecord();
                //启动成功
                if(isrun){
                    new Thread(this).start();
                }else{
                    //启动失败
                    rproc=null;
                    Toast.makeText((Activity)context,  R.string.initfail, Toast.LENGTH_SHORT).show();
                }
            }
        }
        /**
         * 停止录音
         */
        public void stop() {
            if(isrun){
//                String file = rproc.saveRecordFile();

//                String mp3path = System.currentTimeMillis() + ".mp3";
//                String mp3path = StringUtils.getFileDir(file)+"/"+System.currentTimeMillis() + ".mp3";
//                ((IProcessRecorderFile) context).onSaveAudioFile(file);

                startRecording();
            }
        }

        WaitDialog dialog;
        private void startRecording(){
            dialog = new WaitDialog((Activity)context,"正在转码...",false);
            dialog.show();

            String file = rproc.saveRecordFile()+".mp3";
            Message msg = new Message();
            msg.what = RecordCompelete;
            msg.obj = file;
            mHandler.sendMessage(msg);
        }


        class WaitDialog extends Dialog {
            public WaitDialog(Context context, String msg, boolean isCancelable) {
                super(context, R.style.loading_dialog);
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.layout_loading, null);// 得到加载view
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
                // main.xml中的ImageView
                TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
                // 使用ImageView显示动画
                tipTextView.setText(msg);// 设置加载信息
//            setOnKeyListener(keylistener);
                setCancelable(isCancelable);
                setContentView(layout, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                return super.onTouchEvent(event);
            }

            @Override
            public void onBackPressed() {
                super.onBackPressed();
//            dialogShow = false;
            }

            // 拦截到屏幕触摸事件，禁止分发
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return false;
            }
        }

        /**
         * 取消录音
         */
        public void cancel() {
            if(isrun){
                rproc.cancelRecordFile();
            }
        }
    }
}
package com.cs.audio;

/**
 * COPYRIGHT (C) 2010 CHARMINGSOFT CORPRATION All Rights Reserved
 * User: huangbin_1
 * Date: 12-11-15
 * Time: 上午11:55
 * 用途:  处理录音的画面类以及基本控制类
 *
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.cs.android.common.R;

import java.util.Date;

/**
 *
 */
@SuppressLint("DrawAllocation")
public class Button4RecorderView extends View implements Runnable,
        DialogInterface.OnClickListener,
        DialogInterface.OnCancelListener {
    /**
     * 以下为录音画面的画面实现部分
     */
    private Paint mPaint;//绘制的地点
    private Canvas canvas;//默认画板
    private static int step = 500;//动画步进节奏,单位毫秒
    Context mcontext;

    /**
     * 初始化对象
     *
     * @param context content
     */
    public Button4RecorderView(Context context) {
        super(context);
        mcontext = context;

    }

    /**
     * 通过xml初始化对象
     *
     * @param context content
     * @param attr    xml属性
     */
    public Button4RecorderView(Context context, AttributeSet attr) {
        super(context, attr);
        mcontext = context;

    }


    /**
     * 重载 画图功能
     *
     * @param canvas1 指定的画板
     */
    @Override
    protected void onDraw(Canvas canvas1) {
        try {
            if (this.canvas == null) {
                mPaint = new Paint();
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(28);
                String mString = this.mcontext.getString(R.string.recorder_alert_prompt_ready);
                canvas1.drawText(mString, 100, 100, mPaint);
                this.start();
            } else {
                print();
                this.postInvalidate();
            }
            this.canvas = canvas1;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 指定视图的大小
     *
     * @param widthMeasureSpec  默认参数
     * @param HeightMeasureSpec 默认参数
     */
    protected void onMeasure(int widthMeasureSpec, int HeightMeasureSpec) {
        this.getLayoutParams().height = 150;
        this.getLayoutParams().width = 500;
        super.onMeasure(widthMeasureSpec, HeightMeasureSpec);
    }


    /**
     * 动画打印处理过程
     */
    private int record_img_index = 0;//确认调用动画图的变量
    private long last_print_time= 0l;//最后打印的时间

    private void print() {
        try {
            //动画间隔处理
            //图片动画处理
            Bitmap bmp;
            Date now = new Date();

            if(now.getTime()-last_print_time>step) {
                record_img_index++;
                last_print_time  = now.getTime();
            }
            if (record_img_index == 0) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.record_01);
            } else if (record_img_index == 1) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.record_02);
            } else {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.record_03);
                record_img_index = -1;
            }

            //显示文字刷新处理

            long c = now.getTime() - beginDate.getTime();

            c = c / 1000;
            long m = c / 60;
            long s = c - m * 60;
            String ms = "";
            String ss = "";
            if (m < 10) ms = "0" + Long.toString(m);
            else ms = Long.toString(m);
            if (s < 10) ss = "0" + Long.toString(s);
            else ss = Long.toString(s);
            String mString = this.mcontext.getString(R.string.recorder_alert_prompt_run) + " " + ms + ":" + ss;
            mPaint.setColor(Color.GRAY);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(28);
            //将图片和文字画入画板
            this.canvas.drawText(mString, 100, 100, mPaint);
            this.canvas.drawBitmap(bmp, 0, 10, null);
            bmp.recycle();
        } catch (Exception e){
//            e.printStackTrace();
        }
    }

    /**
     * 线程处理
     * 核心处理动态刷新内容的方法
     * 主要采用线程内循环,更新提示框内的动画图片和文字
     */
    @SuppressLint("WrongCall")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isneedstop) {
                Thread.currentThread().interrupt();
            }

            try {
                Thread.sleep(Button4RecorderView.step);

                //只要不是停止的状态,就应该根据动态情况更新画板
                //使用postInvalidate可以直接在线程中更新界面
                if (!isneedstop) {
                    this.onDraw(this.canvas);
                    //postInvalidate();如果只有此处的时候,则4.0可以.
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    /**
     * 以下部分为录音的控制部分
     */
    private boolean isneedstop = false; //是否需要停止的变量.true是需要停止线程并不进行画板刷新.
    private boolean isrun = false;  //是否需要进行
    private Date beginDate;//开始录音的时间
    private RecorderProcess rproc;

    /**
     * 停止录音
     */
    public void stop() {
    	if(isrun){
	        isneedstop = true;
	        String file = rproc.saveRecordFile();
	        ((IProcessRecorderFile) mcontext).onSaveAudioFile(file);
    	}
    }

    /**
     * 取消录音
     */
    public void cancel() {
    	if(isrun){
	        isneedstop = true;
	        rproc.cancelRecordFile();
    	}
    }

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
            	Toast.makeText((Activity)mcontext,  R.string.initfail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 处理监听
     *
     * @param dialogInterface
     * @param i
     */
    @SuppressWarnings("deprecation")
	@Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == AlertDialog.BUTTON1) {
            //确认完成的处理
            this.stop();
        } else if (i == AlertDialog.BUTTON2) {
            //确认取消的处理
            this.cancel();
        }
    }

    /**
     * 回退按钮和点击空白处的相应处理方法
     *
     * @param dialogInterface
     */
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        this.cancel();
    }
}
package com.cs.audio;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.cs.common.HttpConst;
import com.cs.common.utils.FileUtils;

import java.io.File;
import java.util.Date;

/**
 * COPYRIGHT (C) 2010 CHARMINGSOFT CORPRATION All Rights Reserved
 * User: huangbin_1
 * Date: 12-11-15
 * Time: 上午11:56
 * 用途: 录音处理类.
 */
public class RecorderProcess implements MediaRecorder.OnErrorListener {
    //录音对象
//    private static MediaRecorder recorder;
    //存储的文件名
    private static String file ="";
    private final static String TAG ="RecorderProcess";
    private AudioRecorder2Mp3Util util = null;

    /**
     * 初始化录音类
     */
    public RecorderProcess(){

    }

    //按钮按下的处理
    public boolean startRecord() {
        Log.i(TAG, "录音开始 ");
        boolean r = false;
        if (util == null) {
//            recorder = new MediaRecorder();
//            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            recorder.setOnErrorListener(this);

            Date beginDate = new Date();
            try {
                File localpath = new File(Environment.getExternalStorageDirectory()+ HttpConst.PATH + HttpConst.AUDIO_PATH);
                if(!localpath.exists()){
                	FileUtils.mkDir(Environment.getExternalStorageDirectory()+ HttpConst.PATH + HttpConst.AUDIO_PATH);
                }
                Long ts = new Long(beginDate.getTime());
                file= Environment.getExternalStorageDirectory()+ HttpConst.PATH + HttpConst.AUDIO_PATH +ts.toString();

                if (util == null) {
                    util = new AudioRecorder2Mp3Util(null,file+".amr",file+".mp3");
                }

                util.startRecording();
                r = true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                util = null;
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                util = null;
                return false;
            }
           
        }
        return r;
    }

    //保存录音的处理
    public String saveRecordFile() {
        Log.i(TAG,"录音开始 结束");
        if (null != util) {
            Log.i("CallRecorder", "RecordService::onDestroy calling recorder.release()");
//            recorder.stop();
//            recorder.reset();
//            recorder.release();
//            recorder = null;

            util.stopRecordingAndConvertFile();
//            Toast.makeText(this, "ok", 0).show();
            util.cleanFile(AudioRecorder2Mp3Util.RAW);
            // 如果要关闭可以
            util.close();
            util = null;
        }
        return file;
    }
    //取消录音的处理
    public boolean cancelRecordFile() {
        Log.i(TAG,"录音开始 取消keyup");
        boolean  r= false;
        if (null != util) {
            Log.i("CallRecorder", "RecordService::onDestroy calling recorder.release()");
//            recorder.stop();
//            recorder.reset();
//            recorder.release();
//            recorder = null;
            util.cleanFile(AudioRecorder2Mp3Util.RAW);
            util.close();
            util = null;
            File fileF = new File(file);
            fileF.delete();
            r = true;
        }
        return r;
    }

    @Override
    public void onError(MediaRecorder mediaRecorder, int i, int i1) {
        this.cancelRecordFile();
    }
}

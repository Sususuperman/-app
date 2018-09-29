package com.cs.voice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.cs.android.common.R;
import com.cs.common.utils.SharedUtils;
import com.cs.voice.setting.IatSettings;
import com.cs.voice.util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 语音听写
 * Created by XingDu on 2016/5/25.
 */
public class Dictation {
    private Context mContext;
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SharedPreferences mSharedPreferences;
    private Toast mToast;
    private int ret = 0; // 函数调用返回值
    public Dictation(Context context){
        this.mContext = context;
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }


    public void startUp(){
        mSharedPreferences = mContext.getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(mContext, "iat_recognize");
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(mContext.getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);

            mIatDialog.show();
            showTip(mContext.getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip(mContext.getString(R.string.text_dictation_fail) + ret);
            } else {
                showTip(mContext.getString(R.string.text_begin));
            }
        }
    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTip(mContext.getString(R.string.text_fail) + code);
            }
        }
    };


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip(mContext.getString(R.string.text_speak));
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip(mContext.getString(R.string.text_end_speak));
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results,isLast);
            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(mContext.getString(R.string.text_volume) + volume);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            }
        }
    };

    private void printResult(RecognizerResult results, boolean isLast) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        if(onResultTextListener != null && isLast)
            onResultTextListener.onResult(resultBuffer.toString());
    }

    private OnResultTextListener onResultTextListener;
    public void setOnResultTextListener(OnResultTextListener onResultTextListener) {
        this.onResultTextListener = onResultTextListener;
    }

    public  interface OnResultTextListener{
        void onResult(String text);
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
            public void onResult(RecognizerResult results, boolean isLast) {
                    printResult(results,isLast);
            }

            /**
             * 识别回调错误.
             */
            public void onError(SpeechError error) {
                showTip(error.getPlainDescription(true));
            }
    };


    private void showTip(String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            String voice= SharedUtils.getString(mContext,"voice" );
//            Toast.makeText(mContext,""+voice,Toast.LENGTH_LONG).show();
            if(voice!=null){
                if(voice.equals("普通话")){
                    // 设置语言区域
                    mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
                }else if(voice.equals("四川话")){
                    mIat.setParameter(SpeechConstant.ACCENT, "lmz");
                }else if(voice.equals("河南话")){
                    mIat.setParameter(SpeechConstant.ACCENT, "henanese");
                }else if(voice.equals("广东话")){
                    mIat.setParameter(SpeechConstant.ACCENT, "cantonese");
                }
                else{
                    // 设置语言区域
                    mIat.setParameter(SpeechConstant.ACCENT, lag);
                }
            }else{
                // 设置语言区域
                mIat.setParameter(SpeechConstant.ACCENT, lag);
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");

    }

    /**退出时释放连接*/
    public void close(){
        mIat.cancel();
        mIat.destroy();
    }

}

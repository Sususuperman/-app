package com.cs.common.view.xview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cs.common.utils.ViewUtils;
import com.cs.voice.Dictation;


/**
 * Created by weifei on 16/10/12.
 * 文本框输入语音输入文字界面
 */

public class VoiceSpeehEditText extends XImgEditText {
    private Dictation dictation;

    public VoiceSpeehEditText(Context context) {
        this(context, null);
    }

    public VoiceSpeehEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceSpeehEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initListener();
    }

    protected void initView() {
        this.dictation = new Dictation(getContext());
    }

    public void setVoiceImage(int drawableid){
        getXimageView().setImageResource(drawableid);
    }
    private void initListener() {
        setImageClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dictation.startUp();
            }
        });
        dictation.setOnResultTextListener(new Dictation.OnResultTextListener() {
            @Override
            public void onResult(String text) {
                getEditView().setText(getEditView().getText() + text);
                ViewUtils.setSelection(getEditView());
            }
        });
    }

    public void destory(){
        dictation.close();
    }
}

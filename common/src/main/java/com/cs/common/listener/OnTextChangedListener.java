package com.cs.common.listener;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class OnTextChangedListener implements TextWatcher,OnClickListener{
    private ImageView deleteImage;
    private EditText mEdittext;
    private TextView mTextView;
    static int num = 200;// 限制的最大字数

    public OnTextChangedListener(ImageView deleteImage,EditText mEdittext){
        this(deleteImage,mEdittext,null,num);
    }

    
    public OnTextChangedListener(EditText mEdittext,TextView textView){
        this(null,mEdittext,textView,num);
    }
    
    /**
     * 
     * @param mEdittext
     * @param textView 记录剩余字数
     * @param num 最大限制字数
     */
    private OnTextChangedListener(ImageView deleteImage,EditText mEdittext,TextView textView,int num){
        this.deleteImage = deleteImage;
        this.mEdittext = mEdittext;
        this.mTextView = textView;
        if(deleteImage != null)
            deleteImage.setOnClickListener(this);
        this.num = num;
        if(mTextView != null)
            this.mTextView.setText(num+"");
    }
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(deleteImage != null){
            if(mEdittext.getText()!=null && !mEdittext.getText().toString().equals("")){
                deleteImage.setVisibility(View.VISIBLE);
            }else{
                deleteImage.setVisibility(View.GONE);
            }
            if(onSearchTextChangedListener != null)
                onSearchTextChangedListener.OnSearchTextChanged(s.toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mTextView != null){
            Resources resources = mTextView.getContext().getResources();
            int number = num - s.length();
            mTextView.setText(number + "");
            if (number <= -1){
                int maths = Math.abs(number);
                mTextView.setText("" + maths);
            }
            if (number <= 0){
                mTextView.setTextColor(Color.RED);
            }else{
                mTextView.setTextColor(resources.getColor(android.R.color.secondary_text_dark));
            }
        }
    }
    @Override
    public void onClick(View v) {
        mEdittext.setText("");
        mEdittext.requestFocus();
    }
    
    private OnSearchTextChangedListener onSearchTextChangedListener;
    public interface  OnSearchTextChangedListener{
        public void OnSearchTextChanged(String text);
    }
    
    public OnSearchTextChangedListener getOnSearchTextChangedListener() {
        return onSearchTextChangedListener;
    }

    public void setOnSearchTextChangedListener(
        OnSearchTextChangedListener onSearchTextChangedListener) {
        this.onSearchTextChangedListener = onSearchTextChangedListener;
    }
    
}

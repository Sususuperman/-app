package com.cs.common.view.xview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.cs.android.common.R;


/**
 * Created by weifei on 16/10/9.
 */

public class XEditView extends EditText {
    public XEditView(Context context) {
        this(context,null);
    }

    public XEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setTextSize(16);
        setHintTextColor(ContextCompat.getColor(getContext(), R.color.material_text_color_black_hint));
        setTextColor(ContextCompat.getColor(getContext(), R.color.material_text_color_black_secondary_text));
    }

}

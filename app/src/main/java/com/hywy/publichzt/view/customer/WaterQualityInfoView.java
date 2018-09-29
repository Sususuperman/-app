package com.hywy.publichzt.view.customer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.common.utils.PhoneUtil;
import com.hywy.publichzt.R;

/**
 * @author Superman
 * @date 2018/6/26
 */

public class WaterQualityInfoView extends LinearLayout {
    private Context context;
    //    private int colorId;
    private TextView tv1, tv2;

    public WaterQualityInfoView(Context context) {
        super(context, null);
    }

    public WaterQualityInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public void setTextBacgound(int colorId) {
//        this.colorId = colorId;
        GradientDrawable drawable1 = (GradientDrawable) tv1.getBackground();
        GradientDrawable drawable2 = (GradientDrawable) tv2.getBackground();
        drawable1.setStroke(PhoneUtil.dp2px(context, 1), ContextCompat.getColor(context, colorId));

        drawable2.setColor(ContextCompat.getColor(context, colorId));
        drawable2.setStroke(PhoneUtil.dp2px(context, 1), ContextCompat.getColor(context, colorId));

        tv1.setTextColor(ContextCompat.getColor(context, colorId));
        tv1.setBackgroundDrawable(drawable1);
        tv2.setBackgroundDrawable(drawable2);
    }

    public void setTextView1(String text){
        tv1.setText(text);
    }

    public void setTextView2(String text){
        tv2.setText(text);
    }

    private void initView() {
        View view = View.inflate(context, R.layout.water_quality_layout, this);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
    }

}

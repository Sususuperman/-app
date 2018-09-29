package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.YuJing;

import butterknife.Bind;

/**
 * 预警详情
 *
 * @author Superman
 */
public class YujingDetailsActivity extends BaseToolbarActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_sendout)
    TextView tvSendout;
    @Bind(R.id.tv_event)
    TextView tvEvent;
    @Bind(R.id.content)
    TextView tvContent;
    private YuJing yuJing;


    public static void startAction(Activity activity, YuJing yuJing) {
        Intent intent = new Intent(activity, YujingDetailsActivity.class);
        intent.putExtra("yuJing", yuJing);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yujing_details);
        init();
        initData();
        initListeners();
    }


    private void initData() {
        setTextView(title, yuJing.getWARNTHEME());
        setTextView(tvUser, yuJing.getWARNPEOPLE());
        setTextView(tvTime, yuJing.getTIME());
        setTextView(tvEvent, yuJing.getWARNTYPEDES());
        setTextView(tvContent, yuJing.getWARNCONTENT());
        setTextView(tvSendout, yuJing.getWARNCITY());

    }


    private void setTextView(TextView textView, String str) {
        if (StringUtils.hasLength(str)) {
            textView.setText(str);
        }
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.yujing_detail)));
        yuJing = getIntent().getParcelableExtra("yuJing");
    }

    private void initListeners() {
    }

}

package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Notify;

import butterknife.Bind;

public class NotifyDetailsActivity extends BaseToolbarActivity {
    private Notify notify;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    public static void startAction(Context activity, Notify notify) {
        Intent intent = new Intent(activity, NotifyDetailsActivity.class);
        intent.putExtra("notify", notify);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_details);
        init();
    }

    private void init() {
        notify = getIntent().getParcelableExtra("notify");
        this.setTitleBulider(new BaseToolbarActivity.Bulider().title(notify.getINFO()).backicon(R.drawable.ic_arrow_back_white_24dp));
        if(StringUtils.hasLength(notify.getINFOCONTENT())){
            tvInfo.setText(notify.getINFOCONTENT());
        }
    }
}

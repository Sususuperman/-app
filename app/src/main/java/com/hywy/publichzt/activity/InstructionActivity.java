package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.cs.common.base.BaseToolbarActivity;
import com.hywy.publichzt.R;

import butterknife.Bind;

public class InstructionActivity extends BaseToolbarActivity {
    @Bind(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        init();
    }

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, InstructionActivity.class);
        activity.startActivity(intent);
    }

    private void init() {
        setTitleBulider(new Bulider().title("功能介绍").backicon(R.drawable.ic_arrow_back_white_24dp));
        webView.loadUrl("http://www.hengyuweiye.com/LuAnHZT/function/%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D.html");
    }
}

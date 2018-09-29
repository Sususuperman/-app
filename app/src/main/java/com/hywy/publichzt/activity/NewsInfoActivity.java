package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.News;

import butterknife.Bind;

public class NewsInfoActivity extends BaseToolbarActivity {
    @Bind(R.id.webview)
    WebView webView;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        init();
    }

    public static void startAction(Context activity, News news) {
        Intent intent = new Intent(activity, NewsInfoActivity.class);
        intent.putExtra("news", news);
        activity.startActivity(intent);
    }

    private void init() {
        news = getIntent().getParcelableExtra("news");
        if (StringUtils.hasLength(news.getNWES_NAME())) {
            setTitleBulider(new Bulider().title(news.getNWES_NAME()).backicon(R.drawable.ic_arrow_back_white_24dp));
        } else {
            setTitleBulider(new Bulider().title("详情").backicon(R.drawable.ic_arrow_back_white_24dp));
        }
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());
        if (StringUtils.hasLength(news.getHTML_URL())) {
            webView.loadUrl(news.getHTML_URL());
        }
    }
}

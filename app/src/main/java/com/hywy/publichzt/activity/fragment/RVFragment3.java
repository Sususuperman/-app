package com.hywy.publichzt.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cs.common.base.BaseFragment;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.RiverDetails;

import butterknife.Bind;


/**
 * 河道基本信息
 *
 * @author Superman
 */
public class RVFragment3 extends BaseFragment {
    @Bind(R.id.webview)
    WebView webView;
    private RiverDetails.YHYCListBean yhycListBean;

    public static RVFragment3 newInstance(RiverDetails.YHYCListBean yhycListBean) {
        Bundle args = new Bundle();
        args.putParcelable("yhycListBean", yhycListBean);
        RVFragment3 fragment = new RVFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yhyc;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            yhycListBean = getArguments().getParcelable("yhycListBean");
        }
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setSupportZoom(true);
        webView.loadUrl(yhycListBean.getHTML_Url());

        webView.setWebViewClient(new WebViewClient());
//        if (yhycListBean != null) {
//            if (StringUtils.hasLength(yhycListBean.getHTML_Url())) {
//                webView.loadUrl(yhycListBean.getHTML_Url());
//            }
//        }
        Log.i("htmlurl", yhycListBean.getHTML_Url());
    }

}

package com.hywy.publichzt.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.base.BaseFragment;
import com.cs.common.handler.WaitDialog;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.AboutAppActivity;
import com.hywy.publichzt.activity.AllXunchaActivity;
import com.hywy.publichzt.activity.EventSuperviseActivity;
import com.hywy.publichzt.activity.FeedBackActivity;
import com.hywy.publichzt.activity.InstructionActivity;
import com.hywy.publichzt.activity.PersonInfoActivity;
import com.hywy.publichzt.activity.ProblemReportListActivity;
import com.hywy.publichzt.activity.YuJingListActivity;
import com.hywy.publichzt.adapter.RiverFragmentAdapter;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Account;
import com.hywy.publichzt.entity.AppMenu;
import com.hywy.publichzt.utils.StartIntent;
import com.hywy.publichzt.view.NoScrollViewpPager;
import com.hywy.publichzt.view.customer.MoreItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by weifei on 17/1/19.
 */

public class ChildFragment3 extends BaseFragment {
    private List<Fragment> list;
    private RiverFragmentAdapter adapter;

    @Bind(R.id.viewpager)
    NoScrollViewpPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    private String[] titles = new String[]{"最新投诉", "投诉地图"};

    public static ChildFragment3 newInstance(String title) {
        Bundle args = new Bundle();
        ChildFragment3 fragment = new ChildFragment3();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_3;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        list.add(ComplainFragment.newInstance(0));
        list.add(ComplainMapFragment.newInstance(null));

        adapter = new RiverFragmentAdapter(getChildFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).select();//设置默认选中项
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }
}

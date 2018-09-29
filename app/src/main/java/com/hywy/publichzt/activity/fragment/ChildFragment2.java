package com.hywy.publichzt.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cs.common.base.BaseFragment;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.RiverFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by weifei on 17/1/19.
 */

public class ChildFragment2 extends BaseFragment {
    private List<Fragment> list;
    private RiverFragmentAdapter adapter;

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private String[] titles = new String[]{"新闻动态", "通知公告"};

    public static ChildFragment2 newInstance() {
        Bundle args = new Bundle();
        ChildFragment2 fragment = new ChildFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main2;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        list.add(NewsFragment.newInstance());
        list.add(NotifyFragment.newInstance());
        //使用getChildFragmentManager()来替代getFragmentManager()获取FragmentManager
        adapter = new RiverFragmentAdapter(getChildFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).select();//设置默认选中项
    }


    private void initListeners() {

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initListeners();
    }

}
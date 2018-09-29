package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cs.common.base.BaseToolbarActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.fragment.EventListFragment;
import com.hywy.publichzt.adapter.RiverFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 事件督办
 **/
public class EventSuperviseActivity extends BaseToolbarActivity {
    private List<Fragment> list;
    private RiverFragmentAdapter adapter;

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private String[] titles = new String[]{"待办事务", "在办事务", "办结事务"};

    public static void startAction(Context context) {
        Intent intent = new Intent(context, EventSuperviseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_monitor);
        init();
        initListeners();
    }


    private void init() {
        this.setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.event_list)));
        list = new ArrayList<>();
        list.add(EventListFragment.newInstance(0));//待办
        list.add(EventListFragment.newInstance(2));//在办
        list.add(EventListFragment.newInstance(1));//办结

        adapter = new RiverFragmentAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).select();//设置默认选中项


    }

    private void initListeners() {

    }

}

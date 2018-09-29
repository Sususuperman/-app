package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.DialogTools;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.fragment.ReservoirFragment;
import com.hywy.publichzt.activity.fragment.WaterAndRainFragment;
import com.hywy.publichzt.activity.fragment.WaterQualityFragment;
import com.hywy.publichzt.adapter.RiverFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RiverMonitorActivity extends BaseToolbarActivity {
    private List<Fragment> list;
    private RiverFragmentAdapter adapter;

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private String[] titles = new String[]{"水雨情监测", "水库水文监测", "水质监测"};

    public static void startAction(Context context) {
        Intent intent = new Intent(context, RiverMonitorActivity.class);
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
        this.setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.river_monitor)));
        list = new ArrayList<>();
        list.add(WaterAndRainFragment.newInstance());
        list.add(ReservoirFragment.newInstance(0));
        list.add(WaterQualityFragment.newInstance());

        adapter = new RiverFragmentAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        initTabItem();//自定义tabitem选项卡
        tabLayout.getTabAt(0).select();//设置默认选中项


    }

    private void initTabItem() {
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.tab_item);//给每一个tab设置view
//            if (i == 0) {
//                // 设置第一个tab的TextView是被选择的样式
//                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
//            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            ImageView downView = (ImageView) tab.getCustomView().findViewById(R.id.down_view);
            textView.setText(titles[i]);//设置tab上的文字
            //第二个tab显示箭头按钮
            if (i == 1) {
                downView.setVisibility(View.VISIBLE);
            } else {
                downView.setVisibility(View.GONE);
            }
        }
    }

    private void initListeners() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //第二个按钮再次点击的时候处理箭头展示事件
                if (tab.getPosition() == 1) {
                    showListDialog();
                }
            }
        });
    }

    private void showListDialog() {
        final List<String> strs = new ArrayList<>();
        strs.add("水库水文监测");
        strs.add("河道水文监测");
        DialogTools.showListViewDialog(this, "提示", strs, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
                textView.setText(strs.get(i));

                mRxManager.post(RxAction.ACTION_CHOOSE_RIVER_TYPE, i);
//                //同时刷新adapter
//                RiverCourseFragment riverCourseFragment = RiverCourseFragment.newInstance();
//                list.set(1, riverCourseFragment);
//                adapter.setList(list);
//                viewPager.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

            }
        });
    }
}

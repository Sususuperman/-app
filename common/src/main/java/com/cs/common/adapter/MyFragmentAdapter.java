package com.cs.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *复查任务adapter
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    public MyFragmentAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles){
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
    }
    public MyFragmentAdapter(FragmentManager manager, List<Fragment> fragments){
        super(manager);
        this.fragments = fragments;
    }

    public void add(Fragment fragment){
        fragments.add(fragment);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.size() > 0 ? titles.get(position):"";
    }
}

package com.cs.common.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by weifei on 16/5/16.
 */
public class BaseListFlexAdapter<T extends AbstractFlexibleItem> extends FlexibleAdapter<T> {
    private int count = 20;
    private int page = 1;
    private boolean isClear;
    protected Context mContext;
    private boolean isNomore = false;
    public BaseListFlexAdapter(Context activity) {
        super(new ArrayList(),activity);
        this.mContext = activity;
    }

    public void clear(){
        selectAll();
        removeAllSelectedItems();
        notifyDataSetChanged();
    }
    /**
     * 获取所有的item
     * @return
     */
    public List<T> getAllItems(){
        int count = getItemCount();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T item = getItem(i);
            list.add(item);
        }
        return list;
    }

    public boolean addItem(@NonNull T item) {
        int size = getItemCount();
        return super.addItem(size, item);
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    @Override
    public List<Animator> getAnimators(View itemView, int position, boolean isSelected) {
        List<Animator> animators = new ArrayList<Animator>();
        addSlideInFromBottomAnimator(animators,itemView);
        return animators;
    }

    public boolean isNomore() {
        return isNomore;
    }

    public void setNomore(boolean nomore) {
        isNomore = nomore;
    }

    public boolean addItems(@NonNull T item) {
        return super.addItem(0, item);
    }


}

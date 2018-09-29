package com.cs.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配器
 * Created by charmingsoft on 2014/12/31.
 */
public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> mList = new ArrayList<T>();
    protected Context mContext;
    public BaseListAdapter(Context context){
        this.mContext = context;
    }

    public void remove(int index){
    	mList.remove(index);
    }
    
    public void remove(T obj){
    	mList.remove(obj);
    }
    

    public void clear(){
        this.mList.clear();
    }


    public void add(T o){
        this.mList.add(o);
    }

    public void adds(List<T> list){
        if(StringUtils.isNotNullList(list)){
            int size = list.size();
            for (int i =0; i < size ;i++){
                mList.add(list.get(i));
            }
//            mList.addAll(list);
        }
        notifyDataSetChanged();
    }


    public void add(int index,T o){
        this.mList.add(index, o);
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ?0:mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(LayoutInflater.from(mContext), convertView, parent, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent,int position);



}

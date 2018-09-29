package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hywy.publichzt.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by weifei on 17/1/13.
 */

public class MapClassifyHeaderItem extends AbstractHeaderItem<MapClassifyHeaderItem.HeaderViewHolder> implements IFilterable {
    private String title;

    public MapClassifyHeaderItem(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_map_classify_header;
    }

    @Override
    public HeaderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindViewHolder(FlexibleAdapter adapter, HeaderViewHolder holder, int position, List payloads) {
        holder.mTitle.setText(title);
    }

    @Override
    public boolean filter(String constraint) {
        return title != null && title.toLowerCase().trim().contains(constraint);
    }

    static class HeaderViewHolder extends FlexibleViewHolder {
        public TextView mTitle;

        public HeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true);//True for sticky
            mTitle = (TextView) view.findViewById(R.id.title);
        }
    }
}

package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.RiverDetailsActivity;
import com.hywy.publichzt.activity.VideoInfoActivity;
import com.hywy.publichzt.adapter.VideoGridAdapter;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.view.GridViewForScrollView;

import java.text.DecimalFormat;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 河道列表item
 *
 * @author Superman
 */

public class RiverItem extends AbstractFlexibleItem<RiverItem.EntityViewHolder> implements ISectionable<RiverItem.EntityViewHolder, MapClassifyHeaderItem>, IFilterable {
    private River river;
    private MapClassifyHeaderItem headerItem;
    private VideoGridAdapter mAdapter;

    public River getData() {
        return river;
    }

    public RiverItem(River river) {
        this.river = river;
    }

    public RiverItem(River river, MapClassifyHeaderItem header) {
        this.river = river;
        this.headerItem = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_river;
    }

    @Override
    public RiverItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new RiverItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public boolean filter(String constraint) {
        return false;
    }

    @Override
    public MapClassifyHeaderItem getHeader() {
        return headerItem;
    }

    @Override
    public void setHeader(MapClassifyHeaderItem header) {
        this.headerItem = header;
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        GridViewForScrollView gridview;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            gridview = (GridViewForScrollView) view.findViewById(R.id.gridview);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        mAdapter = new VideoGridAdapter(holder.gridview.getContext());
        if (river != null && river.getChild() != null) {
            mAdapter.setList(river.getChild());
            holder.gridview.setAdapter(mAdapter);
            holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    RiverDetailsActivity.startAction(holder.gridview.getContext(), river.getChild().get(i));
                }
            });

        }
    }


    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof RiverItem) {
            RiverItem odata = (RiverItem) o;
            return river.getREACH_CODE().equals(odata.getData().getREACH_CODE());
        }
        return false;
    }
}

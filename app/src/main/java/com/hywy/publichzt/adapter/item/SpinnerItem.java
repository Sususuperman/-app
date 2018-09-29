package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.River;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * @author Superman
 */
public class SpinnerItem extends AbstractFlexibleItem<SpinnerItem.EntityViewHolder> {
    private River name;

    public SpinnerItem(River name) {
        this.name = name;
    }

    public River getData() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_spinner;
    }

    @Override
    public EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        SpinnerItem.EntityViewHolder viewHolder = new EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
        return viewHolder;
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView name;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            name = (TextView) view.findViewById(R.id.address);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SpinnerItem.EntityViewHolder holder, int position, List payloads) {
        holder.name.setText(name.getRV_NAME());
    }

}

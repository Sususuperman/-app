package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Plan;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 河道整治工作进展情况item
 *
 * @author Superman
 */

public class OptionsItem extends AbstractFlexibleItem<OptionsItem.EntityViewHolder> {
    private Plan.OPTIONSBean optionsBean;
    private int status;
    private boolean clicked = true;

    public Plan.OPTIONSBean getData() {
        return optionsBean;
    }

    public OptionsItem(Plan.OPTIONSBean optionsBean) {
        this.optionsBean = optionsBean;
    }

    public OptionsItem(Plan.OPTIONSBean optionsBean, boolean clicked) {
        this.optionsBean = optionsBean;
        this.clicked = clicked;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_options;
    }

    @Override
    public OptionsItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new OptionsItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView name;
        ToggleButton aSwitch;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            name = (TextView) view.findViewById(R.id.name);
            aSwitch = (ToggleButton) view.findViewById(R.id.switchview);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (optionsBean != null) {
            if (StringUtils.hasLength(optionsBean.getOPTIONS_NAME())) {
                holder.name.setText(optionsBean.getOPTIONS_NAME());
            }
            if (!clicked) {
                holder.aSwitch.setClickable(false);
            }else {
                holder.aSwitch.setClickable(true);
            }
            holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        optionsBean.setStatus(1);
                    } else {
                        optionsBean.setStatus(0);
                    }
                }
            });
        }
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
//        if (o instanceof WaterAndRainItem) {
//            WaterAndRainItem odata = (WaterAndRainItem) o;
//            return waterRain.getOrganID() == odata.getData().getOrganID();
//        }

        return false;
    }
}

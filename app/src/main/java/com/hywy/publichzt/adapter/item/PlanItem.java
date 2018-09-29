package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Plan;

import java.text.DecimalFormat;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 *移动巡查~巡查计划item
 *
 * @author Superman
 */

public class PlanItem extends AbstractFlexibleItem<PlanItem.EntityViewHolder> implements ISectionable<PlanItem.EntityViewHolder, MapClassifyHeaderItem>, IFilterable {
    private Plan plan;
    private MapClassifyHeaderItem headerItem;

    public Plan getData() {
        return plan;
    }

    public PlanItem(Plan plan) {
        this.plan = plan;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_river;
    }

    @Override
    public PlanItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new PlanItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
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
        TextView tv_name;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_name = (TextView) view.findViewById(R.id.name);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (plan != null) {
            if (StringUtils.hasLength(plan.getPATROL_PLAN_NAME())) {
                holder.tv_name.setText(plan.getPATROL_PLAN_NAME());
            }
            }

//            if (reservoir.getSTLC() != null) {
//                holder.tv_address_r.setText(reservoir.getSTLC());
//            }
//            holder.tv_water_r.setText(getTwoDecimal(reservoir.getRZ()) + "m");
//            holder.tv_stream_r.setText(getTwoDecimal(reservoir.getOTQ()) + "m³/s");
//            holder.tv_time_r.setText(reservoir.getTM());

    }

    /**
     * 将数据保留两位小数
     */
    private String getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#########0.00");
        String str = dFormat.format(num);
        return str;
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PlanItem) {
            PlanItem odata = (PlanItem) o;
            return plan.getPLAN_ID().equals(odata.getData().getPLAN_ID());
        }
        return false;
    }
}

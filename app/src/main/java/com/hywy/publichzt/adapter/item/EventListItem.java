package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.EventSupervise;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 事件督办列表item
 *
 * @author Superman
 */

public class EventListItem extends AbstractFlexibleItem<EventListItem.EntityViewHolder> {
    private EventSupervise eventSupervise;
    private int type;

    public EventSupervise getData() {
        return eventSupervise;
    }

    public EventListItem(EventSupervise eventSupervise, int type) {
        this.eventSupervise = eventSupervise;
        this.type = type;
    }

    public EventListItem(EventSupervise eventSupervise) {
        this.eventSupervise = eventSupervise;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_event_list;
    }

    @Override
    public EventListItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new EventListItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title_r;
        TextView tv_reason_r;
        TextView tv_time_r;
        ImageView iv_status_r;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title_r = (TextView) view.findViewById(R.id.tv_title_r);
            tv_reason_r = (TextView) view.findViewById(R.id.tv_reason_r);
            tv_time_r = (TextView) view.findViewById(R.id.tv_time_r);
            iv_status_r = (ImageView) view.findViewById(R.id.iv_status_r);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (eventSupervise != null) {
            if (StringUtils.hasLength(eventSupervise.getEVENT_NAME())) {
                holder.tv_title_r.setText(eventSupervise.getEVENT_NAME());
            }
            if (eventSupervise.getEVENT_CONT() != null) {
                holder.tv_reason_r.setText(eventSupervise.getEVENT_CONT());
            }
            holder.tv_time_r.setText(eventSupervise.getSTARTTIME());
            if (eventSupervise.getSTATE().equals("1")) {//state是1表示已办结，不是1就接着判断deal_state判断已处理和未处理
                holder.iv_status_r.setImageResource(R.drawable.shiwu_yibanjie);
            } else {
                if (eventSupervise.getDEAL_STATE().equals("1")) {
                    holder.iv_status_r.setImageResource(R.drawable.shiwu_chulizhong);
                } else {
                    holder.iv_status_r.setImageResource(R.drawable.shiwu_weichuli);
                }
            }
//            switch (type) {
//                case 0:
//                    holder.iv_status_r.setImageResource(R.drawable.ic_event_todo);
//                    break;
//                case 1:
//                    holder.iv_status_r.setImageResource(R.drawable.icon_event_end);
//                    break;
//                case 2:
//                    holder.iv_status_r.setImageResource(R.drawable.ic_event_doing);
//                    break;
//            }

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

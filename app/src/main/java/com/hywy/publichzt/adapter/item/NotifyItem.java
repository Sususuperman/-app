package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Notify;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * 通知公告item
 *
 * @author Superman
 */
public class NotifyItem extends AbstractFlexibleItem<NotifyItem.EntityViewHolder> {
    private Notify notify;

    public NotifyItem(Notify notify) {
        this.notify = notify;
    }

    public Notify getData() {
        return notify;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_notify_layout;
    }

    @Override
    public EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        NotifyItem.EntityViewHolder viewHolder = new EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
        return viewHolder;
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView title, name, content, time, type;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.title);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            type = (TextView) view.findViewById(R.id.type);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, NotifyItem.EntityViewHolder holder, int position, List payloads) {
        holder.title.setText(notify.getINFO());
        holder.name.setText(notify.getINFOMAN());
        holder.time.setText(notify.getTM());
        holder.content.setText(notify.getINFOCONTENT());
        holder.type.setText(notify.getINFOTYPE());
    }

}

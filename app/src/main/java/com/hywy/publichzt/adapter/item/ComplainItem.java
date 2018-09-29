package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.DateUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.entity.Notify;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * 投诉item
 *
 * @author Superman
 */
public class ComplainItem extends AbstractFlexibleItem<ComplainItem.EntityViewHolder> {
    private Complain complain;

    public ComplainItem(Complain complain) {
        this.complain = complain;
    }

    public Complain getData() {
        return complain;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_complain_layout;
    }

    @Override
    public EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        ComplainItem.EntityViewHolder viewHolder = new EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
        return viewHolder;
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView title, name, content, time, code;
        ImageView status;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.title);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            status = (ImageView) view.findViewById(R.id.status);
            code = (TextView) view.findViewById(R.id.code);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ComplainItem.EntityViewHolder holder, int position, List payloads) {
        holder.title.setText(complain.getREACH_NAME());
        holder.name.setText(complain.getCOMPLAINTXT());
        holder.time.setText(complain.getCOMPLAINTIME());
        holder.content.setText(complain.getADNM());
        if (complain.getCOMZT().equals("0")) {
            holder.status.setImageResource(R.drawable.icon_complain_weichuli);
        } else
            holder.status.setImageResource(R.drawable.icon_complain_yichuli);
        holder.code.setText(complain.getCOMID());
    }


}

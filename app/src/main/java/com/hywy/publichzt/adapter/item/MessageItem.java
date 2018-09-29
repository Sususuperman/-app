package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Message;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 留言簿列表item
 *
 * @author Superman
 */

public class MessageItem extends AbstractFlexibleItem<MessageItem.EntityViewHolder> {
    private Message message;

    public Message getData() {
        return message;
    }

    public MessageItem(Message message) {
        this.message = message;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_message;
    }

    @Override
    public MessageItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new MessageItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_name;
        TextView tv_time;
        ImageView iv_head;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_name = (TextView) view.findViewById(R.id.name);
            tv_time = (TextView) view.findViewById(R.id.time);
            iv_head = (ImageView) view.findViewById(R.id.iv_head);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (message != null) {
            if (StringUtils.hasLength(message.getPER_NAME())) {
                holder.tv_name.setText("来自" +
                        message.getPER_NAME() +
                        "的留言");
            }
            if (StringUtils.hasLength(message.getTM())) {
                holder.tv_time.setText(message.getTM());
            }
        }
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MessageItem) {
            MessageItem odata = (MessageItem) o;
            return message.getID().equals(odata.getData().getID());
        }
        return false;
    }
}

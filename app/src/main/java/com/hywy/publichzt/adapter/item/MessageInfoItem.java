package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Account;
import com.hywy.publichzt.entity.Message;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 留言簿详情列表item
 *
 * @author Superman
 */

public class MessageInfoItem extends AbstractFlexibleItem<MessageInfoItem.EntityViewHolder> {
    private Message message;
    private Account account;

    public Message getData() {
        return message;
    }

    public MessageInfoItem(Message message) {
        this.message = message;
        account = App.getInstance().getAccount();
    }

    @Override
    public int getLayoutRes() {
        if (account.getUserId().equals(message.getUSER_ID())) {
            return R.layout.item_message_info;
        } else return R.layout.item_message_info2;
    }

    @Override
    public MessageInfoItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new MessageInfoItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title, tv_content;
        TextView tv_name;
        TextView tv_time;
//        ImageView iv_head;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title = (TextView) view.findViewById(R.id.title);
            tv_name = (TextView) view.findViewById(R.id.name);
            tv_time = (TextView) view.findViewById(R.id.time);
            tv_content = (TextView) view.findViewById(R.id.content);
//            iv_head = (ImageView) view.findViewById(R.id.iv_head);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (message != null) {
            if (StringUtils.hasLength(message.getUSER_NAME())) {
                holder.tv_title.setText(message.getUSER_NAME());
            }
            if (StringUtils.hasLength(message.getPER_NAME())) {
                holder.tv_name.setText("发送至：" +
                        message.getPER_NAME()
                );
            }
            if (StringUtils.hasLength(message.getTM())) {
                holder.tv_time.setText(message.getTM());
            }
            if (StringUtils.hasLength(message.getCONT())) {
                holder.tv_content.setText(message.getCONT());
            }
        }
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MessageInfoItem) {
            MessageInfoItem odata = (MessageInfoItem) o;
            return message.getID().equals(odata.getData().getID());
        }
        return false;
    }
}

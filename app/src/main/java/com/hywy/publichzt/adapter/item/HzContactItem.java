package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.HzContact;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 河长办通讯录item
 *
 * @author Superman
 */

public class HzContactItem extends AbstractFlexibleItem<HzContactItem.EntityViewHolder> implements ISectionable<HzContactItem.EntityViewHolder, MapClassifyHeaderItem>, IFilterable {
    private HzContact.DealsBean contact;
    private MapClassifyHeaderItem headerItem;

    public HzContact.DealsBean getData() {
        return contact;
    }

    public HzContactItem(HzContact.DealsBean contact) {
        this.contact = contact;
    }

    public HzContactItem(HzContact.DealsBean contact, MapClassifyHeaderItem header) {
        this.contact = contact;
        this.headerItem = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_contact;
    }

    @Override
    public HzContactItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new HzContactItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
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
        TextView role_name;
        ImageView iv_head;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_name = (TextView) view.findViewById(R.id.name);
            role_name = (TextView) view.findViewById(R.id.tv_role);
            iv_head = (ImageView) view.findViewById(R.id.iv_head);

        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (contact != null) {
            if (StringUtils.hasLength(contact.getPER_NAME())) {
                holder.tv_name.setText(contact.getPER_NAME());
            }
            if (StringUtils.hasLength(contact.getPER_D())) {
                holder.role_name.setText(contact.getPER_D());
            }
            if (StringUtils.hasLength(contact.getIMAGE_URL())) {
                ImageLoaderUtils.display(holder.iv_head.getContext(), holder.iv_head, contact.getIMAGE_URL());
            }
        }
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof HzContactItem) {
            HzContactItem odata = (HzContactItem) o;
            return contact.getUSER_ID().equals(odata.getData().getUSER_ID());
        }
        return false;
    }
}

package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Contact;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 通讯录item
 *
 * @author Superman
 */

public class ContactItem extends AbstractFlexibleItem<ContactItem.EntityViewHolder> implements ISectionable<ContactItem.EntityViewHolder, MapClassifyHeaderItem>, IFilterable {
    private Contact.DealsBean contact;
    private MapClassifyHeaderItem headerItem;

    public Contact.DealsBean getData() {
        return contact;
    }

    public ContactItem(Contact.DealsBean contact) {
        this.contact = contact;
    }

    public ContactItem(Contact.DealsBean contact, MapClassifyHeaderItem header) {
        this.contact = contact;
        this.headerItem = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_contact;
    }

    @Override
    public ContactItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ContactItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
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
            if (StringUtils.hasLength(contact.getNAME())) {
                holder.tv_name.setText(contact.getNAME());
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
        if (o instanceof ContactItem) {
            ContactItem odata = (ContactItem) o;
            return contact.getUSER_ID().equals(odata.getData().getUSER_ID());
        }
        return false;
    }
}

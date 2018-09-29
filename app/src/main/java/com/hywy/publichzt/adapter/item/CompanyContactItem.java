package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.CompanyContact;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 协作单位通讯录item
 *
 * @author Superman
 */

public class CompanyContactItem extends AbstractFlexibleItem<CompanyContactItem.EntityViewHolder> implements ISectionable<CompanyContactItem.EntityViewHolder, MapClassifyHeaderItem>, IFilterable {
    private CompanyContact.DealsBean contact;
    private MapClassifyHeaderItem headerItem;

    public CompanyContact.DealsBean getData() {
        return contact;
    }

    public CompanyContactItem(CompanyContact.DealsBean contact) {
        this.contact = contact;
    }

    public CompanyContactItem(CompanyContact.DealsBean contact, MapClassifyHeaderItem header) {
        this.contact = contact;
        this.headerItem = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_contact;
    }

    @Override
    public CompanyContactItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new CompanyContactItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
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
            if (StringUtils.hasLength(contact.getDUTY())) {
                holder.role_name.setText(contact.getDUTY());
            }
//            if (StringUtils.hasLength(contact.getIMAGE_URL())) {
//                ImageLoaderUtils.display(holder.iv_head.getContext(), holder.iv_head, contact.getIMAGE_URL());
//            }
        }
    }

    /**
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CompanyContactItem) {
            CompanyContactItem odata = (CompanyContactItem) o;
            return contact.getID()==(odata.getData().getID());
        }
        return false;
    }
}

package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.ImageLoaderUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.News;
import com.hywy.publichzt.entity.Notify;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * 新闻item
 *
 * @author Superman
 */
public class NewsItem extends AbstractFlexibleItem<NewsItem.EntityViewHolder> {
    private News news;

    public NewsItem(News news) {
        this.news = news;
    }

    public News getData() {
        return news;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_news_layout;
    }

    @Override
    public EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        NewsItem.EntityViewHolder viewHolder = new EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
        return viewHolder;
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView title, name, content, time, type;
        ImageView image;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.title);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            image = (ImageView) view.findViewById(R.id.img);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, NewsItem.EntityViewHolder holder, int position, List payloads) {
        holder.title.setText(news.getNWES_NAME());
        holder.name.setText(news.getTM());
        holder.content.setText(news.getCONTENT());
        ImageLoaderUtils.display(holder.image.getContext(), holder.image, news.getIMG_URL());
    }

}

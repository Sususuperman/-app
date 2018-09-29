package com.hywy.publichzt.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.android.task.Task;
import com.cs.common.baserx.RxManager;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.NewsInfoActivity;
import com.hywy.publichzt.activity.NotifyDetailsActivity;
import com.hywy.publichzt.adapter.item.NewsItem;
import com.hywy.publichzt.adapter.item.NotifyItem;
import com.hywy.publichzt.entity.News;
import com.hywy.publichzt.entity.Notify;
import com.hywy.publichzt.task.GetNewsListTask;
import com.hywy.publichzt.task.GetNotifyListTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 新闻fragment
 */

public class NewsFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    public RxManager rxManager;
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;
    private int type;

    public static NewsFragment newInstance() {

        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notify, null);
        swipeRefresh = (SwipeRefreshview) view.findViewById(R.id.swipeRefresh);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        rxManager = new RxManager();
        init();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化数据
     */
    private void init() {
        swipeRefresh.setMode(SwipeRefreshview.Mode.BOTH);
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().setCount(10000);
        swipeRefresh.getAdapter().initializeListeners(this);

        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = null;
        task = new GetNewsListTask(getActivity());

        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onItemClick(int position) {
        NewsItem item = (NewsItem) swipeRefresh.getAdapter().getItem(position);
        News news = item.getData();
        NewsInfoActivity.startAction(getActivity(), news);
        return false;
    }

}

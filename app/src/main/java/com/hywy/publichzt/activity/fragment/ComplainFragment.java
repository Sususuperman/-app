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
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.ComplainInfoActivity;
import com.hywy.publichzt.activity.EventDetailsActivity;
import com.hywy.publichzt.adapter.item.ComplainItem;
import com.hywy.publichzt.adapter.item.EventListItem;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.task.GetComplainListTask;
import com.hywy.publichzt.task.GetEventListTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import rx.functions.Action1;

/**
 * 投诉列表fragment
 */

public class ComplainFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    public RxManager rxManager;
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;
    private int type;

    public static ComplainFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        ComplainFragment fragment = new ComplainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, null);
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
        initData();
        createRecordListener();
    }

    /**
     * 初始化数据
     */
    private void init() {
        swipeRefresh.setMode(SwipeRefreshview.Mode.BOTH);
        swipeRefresh.getAdapter().setCount(10000);
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        params.put("ddd", type);
        Task task = new GetComplainListTask(getActivity());
        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onItemClick(int position) {
        ComplainItem complainItem = (ComplainItem) swipeRefresh.getAdapter().getItem(position);
        Complain complain = complainItem.getData();
        ComplainInfoActivity.startAction(getActivity(), complain);
        return false;
    }

    private void createRecordListener() {
        rxManager.on(RxAction.ACTION_CREATE_COMPLAIN, new Action1<String>() {
            @Override
            public void call(String s) {
                initData();
            }
        });
    }
}

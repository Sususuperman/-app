package com.hywy.publichzt.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.baserx.RxManager;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.SiteDetailsActivity;
import com.hywy.publichzt.adapter.item.ReservoirItem;
import com.hywy.publichzt.adapter.item.RiverCourseItem;
import com.hywy.publichzt.task.GetReservoirTask;
import com.hywy.publichzt.task.GetRiverCourseTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import rx.functions.Action1;

/**
 * 水库水位监测
 */

public class ReservoirFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    TextView emptyText;
    LinearLayout emptyView;
    public RxManager rxManager;
    private int type;//用于表示是河道水文还是水库水文 0 水库  1 河道
    private int current_position;//记录点击的item
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;

    public static ReservoirFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ReservoirFragment fragment = new ReservoirFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservoir, null);
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
        initData(type);
        createSelectListener();
    }

    /**
     * 初始化数据
     */
    private void init() {
        swipeRefresh.setMode(SwipeRefreshview.Mode.BOTH);
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    public void initData(int type) {
        Map<String, Object> params = new HashMap<>();
        Task task;
        if (type == 0) {
            task = new GetReservoirTask(getActivity());
        } else {
            task = new GetRiverCourseTask(getActivity());
        }
        swipeRefresh.getAdapter().clear();
        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onItemClick(int position) {
        current_position = position;
        if (type == 0) {
            ReservoirItem item = (ReservoirItem) swipeRefresh.getAdapter().getItem(position);
            SiteDetailsActivity.startAction(getActivity(), item.getData());
        } else {
            RiverCourseItem item = (RiverCourseItem) swipeRefresh.getAdapter().getItem(position);
            SiteDetailsActivity.startAction(getActivity(), item.getData());
        }
        return false;
    }

    private void createSelectListener() {
        rxManager.on(RxAction.ACTION_CHOOSE_RIVER_TYPE, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                type = integer.intValue();
                initData(integer.intValue());
            }

        });

    }
}

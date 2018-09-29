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
import com.cs.common.utils.DateUtils;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.SiteDetailsActivity;
import com.hywy.publichzt.adapter.item.WaterAndRainItem;
import com.hywy.publichzt.task.GetWaterAndRainTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 水雨情监测
 */

public class WaterAndRainFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    TextView emptyText;
    LinearLayout emptyView;
    public RxManager rxManager;
    private int type;
    private int current_position;//记录点击的item
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;
    private TextView textView;

    public static WaterAndRainFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putInt("type", type);
        WaterAndRainFragment fragment = new WaterAndRainFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_rain, null);
        swipeRefresh = (SwipeRefreshview) view.findViewById(R.id.swipeRefresh);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        textView = (TextView) view.findViewById(R.id.textview);
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
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        textView.setText("注：日降雨量为 " + DateUtils.GetPevTimeChinesne("yyyy年MM月dd日HH时") + "—" + DateUtils.GetNowTimeChinesne("yyyy年MM月dd日HH时"));

    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetWaterAndRainTask(getActivity());
        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onItemClick(int position) {
        WaterAndRainItem waterAndRainItem = (WaterAndRainItem) swipeRefresh.getAdapter().getItem(position);
        SiteDetailsActivity.startAction(getActivity(), waterAndRainItem.getData());
        return false;
    }

    private void createRecordListener() {
    }
}

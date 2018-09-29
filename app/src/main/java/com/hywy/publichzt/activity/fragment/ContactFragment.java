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
import com.cs.common.utils.StringUtils;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.ContactDetailsActivity;
import com.hywy.publichzt.adapter.item.CompanyContactItem;
import com.hywy.publichzt.adapter.item.ContactItem;
import com.hywy.publichzt.adapter.item.HzContactItem;
import com.hywy.publichzt.task.GetCompanyContactListTask;
import com.hywy.publichzt.task.GetContactListTask;
import com.hywy.publichzt.task.GetHzContactListTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import rx.functions.Action1;

/**
 * 通讯录列表fragment
 */

public class ContactFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    public RxManager rxManager;
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;
    private int type;
    public static final int CONTACT_HEZHANG_LIST = 0;//河长
    public static final int CONTACT_HEZHANGBAN_LIST = 1;//河长办
    public static final int CONTACT_XIEZUODANWEI_LIST = 2;//协作单位

    private Task task;

    public static ContactFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        ContactFragment fragment = new ContactFragment();
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
        initData("");
        createSearchListener();
    }

    /**
     * 初始化数据
     */
    private void init() {
        swipeRefresh.setMode(SwipeRefreshview.Mode.DISABLED);
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }

        if (type == 0) {
            task = new GetHzContactListTask(getActivity());
        } else if (type == 1) {
            task = new GetContactListTask(getActivity());
        } else if (type == 2) {
            task = new GetCompanyContactListTask(getActivity());
        }
    }

    private void initData(String s) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasLength(s)) {
            params.put("keywords", s);
        }
        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onItemClick(int position) {
        AbstractFlexibleItem adapter = (AbstractFlexibleItem) swipeRefresh.getAdapter().getItem(position);
        if (adapter instanceof HzContactItem) {
            HzContactItem item = (HzContactItem) adapter;
            ContactDetailsActivity.startAction(getActivity(), item.getData());
        } else if (adapter instanceof ContactItem) {
            ContactItem item = (ContactItem) adapter;
            ContactDetailsActivity.startAction(getActivity(), item.getData());
        } else {
            CompanyContactItem item = (CompanyContactItem) adapter;
            ContactDetailsActivity.startAction(getActivity(), item.getData());
        }
        return false;
    }

    private void createSearchListener() {
        rxManager.on(RxAction.ACTION_CONTACT_SEARCH, new Action1<String>() {
            @Override
            public void call(String s) {
                initData(s);
            }
        });
    }
}

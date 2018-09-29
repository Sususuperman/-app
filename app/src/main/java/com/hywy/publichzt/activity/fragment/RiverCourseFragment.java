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
import com.hywy.publichzt.task.GetRiverCourseTask;

import java.util.HashMap;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 河道水文
 */

public class RiverCourseFragment extends Fragment implements FlexibleAdapter.OnItemClickListener {
    public Activity mActivity;
    TextView emptyText;
    LinearLayout emptyView;
    public RxManager rxManager;
    private int type;
    private int current_position;//记录点击的item
    private RecyclerView recyclerview;
    private SwipeRefreshview swipeRefresh;

    public static RiverCourseFragment newInstance() {
        RiverCourseFragment fragment = new RiverCourseFragment();
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
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetRiverCourseTask(getActivity());
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
//        CustomerManageItem customerManageItem = (CustomerManageItem) swipeRefresh_customer.getAdapter().getItem(position);
//        if (type == NO_INSURE) {
//            CustomerDisplayActivity.startAction(getActivity(), customerManageItem.getData(), "未投保");
//        } else if (type == IN_INSURE) {
//            CustomerDisplayActivity.startAction(getActivity(), customerManageItem.getData(), "在保");
//        } else if (type == OUT_INSURE) {
//            CustomerDisplayActivity.startAction(getActivity(), customerManageItem.getData(), "脱保");
//        }
        return false;
    }

    private void createRecordListener() {
//        rxManager.on(RxAction.ACTION_CREATE_RECORD, new Action1<RecordAction>() {
//            @Override
//            public void call(RecordAction action) {
//                switch (action.type) {
//                    case RecordAction.CREATE_ACTION:
//                        CustomerManageItem currentItem = (CustomerManageItem) swipeRefresh_customer.getAdapter().getItem(current_position);
//                        Organ organ = currentItem.getData();
//                        Record record = action.record;
//                        organ.setCompany_wish_type(record.getType());
//                        organ.setNextDate(record.getNext_visitDate());
//                        organ.setUserName(record.getUser_name());
//
//                        swipeRefresh_customer.getAdapter().notifyDataSetChanged();
//                        break;
//                }
//            }
//        });

//        rxManager.on(RxAction.ACTION_EDIT_CUSTOMER, new Action1<CustomerAction>() {
//            @Override
//            public void call(CustomerAction action) {
//                switch (action.type) {
//                    case CustomerAction.EDIT_ACTION:
//                        CustomerManageItem currentItem = (CustomerManageItem) swipeRefresh_customer.getAdapter().getItem(current_position);
//                        Organ organ = currentItem.getData();
//                        Organ o = action.organ;
//                        organ.setOrganName(o.getOrganName());
//
//                        Organ.Addon addon = organ.getAddon();
//                        Organ.Addon a = o.getAddon();
//                        addon.setAddress(a.getAddress());
//                        addon.setLat(a.getLat());
//                        addon.setLng(a.getLng());
//                        organ.setAddon(addon);
//
//                        swipeRefresh_customer.getAdapter().notifyDataSetChanged();
//                        break;
//                    case CustomerAction.CREATE_ACTION:
//                        if (type == NO_INSURE) {
//                            addItem(swipeRefresh_customer.getAdapter(), action.organ);
//                        }
//                        break;
//                }
//            }
//        });
    }

//    public void addItem(BaseListFlexAdapter<CustomerManageItem> adapter, Organ organ) {
//        if (recyclerview_customer.getVisibility() == View.GONE) {
//            recyclerview_customer.setVisibility(View.VISIBLE);
//        }
//        CustomerManageItem item = new CustomerManageItem(organ);
//        adapter.addItem(0, item);
//        adapter.notifyDataSetChanged();
//    }
}

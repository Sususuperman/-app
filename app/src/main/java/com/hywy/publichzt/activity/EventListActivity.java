package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.DialogTools;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.adapter.PlanListAdapter;
import com.hywy.publichzt.adapter.item.EventListItem;
import com.hywy.publichzt.task.GetEventListTask;
import com.hywy.publichzt.view.dialog.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import rx.functions.Action1;

/**
 * 事件列表
 *
 * @author Superman
 */
public class EventListActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.title)
    TextView title;

    private DialogPlus dialogPlus;

    private PlanListAdapter adapter;

    private int type = -1;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, EventListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        init();
        initListener();
        initData(-1);
    }

    private void init() {
        setTitleBulider(new Bulider().title("事件列表").backicon(R.drawable.ic_arrow_back_white_24dp));

        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);
        swipeRefresh.setMode(SwipeRefreshview.Mode.BOTH);

        title.setText("全部事件");
    }

    private void initData(int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("KEY", type);
        Task task = new GetEventListTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder().params(params).task(task)).isToast(false);
        swipeRefresh.request();
    }


    private void initListener() {

        swipeRefresh.setCompleteListener(new SwipeRefreshview.onCompleteListener() {
            @Override
            public void onComplete(Map<String, Object> result, boolean isLoadmore) {
//                if (result.get(Key.RESULT) != null) {
//                    List<River> list = (List<River>) result.get(Key.RESULT);
//                }
            }
        });

        mRxManager.on(RxAction.ACTION_EVENT_SUBMIT, new Action1<Object>() {
            @Override
            public void call(Object o) {
                initData(type);
            }
        });
    }

    /**
     */
    private void showRadioDialog() {
        //弹窗显示
        final List<String> list = new ArrayList<>();
        list.add("全部事件");
        list.add("未处理");
        list.add("处理中");
        list.add("已办结");
        DialogTools.showListViewDialog(this, "提示", list, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> params = new HashMap<>();
                title.setText(list.get(i));
                if (i == 0) {
                    initData(9);
                } else if (i == 1) {
                    type = 3;
                    initData(3);
                } else if (i == 2) {
                    type = 1;
                    initData(1);
                } else {
                    type = 4;
                    initData(4);
                }
            }
        });
    }


    @OnClick(R.id.layout_river)
    public void choose() {
        showRadioDialog();
    }


    @Override
    public boolean onItemClick(int position) {
        EventListItem eventListItem = (EventListItem) swipeRefresh.getAdapter().getItem(position);
        EventDetailsActivity.startAction(this, eventListItem.getData());
        return false;
    }
}

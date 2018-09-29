package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.NotifyItem;
import com.hywy.publichzt.entity.Notify;
import com.hywy.publichzt.task.GetNotifyListTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import eu.davidea.flexibleadapter.FlexibleAdapter;

public class NotifyActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, NotifyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        init();
        initData();
    }

    private void init() {
        this.setTitleBulider(new Bulider().title(getString(R.string.title_notify)).backicon(R.drawable.ic_arrow_back_white_24dp));
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetNotifyListTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();
    }

    @Override
    public boolean onItemClick(int position) {
        NotifyItem item = (NotifyItem) swipeRefresh.getAdapter().getItem(position);
        Notify notify = item.getData();
        NotifyDetailsActivity.startAction(this, notify);
        return false;
    }
}

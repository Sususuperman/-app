package com.hywy.publichzt.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.InspectionItem;
import com.hywy.publichzt.entity.Inspection;
import com.hywy.publichzt.task.GetMyXcTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * @author Superman
 */
public class AllXunchaActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        initData();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.log_xuncha)));
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();

//        params.put("USER_ID",1);

        Task task = new GetMyXcTask(this);

        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();

    }

    @Override
    public boolean onItemClick(int position) {
        InspectionItem item = (InspectionItem) swipeRefresh.getAdapter().getItem(position);
        Inspection inspection = item.getData();
        return false;
    }
}

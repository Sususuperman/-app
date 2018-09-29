package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.DialogTools;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.ProblemReportItem;
import com.hywy.publichzt.dao.ProblemDao;
import com.hywy.publichzt.entity.ProblemReport;
import com.hywy.publichzt.task.GetProblemReportListTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 我的上报
 *
 * @author Superman
 */
public class ProblemReportListActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, ProblemReportListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yujing_list);
        init();
        initData();
    }

    private void init() {
        this.setTitleBulider(new Bulider().title(getString(R.string.title_my_report)).backicon(R.drawable.ic_arrow_back_white_24dp));
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        findViewById(R.id.sendout).setVisibility(View.GONE);
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetProblemReportListTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();
    }

    @Override
    public boolean onItemClick(int position) {
        ProblemReportItem item = (ProblemReportItem) swipeRefresh.getAdapter().getItem(position);
        ProblemReport problemReport = item.getData();
        if (problemReport.getDATA_TYPE().equals(ProblemReport.DATA_LOCAL)) {
            ProblemReportActivity.startActionForResult(this, problemReport);
        } else
            ProblemReportDetailsActivity.startAction(this, problemReport);
        return false;
    }

    @Override
    public void onItemLongClick(int position) {
        ProblemReportItem item = (ProblemReportItem) swipeRefresh.getAdapter().getItem(position);
        ProblemReport problemReport = item.getData();
        if (problemReport.getDATA_TYPE().equals(ProblemReport.DATA_LOCAL)) {
            DialogTools.showConfirmDialog(this, "提示", "确定删除该条数据？", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProblemDao dao = new ProblemDao(ProblemReportListActivity.this);
                    dao.delete(problemReport);
                    swipeRefresh.getAdapter().removeItem(position);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initData();
        }
    }

}

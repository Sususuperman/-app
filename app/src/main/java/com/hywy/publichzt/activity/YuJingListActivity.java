package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.YuJingItem;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.AppMenu;
import com.hywy.publichzt.entity.YuJing;
import com.hywy.publichzt.task.GetYujingListTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 预警查看
 *
 * @author Superman
 */
public class YuJingListActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;

    private AppMenu menu;
    private App app;

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, YuJingListActivity.class);
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
        this.setTitleBulider(new Bulider().title(getString(R.string.title_yujing_list)).backicon(R.drawable.ic_arrow_back_white_24dp));
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        app = App.getInstance();
        menu = app.getMenu1();
        if (menu != null) {
            if (StringUtils.isNotNullList(menu.getSubMenu())) {
                for (AppMenu m : menu.getSubMenu()) {
                    if (m.getMENU_KEY().equals("yujingfabu")) {
                        if (StringUtils.isNotNullList(m.getSubMenu())) {
                            for (AppMenu am : m.getSubMenu()) {
                                if (am.getMENU_KEY().equals("yujingfabuanniu")) {
                                  findViewById(R.id.sendout).setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetYujingListTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();
    }

    @Override
    public boolean onItemClick(int position) {
        YuJingItem item = (YuJingItem) swipeRefresh.getAdapter().getItem(position);
        YuJing yuJing = item.getData();
        YujingDetailsActivity.startAction(this, yuJing);
        return false;
    }

    @OnClick(R.id.sendout)
    public void sendout() {
        YuJingActivity.startAction(this);
    }
}

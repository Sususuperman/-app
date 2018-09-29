package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.MessageItem;
import com.hywy.publichzt.task.GetMessageListTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import eu.davidea.flexibleadapter.FlexibleAdapter;

public class MessageBookActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MessageBookActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_book);
        init();
        initData();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("留言簿"));
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetMessageListTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();
    }

    @Override
    public boolean onItemClick(int position) {
        MessageItem item = (MessageItem) swipeRefresh.getAdapter().getItem(position);
        MessageInfoActivity.startAction(this, item.getData());
        return false;
    }
}

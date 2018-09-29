package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Message;
import com.hywy.publichzt.task.GetMessageInfoTask;
import com.hywy.publichzt.task.PostCreateMessageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class MessageInfoActivity extends BaseToolbarActivity {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;
    private Message message;

    @Bind(R.id.user_name)
    TextView tv_name;
    @Bind(R.id.et_content)
    EditText et_content;

    public static void startAction(Context context, Message message) {
        Intent intent = new Intent(context, MessageInfoActivity.class);
        intent.putExtra("message", message);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        init();
        initData();
    }

    private void init() {
        message = getIntent().getParcelableExtra("message");
        if (message != null) {
            setTitleBulider(new Bulider().title(message.getPER_NAME() + "的留言").backicon(R.drawable.ic_arrow_back_white_24dp));
        }
        swipeRefresh.setMode(SwipeRefreshview.Mode.DISABLED);
        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);

        tv_name.setText("发送至：" + message.getPER_NAME());
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        params.put("PER_ID", message.getPER_ID());
        Task task = new GetMessageInfoTask(this);
        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();
        swipeRefresh.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.tv_sendout)
    public void sendout() {
        String content = et_content.getText().toString().trim();
        if (StringUtils.hasLength(content)) {
            post();
        } else {
            IToast.toast("发送内容不能为空！");
        }
    }

    private void post() {
        SpringViewHandler handler = new SpringViewHandler(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PER_ID", message.getPER_ID());
            jsonObject.put("CONT", et_content.getText().toString());
            jsonObject.put("USER_ID", message.getUSER_ID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("data", jsonObject.toString());
        handler.request(params, new PostCreateMessageTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                et_content.setText("");
                initData();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }
}

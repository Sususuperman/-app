package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.Key;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.adapter.item.DealItem;
import com.hywy.publichzt.entity.Deal;
import com.hywy.publichzt.entity.EventSupervise;
import com.hywy.publichzt.task.PostCuibanEventTask;
import com.hywy.publichzt.view.MyRecycleView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Superman
 */
public class EventDetailsActivity extends BaseToolbarActivity {
    @Bind(R.id.iv_status_r)
    ImageView imageView;
    @Bind(R.id.tv_title_r)
    TextView eventName;
    @Bind(R.id.tv_publisher_r)
    TextView tvPublisher;
    @Bind(R.id.tv_time_r)
    TextView tvTime;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_event)
    TextView tvEvent;
    @Bind(R.id.tv_deadline)
    TextView tvDeadline;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.image_content)
    GridView contentGrid;
    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.recyclerview)
    MyRecycleView recycleView;

    @Bind(R.id.btn)
    Button btn;


    private EventSupervise eventSupervise;
    private int type;

    private BaseListFlexAdapter adapter;
    private ImagesAdapter imagesAdapter, contentAdapter;

    public static void startAction(Activity activity, EventSupervise eventSupervise) {
        Intent intent = new Intent(activity, EventDetailsActivity.class);
        intent.putExtra("eventSupervise", eventSupervise);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        init();
        initData();
        initListeners();
    }


    private void initData() {
        setTextView(eventName, eventSupervise.getEVENT_NAME());
        setTextView(tvPublisher, eventSupervise.getPER_NM());
        setTextView(tvTime, eventSupervise.getSTARTTIME());
        setTextView(tvAddress, eventSupervise.getREACH_NAME());
        setTextView(tvEvent, eventSupervise.getEVENT_TYPE_NAME());
        setTextView(tvDeadline, eventSupervise.getENDTIME());
        setTextView(tvContent, eventSupervise.getEVENT_CONT());

        initDeals(eventSupervise.getDeals());
//        setTextView(tvUser, waterRain.getPEOPLE());
//        setTextView(tvPhone, waterRain.getTEL());
//
//        setTextView(endTime, DateUtils.GetNowTimeChinesne("yyyy/MM/dd HH:00:00"));
//        setTextView(startTime, DateUtils.GetPevTimeChinesne("yyyy/MM/dd HH:00:00"));
//        setTextView(rainNum, waterRain.getDRP() + "");

    }

    @OnClick(R.id.btn)
    public void onClick() {
        if (eventSupervise.getDEAL_STATE().equals("1")) {
            remind();
        } else {
            EventHandleActivity.startAction(this, eventSupervise);
        }
    }

    private void remind() {
        SpringViewHandler handler = new SpringViewHandler(this);
        Map<String, Object> map = new HashMap<>();
        map.put("EVENT_ID", eventSupervise.getEVENT_ID());
        handler.request(map, new PostCuibanEventTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                finish();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
                IToast.toast((String) result.get(Key.MSG));
            }
        });
    }

    private void initDeals(List<Deal> deals) {
        if (StringUtils.isNotNullList(deals)) {
            for (Deal deal : deals) {
                DealItem dealItem = new DealItem(deal);
                adapter.addItem(dealItem);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void setTextView(TextView textView, String str) {
        if (StringUtils.hasLength(str)) {
            textView.setText(str);
        }
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.event_handle)));
        eventSupervise = getIntent().getParcelableExtra("eventSupervise");
        type = getIntent().getIntExtra("type", 0);
        adapter = new BaseListFlexAdapter(this);
        if (eventSupervise.getSTATE().equals("1")) {//state是1表示已办结，不是1就接着判断deal_state判断已处理和未处理
            findViewById(R.id.input_layout).setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.shiwu_yibanjie);
        } else {
            if (eventSupervise.getDEAL_STATE().equals("1")) {
                findViewById(R.id.input_layout).setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                btn.setText("催办");
                imageView.setImageResource(R.drawable.shiwu_chulizhong);
            } else {
                findViewById(R.id.input_layout).setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.shiwu_weichuli);

            }
        }

//        if (type == 1) {
//            btn.setVisibility(View.GONE);
//            recycleView.setVisibility(View.VISIBLE);
//            findViewById(R.id.input_layout).setVisibility(View.GONE);
//        } else {
//            recycleView.setVisibility(View.VISIBLE);
//            findViewById(R.id.input_layout).setVisibility(View.GONE);
//            btn.setVisibility(View.VISIBLE);
//        }
//
//        switch (type) {
//            case 0:
//                imageView.setImageResource(R.drawable.ic_event_todo);
//                break;
//            case 1:
//                imageView.setImageResource(R.drawable.icon_event_end);
//                break;
//            case 2:
//                imageView.setImageResource(R.drawable.ic_event_doing);
//                break;
//        }

        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new FullyLinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        imagesAdapter = new ImagesAdapter(this);
        if (StringUtils.isNotNullList(eventSupervise.getDealattch()))
            imagesAdapter.setList(eventSupervise.getDealattch());
        imageGrid.setAdapter(imagesAdapter);

        contentAdapter = new ImagesAdapter(this);
        if (StringUtils.isNotNullList(eventSupervise.getCountattch()))
            contentAdapter.setList(eventSupervise.getCountattch());
        contentGrid.setAdapter(contentAdapter);
    }

    private void initListeners() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mRxManager.post(RxAction.ACTION_EVENT_SUBMIT, "");
            finish();
        }
    }
}

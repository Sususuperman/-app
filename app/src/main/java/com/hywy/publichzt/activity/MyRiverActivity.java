package com.hywy.publichzt.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.PhoneUtil;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.RiverItem;
import com.hywy.publichzt.adapter.item.SpinnerItem;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetRiverListTask;
import com.hywy.publichzt.view.dialog.dialogplus.DialogPlus;
import com.hywy.publichzt.view.dialog.dialogplus.OnCancelListener;
import com.hywy.publichzt.view.dialog.dialogplus.OnClickListener;
import com.hywy.publichzt.view.dialog.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * 我的河道
 *
 * @author Superman
 */
public class MyRiverActivity extends BaseToolbarActivity implements FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.title)
    TextView title;

    private DialogPlus dialogPlus;

    private RecyclerView spinnerRecycle;

    private BaseListFlexAdapter mAdapter1, mAdapter2;
    private boolean isShow;

    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_river);
        init();
        initListener();
        initData();
    }

    private void init() {
        setTitleBulider(new Bulider().title(getString(R.string.text_my_river)).backicon(R.drawable.ic_arrow_back_white_24dp));

        swipeRefresh.setAdapter(recyclerview);
        swipeRefresh.getAdapter().initializeListeners(this);
        swipeRefresh.setMode(SwipeRefreshview.Mode.DISABLED);
//        mAdapter1 = new BaseListFlexAdapter(this);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        recyclerview.setAdapter(mAdapter1);

        mAdapter2 = new BaseListFlexAdapter(this);
        spinnerRecycle = new RecyclerView(this);
        spinnerRecycle.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(this));
        spinnerRecycle.setAdapter(mAdapter2);
    }

    private void initSpinner(List<River> list) {
        //先清一下
        mAdapter2.clear();
        List<SpinnerItem> items = new ArrayList<>();
        River river = new River();
        river.setRV_NAME("全部");
        items.add(new SpinnerItem(river));
        for (int i = 0; i < list.size(); i++) {
            SpinnerItem spinnerItem = new SpinnerItem(list.get(i));
            items.add(spinnerItem);
        }
        mAdapter2.addItems(0, items);
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetRiverListTask(this, id);

        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.refresh();
        swipeRefresh.isToast(false);

    }


    private void initListener() {
//        mAdapter1.initializeListeners(new FlexibleAdapter.OnItemClickListener() {
//            @Override
//            public boolean onItemClick(int position) {
//                RiverItem item = (RiverItem) mAdapter1.getItem(position);
//                return false;
//            }
//        });

        mAdapter2.initializeListeners(new FlexibleAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(int position) {
                SpinnerItem item = (SpinnerItem) mAdapter2.getItem(position);
                River r = item.getData();
                id = r.getRV_CODE();
                initData();//选择之后根据rv_code筛选数据进行显示
                if (isShow) {
                    cancleData();
                } else {
                    showData();
                }
                title.setText(item.getData().getRV_NAME());
                return false;
            }
        });

        swipeRefresh.setCompleteListener(new SwipeRefreshview.onCompleteListener() {
            @Override
            public void onComplete(Map<String, Object> result, boolean isLoadmore) {
                if (result.get(Key.RESULT) != null) {
                    List<River> list = (List<River>) result.get(Key.RESULT);
                    initSpinner(list);
                }
            }
        });
    }

    @OnClick(R.id.layout_river)
    public void choose() {
        if (isShow) {
            cancleData();
        } else {
            showData();
        }
    }

    private void showData() {
        isShow = true;
        //弹窗显示
        if (dialogPlus == null) {
            createDialogPlus();
        }
        dialogPlus.show();
    }

    private void cancleData() {
        isShow = false;
        if (dialogPlus != null && dialogPlus.isShowing()) {
            dialogPlus.dismiss();
        }
    }

    private void createDialogPlus() {
        dialogPlus = new DialogPlus.Builder(this)
                .setContentHolder(new ViewHolder(spinnerRecycle))
                .setGravity(DialogPlus.Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.white)
                .setCancelable(true)
                .setOutMostMargin(0, PhoneUtil.dp2px(this, 0), 0, 0)
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {
                        cancleData();
                    }
                })
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {

                    }
                })
                .create();
    }


    @Override
    public boolean onItemClick(int position) {
        if(swipeRefresh.getAdapter().getItem(position) instanceof RiverItem){
            RiverItem item = (RiverItem) swipeRefresh.getAdapter().getItem(position);
            RiverDetailsActivity.startAction(this, item.getData());
        }
        return false;
    }
}

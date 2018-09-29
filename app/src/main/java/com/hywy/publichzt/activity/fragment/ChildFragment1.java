package com.hywy.publichzt.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseFragment;
import com.cs.common.view.SwipeRefreshview;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.ExplaneActivity;
import com.hywy.publichzt.activity.RiverDetailsActivity;
import com.hywy.publichzt.adapter.item.RiverItem;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.task.GetRiverListTask;
import com.hywy.publichzt.view.MyScrollview;
import com.hywy.publichzt.view.customer.BannerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import rx.functions.Action1;

import static com.hywy.publichzt.action.RxAction.MAIN_FRAGMENT_1_REFRESH;


/**
 * Created by weifei on 17/1/3.
 */

public class ChildFragment1 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, FlexibleAdapter.OnItemClickListener {
    @Bind(R.id.river_recy)
    RecyclerView riverRecy;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshview swipeRefresh;
    @Bind(R.id.scrollview)
    MyScrollview scrollView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.bannerview)
    BannerView bannerView;

    List<View> mItems;

    ImageView[] mBottomImgs;

    public static ChildFragment1 newInstance(String title) {
        Bundle args = new Bundle();
        ChildFragment1 fragment = new ChildFragment1();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main1;
    }

    @Override
    protected void initView() {
        init();
        refreshListener();
    }


    private void init() {
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.white));//需要设置下字体颜色，style文件已经设置为白色一直弄不上
        title.setText(getArguments().getString("title"));

        swipeRefresh.setMode(SwipeRefreshview.Mode.DISABLED);
        swipeRefresh.setAdapter(riverRecy);
        swipeRefresh.getAdapter().initializeListeners(this);

        mItems = new ArrayList<>();
        addImageView();


        mBottomImgs = new ImageView[mItems.size()];
        bannerView.startLoop(true);
        bannerView.setViewList(mItems);
        initData();
    }


    private void initData() {
        Map<String, Object> params = new HashMap<>();
        Task task = new GetRiverListTask(getActivity());

        swipeRefresh.builder(new SwipeRefreshview.Builder()
                .task(task).params(params));
        swipeRefresh.isToast(false);
        swipeRefresh.refresh();

    }


    /**
     * 添加图片
     */
    private void addImageView() {
        ImageView view0 = new ImageView(getContext());
        view0.setImageResource(R.drawable.ic_banner_img1);
        ImageView view1 = new ImageView(getContext());
        view1.setImageResource(R.drawable.ic_banner_img2);
        ImageView view2 = new ImageView(getContext());
        view2.setImageResource(R.drawable.ic_banner_img3);

        view0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(view0);
//        mItems.add(view1);
//        mItems.add(view2);

    }

    /**
     * 刷新功能监听
     */
    private void refreshListener() {
        mRxManager.on(MAIN_FRAGMENT_1_REFRESH, new Action1<String>() {
            @Override
            public void call(String s) {
                initData();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public boolean onItemClick(int position) {
        RiverItem item = (RiverItem) swipeRefresh.getAdapter().getItem(position);
        River river = item.getData();
        RiverDetailsActivity.startAction(getActivity(), river);
        return false;
    }

    @OnClick(R.id.explane)
    public void toActivity() {
        startActivity(new Intent(getActivity(), ExplaneActivity.class));
    }
}

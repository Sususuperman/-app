package com.cs.common.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.cs.android.common.BuildConfig;
import com.cs.android.task.Task;
import com.cs.common.HttpConst;
import com.cs.common.Key;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.item.ProgressItem;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.view.swopeRefresh.BaseSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by charmingsoft on 2015/9/7.
 */

public class SwipeRefreshview<T extends AbstractFlexibleItem> extends BaseSwipeRefreshLayout
        implements FlexibleAdapter.EndlessScrollListener,
        SwipeRefreshLayout.OnRefreshListener, OnPostListenter {
    private SpringViewHandler mHandler;
    private BaseListFlexAdapter mAdapter;
    private Context context;
    private Builder builder;
    private boolean isRequest;
    private RecyclerView recyclerView;
    private boolean isEnableRefresh = true;//是否禁用刷新

    private boolean isEnableWiperefresh;

    public SwipeRefreshview(Context activity) {
        this(activity, null);
    }

    private onCompleteListener completeListener;
    private LinearLayout promptView;
    private ProgressItem progressItem;

    private String defaultCount = "count";
    private String defaultPage = "page";

    public SwipeRefreshview(Context activity, AttributeSet attrs) {
        super(activity, attrs);
        context = activity;
        init();
    }

    public Builder getBuilder() {
        return builder;
    }

    private void init() {
        mAdapter = new BaseListFlexAdapter<>(context);
        mHandler = new SpringViewHandler(context, this);
        promptView = new LinearLayout(context);
        promptView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setOnRefreshListener(this);
    }

    public void setAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(0);
        recyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(getContext()));
        mAdapter.setAnimationOnReverseScrolling(true);
        mAdapter.setAnimationOnScrolling(true);
        mAdapter.setAnimationInterpolator(new DecelerateInterpolator());
        mAdapter.setAnimationDelay(150);
        mAdapter.setAnimationInitialDelay(500);
        mAdapter.initializeListeners(this);
        mAdapter.setDisplayHeadersAtStartUp(true)//Show Headers at startUp!
                .enableStickyHeaders()//Make headers sticky
                .setEndlessScrollThreshold(1);//Default=1
        if (!isEnableWiperefresh && isEnableRefresh) {
            setMode(Mode.BOTH);
        }
    }

    public T getItem(int position) {
        return (T) mAdapter.getItem(position);
    }

    public List<T> getAllItems() {
        return (List<T>) mAdapter.getAllItems();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    public void setEdableWiperefresh(boolean edableWiperefresh) {
        isEnableWiperefresh = edableWiperefresh;
    }

    public void setPostListener(OnPostListenter listener) {
        mHandler.setListener(listener);
    }

    public void setOnUpdateListener(FlexibleAdapter.OnUpdateListener onUpdateListener) {
        mAdapter.initializeListeners(onUpdateListener);
    }

    public void setDefaultCount(String defaultCount) {
        this.defaultCount = defaultCount;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

    public enum Mode {
        DISABLED,
        PULL_FROM_START,
        PULL_FROM_END,
        BOTH,
    }

    /**
     * 设置下拉刷新模式，注意必须在设置setAdapter之后调用
     *
     * @param mMode
     */
    public void setMode(Mode mMode) {
        if (mAdapter == null) {
            throw new NullPointerException("adapter can't be empty!!");
        }
        setEnabled(true);
        switch (mMode) {
            case BOTH:
                isEnableRefresh = true;
                mAdapter.setEndlessScrollListener(this, new ProgressItem());
                break;
            case DISABLED:
                setEnabled(false);
                isEnableRefresh = false;
                break;
            case PULL_FROM_END:
                setEnabled(false);
                isEnableRefresh = false;
                mAdapter.setEndlessScrollListener(this, new ProgressItem());
                break;
        }


    }

    public static class Builder {
        public Map<String, Object> params;
        public Task task;

        public Builder(Task task, Map<String, Object> params) {
            this.task = task;
            this.params = params;
        }

        public Builder() {

        }

        public Builder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public Builder task(Task task) {
            this.task = task;
            return this;
        }
    }


    public SwipeRefreshview builder(Builder builder) {
        this.builder = builder;
        return this;
    }

    public void refresh() {
        isEnableWiperefresh = true;
        request();
    }

    /**
     * 发送请求
     */
    public void request() {
        if (isRequest)

            if (isEnableRefresh) {
                setEnabled(true);
            }
        setRefreshing(true);

        isRequest = true;
        mAdapter.setClear(true);
        mAdapter.setPage(HttpConst.PAGE);

        Map<String, Object> params = builder.params == null ? new HashMap<String, Object>() : builder.params;
        params.put(defaultPage, mAdapter.getPage());
        params.put(defaultCount, mAdapter.getCount());
        setWait(false);
        if (builder != null)
            mHandler.request(builder.params, builder.task);
    }


    @Override
    public void onRefresh() {
        if (isRequest) return;
        setEnabled(false);
        if (builder != null) {
            if (builder.task != null) {
                isRequest = true;

                Map<String, Object> params = builder.params == null ? new HashMap<String, Object>() : builder.params;
                mAdapter.setPage(HttpConst.PAGE);
                mAdapter.setClear(true);
                params.put(defaultPage, mAdapter.getPage());
                params.put(defaultCount, mAdapter.getCount());
                setWait(false);
                mHandler.request(params, builder.task);
            }
        }
    }


    private void setWait(boolean iswait) {
        BaseHandler_.Builder b = mHandler.getBuilder();
        b.isWait(iswait);
        mHandler.setBuilder(b);
    }

    public void isToast(boolean isToast) {
        BaseHandler_.Builder b = mHandler.getBuilder();
        b.isShowToast(isToast);
        mHandler.setBuilder(b);
    }

    @Override
    public void onLoadMore() {
        boolean b = recyclerView.canScrollVertically(1);//他的值表示是否能向上滚动，false表示已经滚动到底部
        boolean b1 = recyclerView.canScrollVertically(-1);//他的值表示是否能向下滚动，false表示已经滚动到顶部
        if (BuildConfig.DEBUG) {
            Log.e("sun", "onLoadMore: b " + b + "   b1 " + b1);
        }
        if (!(b || b1)) {
            mAdapter.onLoadMoreComplete(null);
            return;
        }
        if (isRequest || builder == null) return;
        isRequest = true;
        if (!mAdapter.isNomore()) {
            mAdapter.setClear(false);
            mAdapter.setPage(mAdapter.getPage() + 1);
            if (builder != null) {
                Map<String, Object> params = builder.params == null ? new HashMap<String, Object>() : builder.params;
                params.put(defaultPage, mAdapter.getPage());
                params.put(defaultCount, mAdapter.getCount());
                setWait(false);
                mHandler.request(builder.params, builder.task);
            }
        } else {
//            IToast.toast(R.string.no_record);
            isRequest = false;
            mAdapter.onLoadMoreComplete(null);
            stopRefreshing();
        }
    }

    private void stopRefreshing() {
        setRefreshing(false);
        Observable.timer(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (isEnableRefresh) {//如果禁用刷新，就不用开启了
                            setEnabled(true);
                        } else {
                            setEnabled(false);
                        }
                    }
                });
    }

    @Override
    public void OnPostSuccess(Map<String, Object> result) {
        List<T> list = (List<T>) result.get(Key.ITEMS);
        if (list == null) {
            list = new ArrayList<>();
        }
        boolean isNetMore = list.size() < mAdapter.getCount();
        mAdapter.setNomore(isNetMore);
        if (mAdapter.isClear()) {
            clear();
            mAdapter.updateDataSet(list);
        } else {
            mAdapter.onLoadMoreComplete(list);
        }
        mAdapter.notifyDataSetChanged();
        if (completeListener != null) {
//            completeListener.onComplete(HttpConst.ApiSuccess, "", !mAdapter.isClear());
            completeListener.onComplete(result, !mAdapter.isClear());
        }

        isRequest = false;
        stopRefreshing();
    }

    private void clear() {
        mAdapter.selectAll();
        mAdapter.removeAllSelectedItems();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnPostFail(Map<String, Object> result) {
        if (result != null && result.containsKey(Key.RET)) {
            int ret = Integer.parseInt(result.get(Key.RET).toString());
            if (ret == HttpConst.ApiNoData && mAdapter.isClear()) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            }
            if (completeListener != null) {
                String msg;
                if (ret == HttpConst.ApiNoData) {
                    msg = result.containsKey(Key.MSG) ? result.get(Key.MSG).toString() : "没有相关数据";
                } else {
                    msg = result.containsKey(Key.MSG) ? result.get(Key.MSG).toString() : "";
                }
//                completeListener.onComplete(ret, msg, !mAdapter.isClear());
                completeListener.onComplete(result, !mAdapter.isClear());
            }
        } else {
            if (completeListener != null)
//                completeListener.onComplete(-1, "数据异常！", false);
                completeListener.onComplete(result, false);
        }

        isRequest = false;
        stopRefreshing();
        mAdapter.onLoadMoreComplete(null);
    }

    private LayoutParams getLayoutparams() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return params;
    }

    public BaseListFlexAdapter<T> getAdapter() {
        return mAdapter;
    }


    public interface onCompleteListener {
        /**
         * @param result
         * @param isLoadmore 是否加载更多
         */
        void onComplete(Map<String, Object> result, boolean isLoadmore);
    }

    public void setCompleteListener(onCompleteListener completeListener) {
        this.completeListener = completeListener;
    }
}


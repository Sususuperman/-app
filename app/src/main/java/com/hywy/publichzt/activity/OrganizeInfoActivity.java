package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.listener.OnTextChangedListener;
import com.cs.common.utils.PhoneUtil;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.activity.fragment.ContactFragment;
import com.hywy.publichzt.adapter.RiverFragmentAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * 组织信息
 */
public class OrganizeInfoActivity extends BaseToolbarActivity {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.delete)
    ImageView delete;

    private List<Fragment> list;
    private RiverFragmentAdapter adapter;
    private String[] titles = new String[]{"河长", "河长办", "协作单位"};

    public static void startAction(Context context) {
        Intent intent = new Intent(context, OrganizeInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_info);
        init();
        initListeners();
    }

    private void init() {
        this.setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.title_contact)));
        list = new ArrayList<>();
        list.add(ContactFragment.newInstance(ContactFragment.CONTACT_HEZHANG_LIST));
        list.add(ContactFragment.newInstance(ContactFragment.CONTACT_HEZHANGBAN_LIST));
        list.add(ContactFragment.newInstance(ContactFragment.CONTACT_XIEZUODANWEI_LIST));

        adapter = new RiverFragmentAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();//设置默认选中项
//        int padding = PhoneUtil.dp2px(this, 8);
//        reduceMarginsInTabs(tabLayout, padding);
    }

    // Observable（被观察者）发射出事件，然后subscribe（订阅）Observer（观察者）然后Observer会
    // 接收到事件，并进行处理的一个过程。每0.3秒请求一次
    private void initListeners() {
        OnTextChangedListener listener = new OnTextChangedListener(delete, search);
        search.addTextChangedListener(listener);

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                listener.setOnSearchTextChangedListener(new OnTextChangedListener.OnSearchTextChangedListener() {
                    @Override
                    public void OnSearchTextChanged(final String text) {
                        subscriber.onNext(text);
                    }
                });
            }
        }).debounce(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mRxManager.post(RxAction.ACTION_CONTACT_SEARCH, s);
                    }
                });

    }


    /**
     * 对tabLayout的指示器的长度的处理
     *
     * @param tabLayout
     * @param marginOffset
     */
    public void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }
            tabLayout.requestLayout();
        }
    }


    /*****官方建议，可以通过映射来修改自己想要的长短****/
    private void setIndicator(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout layout = null;
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);

                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        params.setMarginStart(PhoneUtil.dp2px(this, marginLeft));
                        params.setMarginEnd(PhoneUtil.dp2px(this, marginRight));
                    }
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.message) {
            MessageBookActivity.startAction(this);
        }
        return super.onOptionsItemSelected(item);
    }


}

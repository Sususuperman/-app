package com.cs.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.android.common.R;

import static android.R.color.black;
import static android.R.color.white;


/**
 * Created by weifei on 2015/9/7.
 */
public class BaseToolbarActivity<Body extends View> extends BaseActivity {
    private String mTitle;
    protected Toolbar mToolbar;
    private LinearLayout mBody;
    public static final String TITLE = "title";
    private Body bodyView;
    private Bulider bulider;
    private boolean isEnableHome = false;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View child = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(child);
    }

    @Override
    public void setContentView(View child) {
        View view = LayoutInflater.from(this).inflate(R.layout.base_toolbar, null);
        mBody = (LinearLayout) view.findViewById(R.id.body);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.title);
        child.setLayoutParams(getLayoutparams());
        mBody.addView(child);

        super.setContentView(view);

        setToolBar();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 设置title
     *
     * @param bulider
     */
    protected void setTitleBulider(Bulider bulider) {
        this.bulider = bulider;
        setToolBar();
    }

    private void setToolBar() {
        if (bulider == null) {
            bulider = new Bulider();
        }

        if (bulider.subtitle != null)
            mToolbar.setSubtitle(bulider.subtitle);

        if (bulider.backIcon != 0) {
            mToolbar.setNavigationIcon(bulider.backIcon);
        }

        if (bulider.logo != 0) {
            mToolbar.setLogo(bulider.logo);
        }
        mToolbar.setTitleTextColor(getResources().getColor(bulider.titleColor));
        mToolbar.setSubtitleTextColor(getResources().getColor(bulider.subtitleColor));
        setSupportActionBar(mToolbar);

        //居中显示
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (getIntent().hasExtra(TITLE)) {
            mTitle = getIntent().getStringExtra(TITLE);
            getSupportActionBar().setTitle(mTitle);
        }
        if (bulider.title != null) {
            getSupportActionBar().setTitle(bulider.title);
            title.setText(bulider.title);
        }

    }


    public class Bulider {
        int logo;
        int titleColor = white;
        int subtitleColor = black;
        int backIcon = 0;
        String title;
        String subtitle;

        public Bulider() {

        }

        public Bulider title(String title) {
            this.title = title;
            return this;
        }

        public Bulider subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Bulider logo(int logoId) {
            this.logo = logoId;
            return this;
        }

        public Bulider titleColor(int color) {
            this.titleColor = color;
            return this;
        }

        public Bulider subtitleColor(int color) {
            this.subtitleColor = color;
            return this;
        }

        public Bulider backicon(int backIconid) {
            this.backIcon = backIconid;
            return this;
        }
    }

    /**
     * 设置主体布局
     *
     * @param id
     */
    private void setBodyView(int id) {
        Body a = (Body) LayoutInflater.from(this).inflate(id, null);
        setBodyView(a);
    }

    /**
     * 设置主体布局
     *
     * @param view
     */
    private void setBodyView(Body view) {
        this.bodyView = view;
        if (view != null) {
            mBody.removeAllViews();
            bodyView.setLayoutParams(getLayoutparams());
            mBody.addView(bodyView);
        }
    }

    private ViewGroup.LayoutParams getLayoutparams() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bulider.backIcon != 0) {
            if (item.getItemId() == android.R.id.home) {
                if (!isEnableHome)
                    finish();
                else
                    onClickBack();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (!isEnableHome) {
                return super.onKeyDown(keyCode, event);
            } else {
                onClickBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickBack() {

    }

    /**
     * 禁用父类返回键,如果isEnableHome为true
     * 返回按钮和物理返回按钮将执行onClickBack()方法，不去关闭activity，需要手动finish
     *
     * @param isEnableHome
     */
    public void setHomeEnable(boolean isEnableHome) {
        this.isEnableHome = isEnableHome;
    }

    public Body getBodyView() {
        return bodyView;
    }


}

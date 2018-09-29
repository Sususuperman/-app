package com.hywy.publichzt.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
import com.cs.common.base.BaseFragment;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.ShowAddressActivity;
import com.hywy.publichzt.adapter.item.ZhuanGuanItem;
import com.hywy.publichzt.entity.Deal;
import com.hywy.publichzt.entity.RiverDetails;
import com.hywy.publichzt.view.customer.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 河道基本信息
 *
 * @author Superman
 */
public class RVFragment1 extends BaseFragment {
    @Bind(R.id.bannerview)
    BannerView bannerView;
    @Bind(R.id.site_name)
    TextView siteName;
    @Bind(R.id.site_num)
    TextView siteNum;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_end)
    TextView tvEnd;
    @Bind(R.id.tv_length)
    TextView tvLength;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_resp)
    TextView tvResp;
    @Bind(R.id.tv_target)
    TextView tvTarget;
    @Bind(R.id.tv_hezhang)
    TextView tvHezhang;
    @Bind(R.id.tv_work)
    TextView tvWork;
    @Bind(R.id.tv_group)
    TextView tvGroup;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private RiverDetails.HDBaseBean hdBaseBean;

    private BaseListFlexAdapter adapter;

    List<View> mItems;

    ImageView[] mBottomImgs;

    public static RVFragment1 newInstance(RiverDetails.HDBaseBean hdBaseBean) {
        Bundle args = new Bundle();
        args.putParcelable("hdBaseBean", hdBaseBean);
        RVFragment1 fragment = new RVFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_river_basic;
    }

    @Override
    protected void initView() {
        mItems = new ArrayList<>();
        addImageView();


        mBottomImgs = new ImageView[mItems.size()];
        bannerView.startLoop(true);
        bannerView.setViewList(mItems);

        adapter = new BaseListFlexAdapter(getActivity());
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        if (getArguments() != null) {
            hdBaseBean = getArguments().getParcelable("hdBaseBean");
        }

        if (hdBaseBean != null) {
            tvLength.setText(hdBaseBean.getRIVER_LEN() + "公里");
            if (StringUtils.hasLength(hdBaseBean.getB_E_LOCATION())) {
                String[] strings = hdBaseBean.getB_E_LOCATION().split("，");
                tvStart.setText(strings[0]);
                tvEnd.setText(strings[1]);
            }

            setTextView(siteName, hdBaseBean.getREACH_NAME());
            setTextView(siteNum, "河道编码：" + hdBaseBean.getREACH_CODE());

            setTextView(tvAddress, hdBaseBean.getADDRESS());
            setTextView(tvResp, hdBaseBean.getPER_DUTY());
            setTextView(tvTarget, hdBaseBean.getMG_TARGET());

            setTextView(tvHezhang, hdBaseBean.getPER_NAME());
            setTextView(tvGroup, hdBaseBean.getDT_PER_COMP());
            setTextView(tvWork, hdBaseBean.getPER_DUTIES());
            setTextView(tvUser, hdBaseBean.getSUP_PER());
            setTextView(tvPhone, hdBaseBean.getSUP_PHONE());

            if (StringUtils.isNotNullList(hdBaseBean.getDeals())) {
                for (Deal deal : hdBaseBean.getDeals()) {
                    ZhuanGuanItem item = new ZhuanGuanItem(deal);
                    adapter.addItem(item);
                }
                adapter.notifyDataSetChanged();
            }

        }
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
        mItems.add(view1);
        mItems.add(view2);

    }

    private void setTextView(TextView tv, String str) {
        if (StringUtils.hasLength(str)) {
            tv.setText(str);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @OnClick(R.id.tv_locate)
    public void toShowMap() {
        ShowAddressActivity.startAction(getActivity(), Double.parseDouble(hdBaseBean.getLGTD()), Double.parseDouble(hdBaseBean.getLTTD()));
    }
}

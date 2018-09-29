package com.hywy.publichzt.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
import com.cs.common.base.BaseFragment;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.XunChaRecordItem;
import com.hywy.publichzt.entity.RiverDetails;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * 河道~巡查记录
 *
 * @author Superman
 */
public class RVFragment4 extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private ArrayList<RiverDetails.XCJHListBean> xcjhListBean;

    private BaseListFlexAdapter adapter;

    public static RVFragment4 newInstance(ArrayList<RiverDetails.XCJHListBean> xcjhListBean) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("xcjhListBean", xcjhListBean);
        RVFragment4 fragment = new RVFragment4();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xcjl;
    }

    @Override
    protected void initView() {
        adapter = new BaseListFlexAdapter(getActivity());
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        if (getArguments() != null) {
            xcjhListBean = getArguments().getParcelableArrayList("xcjhListBean");
        }

        if (StringUtils.isNotNullList(xcjhListBean)) {
            for (RiverDetails.XCJHListBean xj : xcjhListBean) {
                XunChaRecordItem item = new XunChaRecordItem(xj);
                adapter.addItem(item);
            }
            adapter.notifyDataSetChanged();
        }

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


}

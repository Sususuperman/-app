package com.hywy.publichzt.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.cs.android.task.Task;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
import com.cs.common.base.BaseFragment;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.StringUtils;
import com.hywy.amap.PoiItem;
import com.hywy.amap.SingleOverlay;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.item.XunChaRecordItem;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.entity.RiverDetails;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetComplainListTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;


/**
 * 投诉地图
 *
 * @author Superman
 */
public class ComplainMapFragment extends BaseFragment {
    @Bind(R.id.mapView)
    MapView mapView;
    private SingleOverlay overlay;//我的位置
    List<Complain> list;

    private AMap aMap;

    public static ComplainMapFragment newInstance(ArrayList<Complain> list) {
        ComplainMapFragment fragment = new ComplainMapFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complainmap;
    }

    @Override
    protected void initView() {
        initData();
    }

    private void initData() {
        Map<String, Object> params = new HashMap<>();
        params.put("ddd", 0);
        SpringViewHandler handler = new SpringViewHandler(getActivity());
        handler.setBuilder(new BaseHandler_.Builder().isWait(false));
        Task task = new GetComplainListTask(getActivity());
        handler.request(params, task);
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                list = (List<Complain>) result.get(Key.RESULT);
                addMarkers(list);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }

    /**
     * 初始化地图
     */
    private void initMap(Bundle arg0) {
        mapView.onCreate(arg0);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        overlay = new SingleOverlay(getActivity(), aMap);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initMap(savedInstanceState);

    }

    /**
     * 地图上显示当前用户的位置
     */
    private void addMarkers(List<Complain> list) {
        for (Complain complain : list) {
            if (StringUtils.hasLength(complain.getLGTD()) && StringUtils.hasLength(complain.getLLTD())) {
                PoiItem item = new PoiItem();
                item.setLatLng(new LatLng(Double.parseDouble(complain.getLLTD()), Double.parseDouble(complain.getLGTD())));
                overlay.setPoi(item);
                if (complain.getCOMZT().equals(0)) {
                    overlay.setIcon(R.drawable.location_red);
                } else overlay.setIcon(R.drawable.location_green);
                overlay.addToMap();
            }
        }
    }

}

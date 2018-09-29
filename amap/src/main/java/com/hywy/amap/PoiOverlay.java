package com.hywy.amap;

import android.content.Context;
import android.support.v4.content.ContextCompat;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Poi图层类。在高德地图API里，如果要显示Poi，可以用此类来创建Poi图层。如不满足需求，也可以自己创建自定义的Poi图层。
 * @since V2.1.0
 */
public class PoiOverlay {
	public static final int Circle_Range = 10000;//圆环的半径
	private List<PoiItem> mPois;
	private AMap mAMap;
	private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
	/**
	 * 通过此构造函数创建Poi图层。
	 * @param amap 地图对象。
	 * @param pois 要在地图上添加的poi。列表中的poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类<strong> <a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
	 * @since V2.1.0
	 */
	protected Context mContext;
	public PoiOverlay(Context context, AMap amap) {
		this.mAMap = amap;
		this.mContext = context;
	}

	public void setPois(List<PoiItem> mPois) {
		this.mPois = mPois;
	}

	/**
	 * 添加Marker到地图中。
	 * @since V2.1.0
	 */
	public void addToMap() {
		try{
			int size = mPois.size();
			for (int i = 0; i < size ; i++) {
				PoiItem item = mPois.get(i);
				Marker marker = mAMap.addMarker(getMarkerOptions(i));
				marker.setObject(item.getData());
				mPoiMarks.add(marker);
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	/**
	 * 去掉PoiOverlay上所有的Marker。
	 * @since V2.1.0
	 */
	public void removeFromMap() {
		for (Marker mark : mPoiMarks) {
			mark.remove();
		}
	}

	/**
	 * 移动镜头到当前的视角。
	 * @since V2.1.0
	 */
	public void zoomToSpan() {
		try{
			if (mPois != null && mPois.size() > 0) {
				if (mAMap == null)
					return;
				if(mPois.size()==1){
					mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPois.get(0).getLatLng(), 12f));
				}else{
					LatLngBounds bounds = getLatLngBounds();
					mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 14));
				}
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	private LatLngBounds getLatLngBounds() {
		LatLngBounds.Builder b = LatLngBounds.builder();
		int size = mPois.size();
		for (int i = 0; i < size; i++) {
			LatLng latLng = mPois.get(i).getLatLng();
			b.include(new LatLng(latLng.latitude, latLng.longitude));
		}
		return b.build();
	}

	private MarkerOptions getMarkerOptions(int index) {
		LatLng latLng = mPois.get(index).getLatLng();
		return new MarkerOptions() .position(new LatLng(latLng.latitude, latLng.longitude))
				.title(getTitle(index)).snippet(getSnippet(index))
				.icon(getBitmapDescriptor(index));
	}
	/**
	 * 给第几个Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
	 * @param index 第几个Marker。
	 * @return 更换的Marker图片。
	 * @since V2.1.0
	 */
	protected BitmapDescriptor getBitmapDescriptor(int index) {
		return null;
	}
	/**
	 * 返回第index的Marker的标题。
	 * @param index 第几个Marker。
	 * @return marker的标题。
	 * @since V2.1.0
	 */
	protected String getTitle(int index) {
		return mPois.get(index).getTitle();
	}
	/**
	 * 返回第index的Marker的详情。
	 * @param index 第几个Marker。
	 * @return marker的详情。
	 * @since V2.1.0
	 */
	protected String getSnippet(int index) {
		return mPois.get(index).getSnippet();
	}


	/**
	 * 从marker中得到poi在list的位置。
	 * @param marker 一个标记的对象。
	 * @return 返回该marker对应的poi在list的位置。
	 * @since V2.1.0
	 */
	public int getPoiIndex(Marker marker) {
		int size = mPoiMarks.size();
		for (int i = 0; i < size; i++) {
			if (mPoiMarks.get(i).equals(marker)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 返回第index的poi的信息。
	 * @param index 第几个poi。
	 * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
	 * @since V2.1.0
	 */
	public PoiItem getPoiItem(int index) {
		if (index < 0 || index >= mPois.size()) {
			return null;
		}
		return mPois.get(index);
	}


	public ArrayList<Marker> getPoiMarks() {
		return mPoiMarks;
	}

	public List<PoiItem> getPois() {
		return mPois;
	}

	/**
	 * 判断经纬度是否在手机一屏内
	 * @param latLng
	 * @return
	 */
	public boolean contains(LatLng latLng) {
		VisibleRegion visibleRegion = mAMap.getProjection().getVisibleRegion();
		if (visibleRegion.latLngBounds.contains(latLng)) {
			return true;
		}
		return false;
	}

	//平移地图，不缩放地图
	public void move(LatLng latLng){

		//平移地图，
		mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mAMap.getCameraPosition().zoom),500,null);
	}
	//平移地图，缩放地图
	public void moveAndZoom(LatLng latLng){
		//平移地图，
		mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f),1000,null);
	}

	private Circle circle;
	public Circle addCircle(LatLng latLng,Context context){
		if(circle == null){
			CircleOptions options = new CircleOptions();
			options.radius(Circle_Range)
					.fillColor(ContextCompat.getColor(context,R.color.fill_color))
					.strokeColor(ContextCompat.getColor(context,R.color.stroke_color))
					.strokeWidth(1);
			circle = mAMap.addCircle(options);
		}
		circle.setCenter(latLng);
		return circle;
	}

	/**
	 * 获取屏幕左上角和右下角经纬度
	 * @return
	 */
	public String getCoordinates(){
		try {
			if(mAMap!=null ){
				VisibleRegion visibleRegion = mAMap.getProjection().getVisibleRegion();
				LatLng ltLatLng = visibleRegion.farLeft;
				LatLng rbLatLng = visibleRegion.nearRight;

				double l_lat = ltLatLng.latitude;
				double l_lng = ltLatLng.longitude;

				double r_lat = rbLatLng.latitude;
				double r_lng = rbLatLng.longitude;

				String lg = l_lng + "," + l_lat;
				String rg = r_lng + "," + r_lat;
				String coordinates = lg+"|"+rg;
				return coordinates;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

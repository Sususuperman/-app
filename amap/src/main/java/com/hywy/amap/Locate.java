package com.hywy.amap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class Locate {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private Context mContext;
    private boolean isInfinite;//是否进行连续定位

    public Locate(Context context) {
        this(context, false);
    }

    public Locate(Context context, boolean isInfinite) {
        this.mContext = context;
        this.isInfinite = isInfinite;
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        initLocation();

    }


    public void initLocation() {
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        if (isInfinite) {
            locationOption.setOnceLocation(false);
            /**
             * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
             * 注意：只有在高精度模式下的单次定位有效，其他方式无效
             */
            locationOption.setGpsFirst(true);
            // 设置发送定位请求的时间间隔,最小值为1000ms,1秒更新一次定位信息
            locationOption.setInterval(3000);
        }else {
            locationOption.setOnceLocation(true);
        }
        //低功耗定位模式功能演示
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //仅设备定位模式功能演示
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        if (mListener != null)
                            mListener.onReceiveLocation(aMapLocation);
                    } else {
                        Toast.makeText(mContext, R.string.error_location, Toast.LENGTH_SHORT).show();
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());

                        if (mListener != null)
                            mListener.onReceiveFail();
                    }
                }
            }
        });
    }


    public OnLocationListener mListener;

    public void setOnLocationListener(OnLocationListener mListener) {
        this.mListener = mListener;
    }

    public interface OnLocationListener {
        void onReceiveLocation(AMapLocation aMapLocation);

        void onReceiveFail();
    }

    /**
     * 查询地理位置接口
     *
     * @author charmingsoft
     */
    public interface OnGeoSearchLintener {
        void onRegeocodeSearched(RegeocodeResult arg0, int arg1);
    }

    private OnGeoSearchLintener onGeoSearchLintener;

    public OnGeoSearchLintener getOnGeoSearchLintener() {
        return onGeoSearchLintener;
    }

    public void setOnGeoSearchLintener(OnGeoSearchLintener onGeoSearchLintener) {
        this.onGeoSearchLintener = onGeoSearchLintener;
    }

    /**
     * 高德反向地理位置解析
     *
     * @param lat
     * @param lng
     */
    public void searchAddress(Double lat, Double lng) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
        LatLonPoint latLng = new LatLonPoint(lat, lng);
        RegeocodeQuery query = new RegeocodeQuery(latLng, 200, GeocodeSearch.AMAP);
        geocodeSearch.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {

            @Override
            public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
                if (arg1 == 1000) {
                    if (onGeoSearchLintener != null)
                        onGeoSearchLintener.onRegeocodeSearched(arg0, arg1);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult arg0, int arg1) {

            }
        });
        geocodeSearch.getFromLocationAsyn(query);
    }

    public void stop() {
        locationClient.stopLocation();
    }

    public void start() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

}

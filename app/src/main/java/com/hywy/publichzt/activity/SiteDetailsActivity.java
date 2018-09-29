package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.MyProgressDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.WaterClassifyAdapter;
import com.hywy.publichzt.entity.Reservoir;
import com.hywy.publichzt.entity.RiverCourse;
import com.hywy.publichzt.entity.RiverCourseChart;
import com.hywy.publichzt.entity.WaterClassify;
import com.hywy.publichzt.entity.WaterQuality;
import com.hywy.publichzt.entity.WaterQualityChart;
import com.hywy.publichzt.entity.WaterRain;
import com.hywy.publichzt.entity.WaterRainChart;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetReservoirBytimeTask;
import com.hywy.publichzt.task.GetRiverCourseBytimeTask;
import com.hywy.publichzt.task.GetWaterBytimeTask;
import com.hywy.publichzt.task.GetWaterQualityBytimeTask;
import com.hywy.publichzt.view.chart.DayAxisValueFormatter;
import com.hywy.publichzt.view.customer.BannerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class SiteDetailsActivity extends BaseToolbarActivity {
    @Bind(R.id.bannerview)
    BannerView bannerView;
    @Bind(R.id.site_name)
    TextView siteName;
    @Bind(R.id.site_num)
    TextView siteNum;
    @Bind(R.id.tv_locate)
    TextView tvLoate;
    @Bind(R.id.tv_river)
    TextView riverName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_site_address)
    TextView siteAddress;
    @Bind(R.id.tv_organ)
    TextView tvOrgan;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_phone)
    TextView tvPhone;

    //水雨情
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.end_time)
    TextView endTime;
    @Bind(R.id.rain_num)
    TextView rainNum;
    @Bind(R.id.iv_search)
    ImageView ivSearch;

    //水库水文
    @Bind(R.id.tv_time_r)
    TextView tvTimeReservoir;
    @Bind(R.id.water_height)
    TextView waterHeight;
    @Bind(R.id.stream_day)
    TextView streamDay;

    //河道水文
    @Bind(R.id.tv_time_rivercourse)
    TextView tvTimeRivercourse;
    @Bind(R.id.water_height_now)
    TextView waterHeightN;

    //水质
    @Bind(R.id.tv_time_wq)
    TextView tvTimeWq;
    @Bind(R.id.tv_temp_r)
    TextView tvTemp;
    @Bind(R.id.gridview)
    GridView gridView;

    @Bind(R.id.chart)
    BarChart mBarChart;
    @Bind(R.id.line_chart)
    LineChart mLineChart;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.tv_right)
    TextView tvRight;

    @Bind(R.id.btn1)
    RadioButton btn1;
    @Bind(R.id.btn2)
    RadioButton btn2;
    @Bind(R.id.btn3)
    RadioButton btn3;
    @Bind(R.id.edit_start)
    EditText etStart;
    @Bind(R.id.edit_end)
    EditText etEnd;

    @Bind(R.id.rgp_water)
    RadioGroup rgp_water;
    @Bind(R.id.btn_sw)
    RadioButton btn_sw;
    @Bind(R.id.btn_ll)
    RadioButton btn_ll;

    @Bind(R.id.rgp_water_quality)
    RadioGroup rgp_water_quality;
    @Bind(R.id.btn_ddl)
    RadioButton btn_ddl;
    @Bind(R.id.btn_zd)
    RadioButton btn_zd;
    @Bind(R.id.btn_rjy)
    RadioButton btn_rjy;
    @Bind(R.id.btn_ph)
    RadioButton btn_ph;
    @Bind(R.id.btn_wd)
    RadioButton btn_wd;


    private Parcelable parcelable;

    private WaterClassifyAdapter mAdapter;
    private DayAxisValueFormatter xAxisFormatter;
    private WaterRain waterRain;

    private Task task;
    private Map<String, Object> params;

    private double lat, lng;

    List<View> mItems;

    ImageView[] mBottomImgs;


    public static void startAction(Activity activity, Parcelable parcelable) {
        Intent intent = new Intent(activity, SiteDetailsActivity.class);
        intent.putExtra("parcelable", parcelable);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_details);
        init();
        initChart();
        initLineChart();
        initData();
        initListeners();
    }


    private void setTextView(TextView textView, String str) {
        if (StringUtils.hasLength(str)) {
            textView.setText(str);
        }
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.site_detail)));
        parcelable = getIntent().getParcelableExtra("parcelable");
        params = new HashMap<>();

        mItems = new ArrayList<>();
        addImageView();


        mBottomImgs = new ImageView[mItems.size()];
        bannerView.startLoop(true);
        bannerView.setViewList(mItems);
    }

    /**
     * 添加图片
     */
    private void addImageView() {
        ImageView view0 = new ImageView(this);
        view0.setImageResource(R.drawable.ic_banner_img1);
        ImageView view1 = new ImageView(this);
        view1.setImageResource(R.drawable.ic_banner_img2);
        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.drawable.ic_banner_img3);

        view0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(view0);
        mItems.add(view1);
        mItems.add(view2);

    }

    private void initData() {
        if (parcelable instanceof WaterRain) {
            initWaterRain();
            findViewById(R.id.layout_rain_info).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_reservoir_info).setVisibility(View.GONE);
            findViewById(R.id.layout_rivercourse_info).setVisibility(View.GONE);
            findViewById(R.id.layout_water_quality_info).setVisibility(View.GONE);

            findViewById(R.id.bar_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.line_layout).setVisibility(View.GONE);

        } else if (parcelable instanceof Reservoir) {
            initReservoir();
            findViewById(R.id.layout_rain_info).setVisibility(View.GONE);
            findViewById(R.id.layout_reservoir_info).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_rivercourse_info).setVisibility(View.GONE);
            findViewById(R.id.layout_water_quality_info).setVisibility(View.GONE);

            findViewById(R.id.bar_layout).setVisibility(View.GONE);
            findViewById(R.id.line_layout).setVisibility(View.VISIBLE);

            findViewById(R.id.rgp_water).setVisibility(View.VISIBLE);
        } else if (parcelable instanceof RiverCourse) {
            initRiverCourse();
            findViewById(R.id.layout_rain_info).setVisibility(View.GONE);
            findViewById(R.id.layout_reservoir_info).setVisibility(View.GONE);
            findViewById(R.id.layout_rivercourse_info).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_water_quality_info).setVisibility(View.GONE);

            findViewById(R.id.bar_layout).setVisibility(View.GONE);
            findViewById(R.id.line_layout).setVisibility(View.VISIBLE);
        } else if (parcelable instanceof WaterQuality) {
            initWaterQuality();
            findViewById(R.id.layout_rain_info).setVisibility(View.GONE);
            findViewById(R.id.layout_reservoir_info).setVisibility(View.GONE);
            findViewById(R.id.layout_rivercourse_info).setVisibility(View.GONE);
            findViewById(R.id.layout_water_quality_info).setVisibility(View.VISIBLE);

            findViewById(R.id.bar_layout).setVisibility(View.GONE);
            findViewById(R.id.line_layout).setVisibility(View.VISIBLE);

            findViewById(R.id.rgp_water_quality).setVisibility(View.VISIBLE);
        }

    }

    /***
     * 初始化水雨情信息
     */
    private void initWaterRain() {
        waterRain = (WaterRain) parcelable;
        setTextView(siteName, waterRain.getSTNM());
        setTextView(siteNum, waterRain.getSTCD());
        setTextView(riverName, waterRain.getRVNM());
        setTextView(tvAddress, waterRain.getADNM());
        setTextView(siteAddress, waterRain.getSTLC());
        setTextView(tvOrgan, waterRain.getADMAUTH());
        setTextView(tvUser, waterRain.getPEOPLE());
        setTextView(tvPhone, waterRain.getTEL());

        if (StringUtils.hasLength(waterRain.getLTTD()) && StringUtils.hasLength(waterRain.getLGTD())) {
            lat = Double.parseDouble(waterRain.getLTTD());
            lng = Double.parseDouble(waterRain.getLGTD());
        }

        setTextView(endTime, DateUtils.GetNowTimeChinesne("yyyy/MM/dd HH:00:00"));
        setTextView(startTime, DateUtils.GetPevTimeChinesne("yyyy/MM/dd HH:00:00"));
        setTextView(rainNum, waterRain.getDRP() + "mm");

        tvLeft.setText("降雨量mm");
        tvRight.setText("降雨量mm");
        task = new GetWaterBytimeTask(this);
        params.put("STCD", waterRain.getSTCD());
//        initChartData(waterRain);
    }

    /***
     * 初始化水库水文信息
     */
    private void initReservoir() {
        Reservoir reservoir = (Reservoir) parcelable;
        setTextView(siteName, reservoir.getSTNM());
        setTextView(siteNum, reservoir.getSTCD());
        setTextView(riverName, reservoir.getRVNM());
        setTextView(tvAddress, reservoir.getADNM());
        setTextView(siteAddress, reservoir.getSTLC());
        setTextView(tvOrgan, reservoir.getADMAUTH());
        setTextView(tvUser, reservoir.getPEOPLE());
        setTextView(tvPhone, reservoir.getTEL());
        setTextView(tvTimeReservoir, reservoir.getTM());
        setTextView(waterHeight, reservoir.getRZ() + "mm");
        setTextView(streamDay, reservoir.getOTQ() + "m³/s");

        if (StringUtils.hasLength(reservoir.getLTTD()) && StringUtils.hasLength(reservoir.getLGTD())) {
            lat = Double.parseDouble(reservoir.getLTTD());
            lng = Double.parseDouble(reservoir.getLGTD());
        }

        task = new GetReservoirBytimeTask(this);
        params.put("STCD", reservoir.getSTCD());
    }

    /***
     * 初始化水河道文信息
     */
    private void initRiverCourse() {
        RiverCourse riverCourse = (RiverCourse) parcelable;
        setTextView(siteName, riverCourse.getSTNM());
        setTextView(siteNum, riverCourse.getSTCD());
        setTextView(riverName, riverCourse.getRVNM());
        setTextView(tvAddress, riverCourse.getADNM());
        setTextView(siteAddress, riverCourse.getSTLC());
        setTextView(tvOrgan, riverCourse.getADMAUTH());
        setTextView(tvUser, riverCourse.getPEOPLE());
        setTextView(tvPhone, riverCourse.getTEL());
        setTextView(tvTimeRivercourse, riverCourse.getTM());
        setTextView(waterHeightN, riverCourse.getZ() + "mm");

        if (StringUtils.hasLength(riverCourse.getLTTD()) && StringUtils.hasLength(riverCourse.getLGTD())) {
            lat = Double.parseDouble(riverCourse.getLTTD());
            lng = Double.parseDouble(riverCourse.getLGTD());
        }

        tvLeft.setText("水位");
        tvRight.setText("水位");
        task = new GetRiverCourseBytimeTask(this);
        params.put("STCD", riverCourse.getSTCD());
    }

    /***
     * 初始化水质信息
     */
    private void initWaterQuality() {
        WaterQuality waterQuality = (WaterQuality) parcelable;
        mAdapter = new WaterClassifyAdapter(this);
        gridView.setAdapter(mAdapter);
        setTextView(siteName, waterQuality.getSTNM());
        setTextView(siteNum, waterQuality.getSTCD());
        setTextView(riverName, waterQuality.getRVNM());
        setTextView(tvAddress, waterQuality.getADNM());
        setTextView(siteAddress, waterQuality.getSTLC());
        setTextView(tvOrgan, waterQuality.getADMAUTH());
        setTextView(tvUser, waterQuality.getPEOPLE());
        setTextView(tvPhone, waterQuality.getTEL());
        setTextView(tvTimeWq, waterQuality.getSPT());
        setTextView(tvTemp, waterQuality.getWT() + "℃");

        if (StringUtils.hasLength(waterQuality.getLTTD()) && StringUtils.hasLength(waterQuality.getLGTD())) {
            lat = Double.parseDouble(waterQuality.getLTTD());
            lng = Double.parseDouble(waterQuality.getLGTD());
        }

        List<WaterClassify> list = new ArrayList<>();
        WaterClassify waterClassify1 = new WaterClassify(getString(R.string.text_zonglin), Double.parseDouble(waterQuality.getTP()), WaterClassify.getTPtype(Double.parseDouble(waterQuality.getTP())));
        WaterClassify waterClassify2 = new WaterClassify(getString(R.string.text_fuhuawu), Double.parseDouble(waterQuality.getF()), WaterClassify.getFtype(Double.parseDouble(waterQuality.getF())));
        WaterClassify waterClassify4 = new WaterClassify(getString(R.string.text_gaomeng), Double.parseDouble(waterQuality.getCODMN()), WaterClassify.getGmsytype(Double.parseDouble(waterQuality.getCODMN())));
        WaterClassify waterClassify3 = new WaterClassify(getString(R.string.text_rongjieyang), waterQuality.getDOX(), WaterClassify.getRjytype(waterQuality.getDOX()));
        list.add(waterClassify1);
        list.add(waterClassify2);
        list.add(waterClassify3);
        list.add(waterClassify4);
        mAdapter.setList(list);

        task = new GetWaterQualityBytimeTask(this);
        params.put("STCD", waterQuality.getSTCD());

    }


    @OnClick(R.id.tv_locate)
    public void toMap() {
        ShowAddressActivity.startAction(this, lng, lat);
    }

    @OnClick(R.id.iv_search)
    public void toSearch() {
        if (StringUtils.hasLength(etStart.getText().toString()) && StringUtils.hasLength(etEnd.getText().toString())) {
            initChartData();
        }
    }

    @OnClick({R.id.edit_start, R.id.edit_end})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_start:
                chooseDate(etStart);
                break;
            case R.id.edit_end:
                chooseDate(etEnd);
                break;
        }
    }

    private void chooseDate(final EditText tv) {
        if (btn1.isChecked()) {
            showDateDialog(tv, MyProgressDialog.DIALOG_DATEPICKER_YM);
        } else if (btn2.isChecked()) {
            showDateDialog(tv, MyProgressDialog.DIALOG_DATEPICKER);
        } else {
            showTimeDialog(tv);
        }
    }

    private void showDateDialog(final EditText tv, final int mode) {
        DialogTools.showDateDialog(this, System.currentTimeMillis(), mode, new MyProgressDialog.OnDatePickerClickListener() {
            @Override
            public void datePickerConfirmClick(long dateTime) {
                if (mode == MyProgressDialog.DIALOG_DATEPICKER_YM) {
                    tv.setText(DateUtils.transforMillToMoth(dateTime));
                } else {
                    tv.setText(DateUtils.transforMillToDate(dateTime));
                }
            }

            @Override
            public void datePickerCancelClick() {

            }
        });
    }

    /**
     * 选择时间和日期
     *
     * @param tv
     */
    private void showTimeDialog(final EditText tv) {
        final MyProgressDialog progressDialog = DialogTools.showDateDialog(this, System.currentTimeMillis(), MyProgressDialog.DIALOG_DATEPICKER, new MyProgressDialog.OnDatePickerClickListener() {
            @Override
            public void datePickerConfirmClick(long dateTime) {
                MyProgressDialog myProgressDialog = new MyProgressDialog(SiteDetailsActivity.this
                        , MyProgressDialog.DIALOG_TIMEPICKER);
                myProgressDialog.setmTimePickerClickListener(new MyProgressDialog.OnTimePickerClickListener() {
                    @Override
                    public void timePickerClick(long dateTime) {
                        tv.setText(DateUtils.transforHourMill(dateTime));
                    }
                });
                //yyyy-MM-dd
                String date = DateUtils.transforMillToDate(dateTime);
                long time = DateUtils.transforDateToMill(date);
                myProgressDialog.showDialog(time);
            }

            @Override
            public void datePickerCancelClick() {

            }
        });
    }

    private void initListeners() {
        rgp_water.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checketBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                tvLeft.setText(checketBtn.getText().toString());
                tvRight.setText(checketBtn.getText().toString());
            }
        });

        rgp_water_quality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checketBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                tvLeft.setText(checketBtn.getText().toString());
                tvRight.setText(checketBtn.getText().toString());
            }
        });
    }

    // 设置显示的样式
    private void initLineChart() {
        mLineChart.setDrawBorders(false);  //是否在折线图上添加边框
        mLineChart.getDescription().setEnabled(false);
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview

        // enable / disable grid background
        mLineChart.setDrawGridBackground(false); // 是否显示表格颜色
        mLineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        mLineChart.getLegend().setEnabled(false);
        // enable touch gestures
        mLineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        mLineChart.setDragEnabled(false);// 是否可以拖拽
        mLineChart.setScaleEnabled(false);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(false);//

//        mLineChart.setBackgroundColor(color);// 设置背景

        // get the legend (only possible after setting data)
//        Legend mLegend = mLineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
//        // modify the legend ...
//        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
//        mLegend.setFormSize(6f);//
//        mLegend.setTextSize(14);//字体
//        mLegend.setTextColor(getResources().getColor(R.color.font_3));// 颜色
//      mLegend.setTypeface(mTf);// 字体

//        mLineChart.animateX(2500); // 立即执行的动画,x轴

        mLineChart.setPinchZoom(false);
        mLineChart.getAxisLeft().setDrawGridLines(false);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawGridLines(true);
//        xAxis.setLabelCount(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-30);//文字斜的显示
        xAxis.enableGridDashedLine(10f, 5f, 0f);
//        xAxis.setAxisMinimum(0.7f);
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setLabelCount(5);
        leftAxis.setSpaceTop(15f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f); //设置横向表格为虚线
        leftAxis.setAxisMinimum(0f);//设置柱状图底部从0开始
        leftAxis.enableGridDashedLine(10f, 5f, 0f);
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return "" + (int) value;//这句是重点!y轴设置为整数显示
//            }
//        });

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);//设置柱状图底部从0开始
        rightAxis.enableGridDashedLine(10f, 5f, 0f);
    }


    private void initChart() {
        mBarChart.setDrawBorders(false);  ////是否在折线图上添加边框
        Description description = new Description();
        description.setText("测试一下");
        mBarChart.setDescription(description);// 数据描述
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
//        mBarChart.setNoDataText(getResources().getString(R.string.no_data));
        mBarChart.setMaxVisibleValueCount(60);
//        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setScaleXEnabled(false);
        mBarChart.setScaleYEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);
        mBarChart.setDoubleTapToZoomEnabled(false);
        mBarChart.setHighlightPerDragEnabled(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.getAxisLeft().setDrawGridLines(false);
        // add a nice and smooth animation
        mBarChart.getLegend().setEnabled(false);
        // apply styling
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawGridBackground(false);

        xAxisFormatter = new DayAxisValueFormatter(mBarChart);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(true);
//        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.enableGridDashedLine(10f, 5f, 0f);
//        xAxis.enableAxisLineDashedLine(10f, 5f, 0f);//
        xAxis.setLabelRotationAngle(-30);//文字斜的显示

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);//设置x轴刻度个数
        leftAxis.setSpaceTop(15f);
        leftAxis.enableGridDashedLine(10f, 5f, 0f);
        leftAxis.setAxisMinimum(0f);//设置柱状图底部从0开始

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setLabelCount(5, false);//设置x轴刻度个数
        rightAxis.setSpaceTop(15f);
        rightAxis.enableGridDashedLine(10f, 5f, 0f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        mBarChart.setFitBars(true);
//        time = Calendar.getInstance().get(Calendar.YEAR);
//        this.date.setText(time + "");
//        timeSub.setOnClickListener(this);
//        timeAdd.setOnClickListener(this);
    }

    private void initChartData() {
        SpringViewHandler handler = new SpringViewHandler(this);
        Map<String, Object> params = getParams();
        handler.request(params, task);
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                String value = (String) result.get(Key.CHART);
                if (value.equals("WaterRainChart")) {
                    List<WaterRainChart> list = (List<WaterRainChart>) result.get(Key.RESULT);
                    if (StringUtils.isNotNullList(list))
                        loadData(list);
                } else if (value.equals("RiverCourseChart")) {
                    List<RiverCourseChart> list = (List<RiverCourseChart>) result.get(Key.RESULT);
                    if (StringUtils.isNotNullList(list)) {
                        if (btn_sw.isChecked()) {
                            loadLineChartData(list, "Z03");
                        } else {
                            loadLineChartData(list, "Q03");
                        }
                    }
                } else if (value.equals("WaterQualityChart")) {
                    List<WaterQualityChart> list = (List<WaterQualityChart>) result.get(Key.RESULT);
                    if (StringUtils.isNotNullList(list)) {
                        if (btn_ddl.isChecked()) {
                            loadWqLineChartData(list, "TP");
                        } else if (btn_zd.isChecked()) {
                            loadWqLineChartData(list, "F");
                        } else if (btn_ph.isChecked()) {
                            loadWqLineChartData(list, "CODMN");
                        } else if (btn_rjy.isChecked()) {
                            loadWqLineChartData(list, "DOX");
                        } else if (btn_wd.isChecked()) {
                            loadWqLineChartData(list, "WT");
                        }
                    }
                }

            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
            }
        });

    }


    private Map<String, Object> getParams() {
        if (btn1.isChecked()) {
            if (rgp_water_quality.getVisibility() == View.VISIBLE) {
                params.put("SDID", 2);//0-时、1-日、2-月  水质月传 2
            } else
                params.put("SDID", 3);//0-时、1-日、3-月
        } else if (btn2.isChecked()) {
            params.put("SDID", 1);//0-时、1-日、3-月
        } else {
            params.put("SDID", 0);//0-时、1-日、3-月
        }
        params.put("StartTM", etStart.getText().toString());
        params.put("EndTM", etEnd.getText().toString());
        return params;
    }

    private void loadData(List<WaterRainChart> charts) {
        int size = charts.size();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        int colors[] = new int[size];
        String[] months = new String[size];
        for (int i = 0; i < size; i++) {
            WaterRainChart chart = charts.get(i);
            BarEntry entry = new BarEntry(i, Float.parseFloat(chart.getD01()));
            entry.setData(chart);
            yVals.add(entry);
            months[i] = chart.getTM();
            colors[i] = ContextCompat.getColor(this, R.color.primary_default);
        }

        xAxisFormatter.setMonths(months);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 5f, 0);
        xAxis.setLabelCount(size);
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(xAxisFormatter);

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setColors(colors);
//        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0.00");
                if (value == 0) {
                    return "";
                } else {
                    return format.format(value);
                }
            }
        });
        data.setBarWidth(0.7f);

        mBarChart.setData(data);
        mBarChart.invalidate();
    }

    /***
     * 折线图
     * @param charts
     */
    private void loadLineChartData(List<RiverCourseChart> charts, String name) {
        int size = charts != null ? charts.size() : 0;
        ArrayList<Entry> yVals = new ArrayList<>();
        String[] months = new String[size];
        for (int i = 0; i < size; i++) {
            RiverCourseChart chart = charts.get(i);
            Entry entry = new Entry();
            if (name.equals("Z03")) {
                entry = new Entry(i, Float.parseFloat(chart.getZ03()));
            } else if (name.equals("Q03")) {
                entry = new Entry(i, Float.parseFloat(chart.getQ03()));
            }
            entry.setData(chart);
            yVals.add(entry);

            months[i] = chart.getTM();
        }
        DayAxisValueFormatter formatter = new DayAxisValueFormatter(mLineChart);
        formatter.setMonths(months);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setLabelCount(size > 1 ? size - 1 : 0);
        xAxis.setValueFormatter(formatter);

        generateData(yVals);
    }


    /***
     * 折线图~水质
     * @param charts
     */
    private void loadWqLineChartData(List<WaterQualityChart> charts, String name) {
        int size = charts != null ? charts.size() : 0;
        ArrayList<Entry> yVals = new ArrayList<>();
        String[] months = new String[size];
        for (int i = 0; i < size; i++) {
            WaterQualityChart chart = charts.get(i);
            Entry entry = new Entry();
            if (name.equals("TP")) {
                entry = new Entry(i, Float.parseFloat(chart.getAVG_TP()));
            } else if (name.equals("F")) {
                entry = new Entry(i, Float.parseFloat(chart.getAVG_F()));
            } else if (name.equals("CODMN")) {
                entry = new Entry(i, Float.parseFloat(chart.getAVG_CODMN()));
            } else if (name.equals("DOX")) {
                entry = new Entry(i, Float.parseFloat(chart.getAVG_DOX()));
            } else if (name.equals("WT")) {
                entry = new Entry(i, Float.parseFloat(chart.getAVG_WT()));
            }
            entry.setData(chart);
            yVals.add(entry);

            months[i] = chart.getSPT();
        }
        DayAxisValueFormatter formatter = new DayAxisValueFormatter(mLineChart);
        formatter.setMonths(months);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setLabelCount(size > 1 ? size - 1 : 0);
        xAxis.setValueFormatter(formatter);

        generateData(yVals);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateData(ArrayList<Entry> entries) {
        mLineChart.animateY(700);
        LineDataSet d = new LineDataSet(entries, "");
//        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setDrawFilled(true);//设置是否填充颜色
        d.setLineWidth(2f);
        d.setFillAlpha(65);
        d.setFillColor(getResources().getColor(R.color.primary_default));//设置填充颜色
        d.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//设置平滑曲线
        d.setColor(getResources().getColor(R.color.primary_default));//设置曲线颜色
        d.setHighlightEnabled(true);// 不显示定位线
        d.setDrawCircles(false);//设置有圆点
        d.setDrawValues(true);
//        d.setCircleColor(R.color.material_color_red_500);//设置圆点颜色

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d);
        LineData cd = new LineData(sets);
        cd.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0.00");
                if (value == 0) {
                    return "";
                } else {
                    return format.format(value);
                }
            }
        });
        mLineChart.setData(cd);
        return cd;
    }

}

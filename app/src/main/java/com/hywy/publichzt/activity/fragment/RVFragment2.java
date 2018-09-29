package com.hywy.publichzt.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cs.common.base.BaseFragment;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.MyProgressDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.WaterClassifyAdapter;
import com.hywy.publichzt.entity.RiverDetails;
import com.hywy.publichzt.entity.WaterClassify;
import com.hywy.publichzt.entity.WaterQualityChart;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetWaterQualityBytimeTask;
import com.hywy.publichzt.view.chart.DayAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 河道水质
 *
 * @author Superman
 */
public class RVFragment2 extends BaseFragment {
    @Bind(R.id.name)
    TextView tvName;
    @Bind(R.id.site_name)
    TextView steName;
    @Bind(R.id.iv_status)
    ImageView ivStatus;

    @Bind(R.id.tv_time_wq)
    TextView tvTime;
    @Bind(R.id.tv_temp_r)
    TextView tvTemp;
    @Bind(R.id.gridview)
    GridView gridView;

    @Bind(R.id.line_chart)
    LineChart mLineChart;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.tv_right)
    TextView tvRight;

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


    private RiverDetails.SZBaseBean szBaseBean;
    private WaterClassifyAdapter mAdapter;
    private Map<String, Object> params;

    public static RVFragment2 newInstance(RiverDetails.SZBaseBean szBaseBean) {
        Bundle args = new Bundle();
        args.putParcelable("szBaseBean", szBaseBean);
        RVFragment2 fragment = new RVFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shuizhi;
    }

    @Override
    protected void initView() {
        mAdapter = new WaterClassifyAdapter(getActivity());
        params = new HashMap<>();
        gridView.setAdapter(mAdapter);

        getActivity().findViewById(R.id.bar_layout).setVisibility(View.GONE);
        getActivity().findViewById(R.id.line_layout).setVisibility(View.VISIBLE);

        getActivity().findViewById(R.id.rgp_water_quality).setVisibility(View.VISIBLE);

        initLineChart();

        if (getArguments() != null) {
            szBaseBean = getArguments().getParcelable("szBaseBean");
        }

        if (szBaseBean != null) {
            if (StringUtils.hasLength(szBaseBean.getREACH_NAME())) {
                tvName.setText(szBaseBean.getREACH_NAME());
            }

            if (StringUtils.hasLength(szBaseBean.getWATER_TYPE())) {
                switch (szBaseBean.getWATER_TYPE()) {
                    case "1":
                        ivStatus.setImageResource(R.drawable.ic_i);
                        break;
                    case "2":
                        ivStatus.setImageResource(R.drawable.ic_ii);
                        break;
                    case "3":
                        ivStatus.setImageResource(R.drawable.ic_iii);
                        break;
                    case "4":
                        ivStatus.setImageResource(R.drawable.ic_iv);
                        break;
                    case "5":
                        ivStatus.setImageResource(R.drawable.ic_v);
                        break;
                    case "6":
                        ivStatus.setImageResource(R.drawable.ic_v_);
                        break;
                }
            }

            setTextView(steName, szBaseBean.getSTNM());
            setTextView(tvTime, szBaseBean.getSPT());
            setTextView(tvTemp, szBaseBean.getWT() + "℃");//温度

            List<WaterClassify> list = new ArrayList<>();
            WaterClassify waterClassify1;
            WaterClassify waterClassify2;
            WaterClassify waterClassify4;
            WaterClassify waterClassify3;
            if (StringUtils.hasLength(szBaseBean.getTP())) {
                waterClassify1 = new WaterClassify(getString(R.string.text_zonglin), Double.parseDouble(szBaseBean.getTP()), WaterClassify.getTPtype(Double.parseDouble(szBaseBean.getTP())));
            } else
                waterClassify1 = new WaterClassify(getString(R.string.text_zonglin), 0.00,WaterClassify.getTPtype(0.00));
            if (StringUtils.hasLength(szBaseBean.getF())) {
                waterClassify2 = new WaterClassify(getString(R.string.text_fuhuawu), Double.parseDouble(szBaseBean.getF()), WaterClassify.getFtype(Double.parseDouble(szBaseBean.getF())));
            } else
                waterClassify2 = new WaterClassify(getString(R.string.text_fuhuawu), 0.00, WaterClassify.getFtype(0.00));
            if (StringUtils.hasLength(szBaseBean.getCODMN())) {
                waterClassify4 = new WaterClassify(getString(R.string.text_gaomeng), Double.parseDouble(szBaseBean.getCODMN()), WaterClassify.getGmsytype(Double.parseDouble(szBaseBean.getCODMN())));
            } else
                waterClassify4 = new WaterClassify(getString(R.string.text_gaomeng),0.00, WaterClassify.getGmsytype(0.00));
            if (StringUtils.hasLength(szBaseBean.getDOX())) {
                waterClassify3 = new WaterClassify(getString(R.string.text_rongjieyang), Double.parseDouble(szBaseBean.getDOX()), WaterClassify.getRjytype(Double.parseDouble(szBaseBean.getDOX())));
            } else
                waterClassify3 = new WaterClassify(getString(R.string.text_rongjieyang),  0.00,WaterClassify.getRjytype(0.00));

            list.add(waterClassify1);
            list.add(waterClassify2);
            list.add(waterClassify3);
            list.add(waterClassify4);
            mAdapter.setList(list);

        }

        initListeners();
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

    private void initListeners() {
        rgp_water_quality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checketBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                tvLeft.setText(checketBtn.getText().toString());
                tvRight.setText(checketBtn.getText().toString());
            }
        });
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

    @OnClick(R.id.iv_search)
    public void toSearch() {
        initChartData();
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
        DialogTools.showDateDialog(getActivity(), System.currentTimeMillis(), mode, new MyProgressDialog.OnDatePickerClickListener() {
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
        final MyProgressDialog progressDialog = DialogTools.showDateDialog(getActivity(), System.currentTimeMillis(), MyProgressDialog.DIALOG_DATEPICKER, new MyProgressDialog.OnDatePickerClickListener() {
            @Override
            public void datePickerConfirmClick(long dateTime) {
                MyProgressDialog myProgressDialog = new MyProgressDialog(getActivity()
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


    private void initChartData() {
        SpringViewHandler handler = new SpringViewHandler(getActivity());
        Map<String, Object> params = getParams();
        handler.request(params, new GetWaterQualityBytimeTask(getActivity()));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                String value = (String) result.get(Key.CHART);
                if (value.equals("WaterQualityChart")) {
                    List<WaterQualityChart> list = (List<WaterQualityChart>) result.get(Key.RESULT);
                    if (StringUtils.isNotNullList(list)) {
                        if (btn_ddl.isChecked()) {
                            loadWqLineChartData(list, "TP");
                        } else if (btn_zd.isChecked()) {
                            loadWqLineChartData(list, "F");
                        } else if (btn_ph.isChecked()) {
                            loadWqLineChartData(list, "DOX");
                        } else if (btn_rjy.isChecked()) {
                            loadWqLineChartData(list, "CODMN");
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


    private Map<String, Object> getParams() {
        if (btn1.isChecked()) {
            params.put("SDID", 2);//0-时、1-日、2-月  水质月传 2
        } else if (btn2.isChecked()) {
            params.put("SDID", 1);//0-时、1-日、3-月
        } else {
            params.put("SDID", 0);//0-时、1-日、3-月
        }
        params.put("StartTM", etStart.getText().toString());
        params.put("EndTM", etEnd.getText().toString());
        params.put("STCD", szBaseBean.getSTCD());
        return params;
    }

}

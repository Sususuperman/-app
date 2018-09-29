package com.cs.common.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.cs.android.common.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class MyProgressDialog<T> extends Dialog {
    /**
     * 日期选择器
     */
    public static final int DIALOG_DATEPICKER = 4;

    /**
     * 日期选择器,只显示年月
     */
    public static final int DIALOG_DATEPICKER_YM = 6;

    /**
     * 时间选择器
     */
    public static final int DIALOG_TIMEPICKER = 5;

    public int model = 0;
    private Activity mActivity;

    /**
     * 默认为等待框相关设置
     */
    private String title;//标题
    private String waitContent;//加载框显示内容
    private boolean isCancelable = true;//点击返回是否取消等待框显示

    /**
     * 确认框相关设置
     */
    private int confirmBackgrounp;//确认背景框颜色
    private int cancelBackgroup;//取消背景框颜色
    private int titleColor;//标题颜色
    private String confirmBtntxt;//确认文字
    private String cancelBtntxt;
    private int confirmBtntxtColor;
    private int cancelBtntxtColor;

    /**
     * 设置升级相关设置
     */
    private String Upgrade_content;//设置升级加载框内容
    private String Upgrade_Size;//设置升级apk大小

    public MyProgressDialog(Activity activity) {
        super(activity, R.style.loading_dialog);
        this.mActivity = activity;
    }

    public MyProgressDialog(Activity activity, int model) {
        super(activity);
        this.mActivity = activity;
        this.model = model;

        this.titleColor = R.color.primary_default;
        this.confirmBtntxt = "确定";
        this.cancelBtntxt = "取消";
        this.cancelBtntxtColor = R.color.primary_default;
        this.confirmBtntxtColor = R.color.primary_default;
    }


    /**
     * 显示确认框、日期选择器、升级弹出框。调用该方法
     */
    public void showDialog() {
        this.showDialog(null, 0);
    }

    /**
     * 显示时间选择器，调用该方法
     *
     * @param dateTime
     */
    public void showDialog(long dateTime) {
        this.showDialog(null, dateTime);
    }

    private void showDialog(List<T> list, long datetime) {
        switch (model) {
            case DIALOG_DATEPICKER:
                initDatePickerDialog(DIALOG_DATEPICKER);
                break;
            case DIALOG_TIMEPICKER:
                initTimePicker(datetime);
                break;
            case DIALOG_DATEPICKER_YM:
                initDatePickerDialog(DIALOG_DATEPICKER_YM);
                break;
        }
    }


    /**
     * 日期选择器
     */
    boolean isCancel = false;

    private void initDatePickerDialog(int model) {
        long date = System.currentTimeMillis();
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date);
        if (date != 0) {
            calendar.setTimeInMillis(date);
        }

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dg = new DatePickerDialog(mActivity, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int y, int m, int d) {
                if (!isCancel) {
                    Calendar c = Calendar.getInstance();
                    GregorianCalendar ca = new GregorianCalendar(y, m, d);
                    ca.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
                    ca.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
                    mDatePickerClickListener.datePickerConfirmClick(ca.getTimeInMillis());
                    isCancel = false;
                }
            }
        }, year, month, day);

        if(model==DIALOG_DATEPICKER_YM){
            ((ViewGroup) ((ViewGroup) dg.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        }

        dg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isCancel = true;
                mDatePickerClickListener.datePickerCancelClick();
            }
        });
        dg.show();
    }

    /**
     * 时间选择器
     *
     * @param date 如果为0，则只返回时间
     */
    private void initTimePicker(final long date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = View.inflate(mActivity, R.layout.diaolg_time_picker, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        builder.setView(view);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
        builder.setTitle("选择时间");
        builder.setPositiveButton("确  定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long dateTime = 0;
                if(date != 0)
                    dateTime = date + timePicker.getCurrentHour()*3600*1000 + timePicker.getCurrentMinute()*60*1000;
                else {
//                    dateTime = timePicker.getCurrentHour()*3600*1000 + timePicker.getCurrentMinute()*60*1000;
                    //因为时区的差异，获取的时间点比北京时间多了8小时，所以-8
                    dateTime = (timePicker.getCurrentHour()-8)*3600*1000 + timePicker.getCurrentMinute()*60*1000;

                }
                mTimePickerClickListener.timePickerClick(dateTime);
                dialog.cancel();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 确认框监听事件
     */
    private OnDialogClickListener mDialogClickListener;

    public interface OnDialogClickListener {
        void confirmClick();

        void cancelClick();
    }

    public OnDialogClickListener getDialogClickListener() {
        return mDialogClickListener;
    }

    public void setDialogClickListener(OnDialogClickListener mClickListener) {
        this.mDialogClickListener = mClickListener;
    }


    /**
     * 日期选择器监听事件
     */
    private OnDatePickerClickListener mDatePickerClickListener;

    public interface OnDatePickerClickListener {
        void datePickerConfirmClick(long dateTime);

        void datePickerCancelClick();
    }

    public OnDatePickerClickListener getmDatePickerClickListener() {
        return mDatePickerClickListener;
    }

    public void setmDatePickerClickListener(OnDatePickerClickListener mDatePickerClickListener) {
        this.mDatePickerClickListener = mDatePickerClickListener;
    }


    /**
     * 时间选择器监听事件
     */
    private OnTimePickerClickListener mTimePickerClickListener;

    public interface OnTimePickerClickListener {
        void timePickerClick(long dateTime);
    }

    public OnTimePickerClickListener getmTimePickerClickListener() {
        return mTimePickerClickListener;
    }

    public void setmTimePickerClickListener(
            OnTimePickerClickListener mTimePickerClickListener) {
        this.mTimePickerClickListener = mTimePickerClickListener;
    }


    public String getWaitContent() {
        return waitContent;
    }

    public void setWaitContent(String waitContent) {
        this.waitContent = waitContent;
    }

    public void setWaitContent(int id) {
        this.setWaitContent(mActivity.getResources().getString(id));
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }


    public int getCancelBackgroup() {
        return cancelBackgroup;
    }

    public void setCancelBackgroup(int cancelBackgroup) {
        this.cancelBackgroup = cancelBackgroup;
    }

    public int getConfirmBackgrounp() {
        return confirmBackgrounp;
    }

    /**
     * 设置确认按钮背景
     *
     * @param confirmBackgrounp
     */
    public void setConfirmBackgrounp(int confirmBackgrounp) {
        this.confirmBackgrounp = confirmBackgrounp;
    }

    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setDialogTitle(String title) {
        this.title = title;
    }

    /**
     * 设置标题
     *
     * @param id
     */
    public void setDialogTitle(int id) {
        this.title = mActivity.getResources().getString(id);
    }

    public int getTitleColor() {
        return titleColor;
    }

    /**
     * 设置标题颜色
     *
     * @param titleColor
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getConfirmBtntxt() {
        return confirmBtntxt;
    }

    /**
     * 设置确认按钮文字
     *
     * @param confirmBtntxt
     */
    public void setConfirmBtntxt(String confirmBtntxt) {
        this.confirmBtntxt = confirmBtntxt;
    }

    /**
     * 设置确认按钮文字
     *
     * @param id
     */
    public void setConfirmBtntxt(int id) {
        this.confirmBtntxt = mActivity.getResources().getString(id);
    }

    public String getCancelBtntxt() {
        return cancelBtntxt;
    }

    /**
     * 设置取消按钮文字
     *
     * @param cancelBtntxt
     */
    public void setCancelBtntxt(String cancelBtntxt) {
        this.cancelBtntxt = cancelBtntxt;
    }

    public int getConfirmBtntxtColor() {
        return confirmBtntxtColor;
    }

    /**
     * 设置确认按钮文字颜色
     *
     * @param confirmBtntxtColor
     */
    public void setConfirmBtntxtColor(int confirmBtntxtColor) {
        this.confirmBtntxtColor = confirmBtntxtColor;
    }

    public int getCancelBtntxtColor() {
        return cancelBtntxtColor;
    }

    /**
     * 设置取消按钮文字颜色
     *
     * @param cancelBtntxtColor
     */
    public void setCancelBtntxtColor(int cancelBtntxtColor) {
        this.cancelBtntxtColor = cancelBtntxtColor;
    }


    public String getUpgrade_content() {
        return Upgrade_content;
    }

    /**
     * 设置升级要显示的内容
     *
     * @param upgrade_content
     */
    public void setUpgrade_content(String upgrade_content) {
        Upgrade_content = upgrade_content;
    }

    public String getUpgrade_Size() {
        return Upgrade_Size;
    }

    /**
     * 设置升级要显示的apk大小
     *
     * @param upgrade_Size
     */
    public void setUpgrade_Size(String upgrade_Size) {
        Upgrade_Size = upgrade_Size;
    }


    /**
     * 显示列表类型的弹出框
     * listViewDialog方法
     */
    public static MaterialDialog showListViewDialog(Context context, String title, ListAdapter adapter, final AdapterView.OnItemClickListener clickListener) {
        final MaterialDialog dialog = new MaterialDialog(context);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.onItemClick(parent, view, position, id);
                dialog.dismiss();
            }
        });
//        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title);
        dialog.setContentView(listView);
        dialog.show();
        return dialog;
    }
    /**
     * 自定义输入框弹出框
     * **/
//    public static MaterialDialog showCustomizeViewDialog(Context context){
//        final MaterialDialog dialog = new MaterialDialog(context);
//        dialog.setContentView(R.layout.activity_return_opinion);
//        dialog.setNegativeButton("確定", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setPositiveButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//
//        return dialog;
//    }
}

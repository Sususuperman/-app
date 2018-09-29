package com.cs.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.android.common.R;
import com.cs.common.listener.OnPwdClickListenter;
import com.cs.common.view.MyProgressDialog;
import com.jungly.gridpasswordview.GridPasswordView;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class DialogTools {
    /**
     * 显示确认框
     *
     * @param context
     * @param title
     * @param content
     * @param clickListener
     * @return
     */
    public static MaterialDialog showConfirmDialog(Context context, String title, String content, final View.OnClickListener clickListener) {

        return showConfirmDialog(context, title, content, "确定", clickListener);
    }

    /**
     * 显示确认框
     *
     * @param context
     * @param title         标题
     * @param content       内容
     * @param okButton      确定按钮
     * @param clickListener 确定按钮监听
     * @return
     */
    public static MaterialDialog showConfirmDialog(Context context, String title, String content, String okButton, final View.OnClickListener clickListener) {
        if (!StringUtils.hasLength(title))
            title = "提示";

        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(content);
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

    /**
     * 显示确认和取消框
     *
     * @param context
     * @param title         标题
     * @param content       内容
     * @param okButton      确定按钮
     * @param cancleButton  取消按钮
     * @return
     */
    public static MaterialDialog showConfirmCancleDialog(Context context, String title, String content, String okButton, String cancleButton, final View.OnClickListener clickOkListener, final View.OnClickListener clickCancleListener) {
        if (!StringUtils.hasLength(title))
            title = "提示";
        if (!StringUtils.hasLength(okButton)) {
            okButton = "确定";
        }
        if (!StringUtils.hasLength(cancleButton)) {
            okButton = "取消";
        }
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(content);
        dialog.setPositiveButton(okButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOkListener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCancleListener.onClick(v);
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }


    /**
     * 显示日期选择器
     *
     * @param context
     * @param datetime
     * @param datePickerClickListener
     * @return
     */
    public static MyProgressDialog showDateDialog(Activity context, long datetime, MyProgressDialog.OnDatePickerClickListener datePickerClickListener) {
        MyProgressDialog dialog = showDateDialog(context, datetime, MyProgressDialog.DIALOG_DATEPICKER, datePickerClickListener);
        dialog.setmDatePickerClickListener(datePickerClickListener);
        dialog.showDialog(datetime);
        return dialog;
    }


    /**
     * 显示日期选择器
     *
     * @param mode                    时间选择器类型 只显示年月，只显示年月日
     * @param context
     * @param datetime
     * @param datePickerClickListener
     * @return
     */
    public static MyProgressDialog showDateDialog(Activity context, long datetime, int mode, MyProgressDialog.OnDatePickerClickListener datePickerClickListener) {
        MyProgressDialog dialog = new MyProgressDialog(context, mode);
        dialog.setmDatePickerClickListener(datePickerClickListener);
        dialog.showDialog(datetime);
        return dialog;
    }


    /**
     * 显示时间选择器
     *
     * @param context
     * @param timePickerClickListener
     * @return
     */
    public static MyProgressDialog showTimerDialog(Activity context, MyProgressDialog.OnTimePickerClickListener timePickerClickListener) {
        MyProgressDialog dialog = new MyProgressDialog(context, MyProgressDialog.DIALOG_TIMEPICKER);
        dialog.setmTimePickerClickListener(timePickerClickListener);
        dialog.showDialog();
        return dialog;
    }

    /**
     * listViewDialog方法
     */
    public static MaterialDialog showListViewDialog(Context context, String title, List<String> items, final AdapterView.OnItemClickListener clickListener) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        final MaterialDialog dialog = new MaterialDialog(context);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.addAll(items);
        arrayAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.onItemClick(parent, view, position, id);
                dialog.dismiss();
            }
        });
//        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setCanceledOnTouchOutside(true);//点击外部区域 使弹框消失
        dialog.setTitle(title);
        dialog.setContentView(listView);
        dialog.show();
        return dialog;
    }

    /**
     * 显示密码输入确认框
     *
     * @param context
     * @param title   标题
     * @return
     */
    public static MaterialDialog showPwdDialog(Context context, String title, final OnPwdClickListenter onPwdClickListenter) {
        final MaterialDialog dialog = new MaterialDialog(context);
        View view = View.inflate(context, R.layout.diaolg_pwd, null);
        TextView txttitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView ok = (TextView) view.findViewById(R.id.verifycode_ok);
        TextView cancle = (TextView) view.findViewById(R.id.verifycode_cancel);
        final GridPasswordView gpv = (GridPasswordView) view.findViewById(R.id.pwd);
        if (StringUtils.hasLength(title)) {
            txttitle.setText(title);
        } else txttitle.setText("提示");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = gpv.getPassWord();
                if (onPwdClickListenter != null) {
                    onPwdClickListenter.onPwdClick(p);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);//点击外部区域 使弹框消失
        dialog.setView(view);
        dialog.show();
        return dialog;
    }
}

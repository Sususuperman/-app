package com.cs.common.utils;

import android.widget.Toast;

import com.cs.common.baseapp.BaseApp;

/**
 * Created by weifei on 16/8/10.
 */
public class IToast {
    public static Toast toast;

    public static void toast(final String msg){
        if (toast == null) {
            toast = Toast.makeText(BaseApp.getAppContext(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    public static void toast(int msg){
        toast(BaseApp.getAppContext().getString(msg));
    }
}

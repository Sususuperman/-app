package com.cs.common.utils;

import android.os.Build;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    /**
     * 设置光标到最后
     * @param editText
     */
    public static void setSelection(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable)text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * Generate a value suitable for use in {@link #(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }


    public static int getViewId(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            return generateViewId();
        }else{
            return View.generateViewId();
        }
    }


}

package com.cs.common.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cs.common.utils.DialogTools;

/**
 * Created by weifei on 2015/9/7.
 */
public abstract class BaseEditActivity extends BaseToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHomeEnable(true);
    }

    @Override
    public void onClickBack() {
        showExitDialog(getDialogContent());
    }


    private void showExitDialog(String content) {
        if(TextUtils.isEmpty(content)){
            finish();
        } else{
            DialogTools.showConfirmDialog(this, "", content, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 获取退出时dialog要显示的内容，如果是空则不显示
     * @return
     */
    protected abstract String getDialogContent();
}

package com.hywy.publichzt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs.common.base.BaseToolbarActivity;
import com.hywy.publichzt.R;

public class ExplaneActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explane);
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("水质类别说明"));
    }
}

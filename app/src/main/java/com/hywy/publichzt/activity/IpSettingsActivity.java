package com.hywy.publichzt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.common.base.BaseActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.app.App;

import butterknife.Bind;
import butterknife.OnClick;

public class IpSettingsActivity extends BaseActivity {
    @Bind(R.id.et_ip)
    TextView et_ip;
    @Bind(R.id.et_port)
    TextView et_port;
    @Bind(R.id.btn_confirm)
    Button btn;

    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.rb1)
    RadioButton btn1;
    @Bind(R.id.rb2)
    RadioButton btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_settings);
    }


    @OnClick({R.id.tv_back, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_confirm:
                App app = (App) getApplication();
                String ip = et_ip.getText().toString().trim();
                String port = et_port.getText().toString().trim();
                if (ip.equals("")) {
                    Toast.makeText(this, "IP不能为空！", Toast.LENGTH_SHORT).show();
                } else if (port.equals("")) {
                    Toast.makeText(this, "端口号不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (btn1.isChecked()) {
                        app.setApiURL("http" + "://" + ip + ":" + port);
                    }else if(btn2.isChecked()){
                        app.setApiURL("https" + "://" + ip + ":" + port);
                    }
                    finish();
                }
                break;
        }
    }
}

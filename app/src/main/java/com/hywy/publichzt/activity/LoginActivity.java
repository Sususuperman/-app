package com.hywy.publichzt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cs.common.base.BaseActivity;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.SoftKeyBoardSatusView;
import com.cs.common.view.percent.PercentLinearLayout;
import com.hywy.publichzt.R;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.task.GetAppMenuTask;
import com.hywy.publichzt.task.LoginTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


//import com.tencent.bugly.crashreport.CrashReport;

/**
 * 登录面，验证用户的登录信息并获取用户信息
 *
 * @author charming
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
        SoftKeyBoardSatusView.SoftkeyBoardListener {
    @Bind(R.id.login_status_view)
    SoftKeyBoardSatusView loginStatusView;
    @Bind(R.id.top_bg)
    PercentLinearLayout topBg;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.userpass)
    EditText userpass;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.bottom_bg)
    PercentLinearLayout bottomBg;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    //    @Bind(R.id.tv_http)
//    TextView tvHttp;
    //    @Bind(R.id.et_ip)
//    EditText etIp;
//    @Bind(R.id.et_port)
//    EditText etPort;
    @Bind(R.id.togglePwd)
    ToggleButton toggleButton;


    private String url;
    private Animation my_Translate;

    public static final int MAIN = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        init();
        initListeners();
    }

    private void initListeners() {
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    userpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    userpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * 初始化页面
     */
    protected void init() {
        my_Translate = AnimationUtils.loadAnimation(this, R.anim.login_animation);
        bottomBg.setAnimation(my_Translate);
        loginStatusView.setSoftKeyBoardListener(this);

//        username.setText("admin");
//        userpass.setText("1");
    }

    @OnClick({R.id.login_btn, R.id.tv_settings})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:// 登录
                login();
                break;
            case R.id.tv_settings:
//                chooseHttp();
                startActivity(new Intent(this, IpSettingsActivity.class));
                break;
        }
    }

    private void chooseHttp() {
        final List<String> list = new ArrayList<>();
        list.add("http");
        list.add("https");
        DialogTools.showListViewDialog(this, "提示", list, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                tvHttp.setText(list.get(i));
            }
        });
    }


    /**
     * 登录
     */
    private void login() {
        final String uname = username.getText().toString().trim();
        final String pass = userpass.getText().toString().trim();
//        final String ip = etIp.getText().toString().trim();
//        final String port = etPort.getText().toString().trim();
        if (uname.equals("")) {// 用户名为空
            Toast.makeText(this, R.string.name_empty, Toast.LENGTH_SHORT).show();
        } else if (pass.equals("")) {// 密码为空
            Toast.makeText(this, R.string.password_empty, Toast.LENGTH_SHORT).show();
        }
//        else if (ip.equals("")) {
//            Toast.makeText(this, "IP不能为空！", Toast.LENGTH_SHORT).show();
//        } else if (port.equals("")) {
//            Toast.makeText(this, "端口号不能为空！", Toast.LENGTH_SHORT).show();
//        }
        else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tm", System.currentTimeMillis());//当前时间戳
            params.put("KEYDATA", uname + ",fh," + pass + ",fh," + "1");//用户名,fh,密码,fh,code
            params.put("password", pass);
            login(params);
        }
    }

    /**
     * 登录
     *
     * @param params
     */
    private void login(Map<String, Object> params) {
        App app = (App) getApplication();
        if (!StringUtils.hasLength(app.getApiURL())) {
            app.setApiURL("http://218.22.195.54:7007");
        }
//        app.setApiURL(tvHttp.getText().toString() + "://" + etIp.getText().toString().trim() + ":" + etPort.getText().toString());
        // 有网络，网络登陆
        if (app.getNetworkMng().isCanConnect()) {
            SpringViewHandler handler = new SpringViewHandler(this);
            BaseHandler_.Builder builder = new BaseHandler_.Builder();
            builder.isLogin(true);
            builder.isShowToast(false);
            handler.request(params, new LoginTask(this));
            handler.setListener(new OnPostListenter() {
                @Override
                public void OnPostSuccess(Map<String, Object> result) {
                    hideSoftInput(username);
                    initAppMenu();
                }

                @Override
                public void OnPostFail(Map<String, Object> result) {
                    String failure = "";
                    if (result != null && result.containsKey("msg")) {
                        failure = result.get("msg").toString();
                    } else {
                        failure = getString(R.string.login_failure);
                    }
                    Toast.makeText(LoginActivity.this, failure, Toast.LENGTH_SHORT).show();
                }
            });
        } else {// 无网络，不能登陆，提示用户
            Toast.makeText(this, R.string.netWorkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void initAppMenu() {
        SpringViewHandler handler = new SpringViewHandler(this);
        Map<String, Object> params = new HashMap<>();
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false).isWait(false));
        handler.request(params, new GetAppMenuTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                MainActivity.startAction(LoginActivity.this);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
//                if (app.getMenu1() == null) {
//                    findViewById(R.id.bottom_bar1).setVisibility(View.GONE);
//                } else if (app.getMenu2() == null) {
//                    findViewById(R.id.bottom_bar2).setVisibility(View.GONE);
//                } else if (app.getMenu3() == null) {
//                    findViewById(R.id.bottom_bar3).setVisibility(View.GONE);
//                }

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, ClearActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isFinish", ClearActivity.wNoLoginEnd);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {

    }

    @Override
    public void keyBoardVisable(int move) {
        loginBtn.getScrollY();
        Message message = new Message();
        message.what = WHAT_SCROLL;
        message.obj = move;
        handler.sendMessageDelayed(message, 100);
    }

    @Override
    public void keyBoardInvisable(int move) {
        handler.sendEmptyMessageDelayed(WHAT_BTN_VISABEL, 200);
    }


    final int WHAT_SCROLL = 0, WHAT_BTN_VISABEL = WHAT_SCROLL + 1;
    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case WHAT_SCROLL:
                    int move = (Integer) msg.obj;
                    scrollView.smoothScrollBy(0, move);
                    break;
                case WHAT_BTN_VISABEL:
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCancel(resultCode)) {
            switch (requestCode) {
                case MAIN://表示更新成功
                    break;
                case -1://表示更新失败
                    break;
                case request_activity_finish://表示更新失败
                    MainActivity.startAction(LoginActivity.this);
                    break;
            }
        }
    }


    private void startCountDownTime(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                Log.d("CountDownTimer", "onTick  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Log.d("CountDownTimer", "onFinish -- 倒计时结束");
            }
        };
        timer.start();// 开始计时
        //timer.cancel(); // 取消
    }
}

package com.hywy.publichzt.activity;

import android.os.Bundle;

import com.cs.common.base.BaseActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.handler.FlyoxHandler;

/**
 * 启动界面
 * @author james
 *
 */
public class WelcomeActivity extends BaseActivity
{
	private FlyoxHandler flyoxHandler;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

//		//这里，要做一次百度云推送的绑定
//		PushUtils.bind(this);

		flyoxHandler = new FlyoxHandler(this);
	    flyoxHandler.sendEmptyMessage(FlyoxHandler.wBegin);
	}

}

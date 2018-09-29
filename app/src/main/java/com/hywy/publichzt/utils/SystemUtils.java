package com.hywy.publichzt.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


public class SystemUtils {
    /**
     * 调用系统拨号功能
     * @param context
     * @param phone
     */
    public static final void call(Context context, String phone){
        if(phone != null){
            // 拨号：激活系统的拨号组件,6.0以上拨号
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + phone); // 设置数据
            intent.setData(data);
            context.startActivity(intent); // 激活Activity组件
        }else{
            Toast.makeText(context,"电话为空", Toast.LENGTH_SHORT).show();
        }
    }
    
    
    /**
     * 调起系统发短信功能
     * @param message 短信内容
     */
    public static final void sendSMS(Context context, String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ phoneNumber));
        intent.putExtra("sms_body", message);
        context.startActivity(intent);
    }
}

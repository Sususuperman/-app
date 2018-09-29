package com.hywy.publichzt.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.cs.common.utils.IToast;
import com.hywy.publichzt.entity.Upgrade;
import com.hywy.publichzt.utils.UpgradeUtils;

import java.io.File;

public class DownloadToInstallActivity extends AppCompatActivity {

    private Intent mIntent;
    private Upgrade upgrade;
    public static final int INSTALL_OK = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //传入下载保存路径
        mIntent = getIntent();
        upgrade = (Upgrade)getIntent().getSerializableExtra("upgrade");
        String apkPath = UpgradeUtils.getApkPath(upgrade.getUrl());
        if(apkPath == null || apkPath.trim().equals("")){
            IToast.toast("文件不存在！");
            finish();
        }else{
            File downFile = new File(apkPath);
            if (downFile.exists()){
                /* 打开文件进行安装 */
                openFile(downFile);
            }else{
                IToast.toast("文件不存在！");
                finish();
            }
        }
    }


    // 在手机上打开文件的method
    private void openFile(File f){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        // 调用getMIMEType()来取得MimeType
        String type = getMIMEType(f);
        // 设定intent的file与MimeType
        intent.setDataAndType(Uri.fromFile(f), type);
        startActivity(intent);
    }


    // 判断文件MimeType的method
    private String getMIMEType(File f){
        String type = "";
        String fName = f.getName();
        // 取得扩展名
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        // 按扩展名的类型决定MimeType
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav"))
        {
            type = "audio";
        }
        else if (end.equals("3gp") || end.equals("mp4"))
        {
            type = "video";
        }
        else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp"))
        {
            type = "image";
        }
        else if (end.equals("apk"))
        {
            // android.permission.INSTALL_PACKAGES
            type = "application/vnd.android.package-archive";
        }
        else if(end.equals("pdf"))
        {
            type = "pdf";
        }else {
            type = "*";
        }
        // 如果无法直接打开，就跳出软件清单给使用者选择
        if (!end.equals("apk"))
        {
            type += "/*";
        }

        return type;
    }


    @Override
    protected void onRestart()
    {
        updateUpgrade();
        super.onRestart();
    }


    @Override
    protected void onResume()
    {

        //防止用户点击取消按钮，之后停留在该界面，显示为黑屏
        if(!mIntent.hasExtra("upgrade")){
            finish();
        }else {
            mIntent.removeExtra("upgrade");
        }

        super.onResume();
    }


    private void updateUpgrade(){
        if(upgrade != null){
            if(UpgradeUtils.isInStall(upgrade, this)){
//                updateFlag();
            }
        }
    }

    /**
//     * 更新数据库升级状态
//     */
//    private void updateFlag(){
//        if(upgrade != null){
//            UpgradeDao dao = new UpgradeDao(this);
//            upgrade.setUpgrdeFlag(Upgrade.NO_Upgrde);
//            dao.updateFlag(upgrade);
//            Intent intent = new Intent(this,MainActivity.class);
//            intent.putExtra("upgrade", upgrade);
//            setResult(INSTALL_OK, intent);
//        }
//    }
}

package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.cs.android.task.Task;
import com.cs.common.Key;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.FileUtils;
import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.dao.AccountDao;
import com.hywy.publichzt.entity.Account;
import com.hywy.publichzt.task.PostReplaceHeadTask;
import com.hywy.publichzt.view.customer.MoreItemView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.entity.Photo;

public class PersonInfoActivity extends BaseToolbarActivity {
    @Bind(R.id.iv_head)
    ImageView iv_head;
    @Bind(R.id.id)
    MoreItemView mId;
    @Bind(R.id.name)
    MoreItemView mName;
    @Bind(R.id.duty)
    MoreItemView mDuty;
    @Bind(R.id.admn)
    MoreItemView mAdmn;
    @Bind(R.id.phone)
    MoreItemView mPhone;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        init();
    }

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, PersonInfoActivity.class);
        activity.startActivity(intent);
    }

    private void init() {
        setTitleBulider(new Bulider().title(getString(R.string.user_info)).backicon(R.drawable.ic_arrow_back_white_24dp));
        account = App.getInstance().getAccount();
        if (StringUtils.hasLength(account.getUrlData()))
            ImageLoaderUtils.displayRound(this, iv_head, account.getUrlData(), R.drawable.ic_head_default);
        setMoreItemView(mId, account.getUserName());
        setMoreItemView(mName, account.getNAME());
        setMoreItemView(mDuty, account.getROLE_NAME());
        setMoreItemView(mId, account.getUserName());
        setMoreItemView(mAdmn, account.getADNM());
        setMoreItemView(mPhone, account.getPHONE());

    }

    private void setMoreItemView(MoreItemView m, String str) {
        if (StringUtils.hasLength(str)) {
            m.setItemRightText(str);
        }
    }

    @OnClick(R.id.head_layout)
    public void replace() {
        chooseMedia();
    }

    private void chooseMedia() {
        PhotoPicker.builder().setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCancel(resultCode)) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    if (data != null) {
                        ArrayList<Photo> listPhotos = data.getParcelableArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        Photo photo = listPhotos.get(0);
                        File file = new File(photo.getPath());
                        if (file.exists()) {
                            JSONObject object = new JSONObject();
                            try {
                                object.put("fileName", StringUtils.getFilename(photo.getPath()));
                                object.put("fileExt", StringUtils.getFilenameExtension(photo.getPath()));
                                Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath());
                                object.put("fileStr", FileUtils.getByteByBitmap(bitmap));
                                object.put("USER_ID", account.getUserId());
                                post(object.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void post(String json) {
        SpringViewHandler handler = new SpringViewHandler(this);
        Task task = new PostReplaceHeadTask(this);
        Map<String, Object> params = new HashMap<>();
        params.put("data", json);
        handler.request(params, task);
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                String path = (String) result.get(Key.RESULT);
                if (StringUtils.hasLength(path)) {
                    ImageLoaderUtils.displayRound(PersonInfoActivity.this, iv_head, path, R.drawable.ic_head_default);
                    AccountDao dao = new AccountDao(PersonInfoActivity.this);
                    account.setUrlData(path);
                    dao.replace(account);
                    App.getInstance().setAccount(account);
                    mRxManager.post(RxAction.ACTION_REPLACE_HEAD, path);
                }
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
//                IToast.toast(R.string.submit_fail);
            }
        });
    }

}

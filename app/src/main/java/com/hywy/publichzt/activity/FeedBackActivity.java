package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.cs.android.task.Task;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.FileUtils;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.ImagePagerActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.task.PostFeedBackTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.entity.Photo;

public class FeedBackActivity extends BaseToolbarActivity implements ImagesAdapter.onDeleteListener, AdapterView.OnItemClickListener {

    @Bind(R.id.et_feedback)
    EditText et_feed;
    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.et_phone)
    EditText et_phone;

    private ImagesAdapter imageAdapter;
    private JSONArray array;

    public static void startAction(Context activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        init();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.title_feedback)));

        imageAdapter = new ImagesAdapter(this);
        imageGrid.setAdapter(imageAdapter);
        imageAdapter.setOnDeleteListener(this);
        imageGrid.setOnItemClickListener(this);
        addDefaultImage();
    }

    private void addDefaultImage() {
        AttachMent attachMent = createNewAttach(null);
        imageAdapter.add(imageAdapter.getCount(), attachMent);
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * 创建附件
     *
     * @param path
     * @return
     */
    private AttachMent createNewAttach(String path) {
        AttachMent attachMent = new AttachMent();
        attachMent.setATTACH_URL(path);
        attachMent.setStatus(AttachMent.UPLOAD_UN);
        attachMent.setATTACH_NAME(StringUtils.getFilename(path));
        return attachMent;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AttachMent attachMent = imageAdapter.getItem(i);
        if (attachMent.getID() == 0) {
            chooseMedia(imageAdapter.getImageList());
        } else {
            ImagePagerActivity.startShowImages(view.getContext(), imageAdapter.getImagePaths(), i);
        }
    }

    private void chooseMedia(List<AttachMent> arrayList) {
        PhotoPicker.builder().setPhotoCount(8)
                .setSelected(getPhotos(arrayList))
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    /**
     * AttachMent转换成Photo进入相册选择
     *
     * @param arrayList
     * @return
     */
    private ArrayList<Photo> getPhotos(List<AttachMent> arrayList) {
        ArrayList<Photo> photos = new ArrayList<>();
        if (StringUtils.isNotNullList(arrayList)) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                AttachMent att = arrayList.get(i);
                Photo photo = new Photo();
                photo.setPath(att.getATTACH_URL());
                photo.setStatus(att.getID() != 0 ? Photo.Enabled : Photo.UnEnabled);
                photos.add(photo);
            }
        }
        return photos;
    }

    @OnClick(R.id.btn)
    public void submit() {
        if (!StringUtils.hasLength(et_feed.getText().toString()))
            return;
//        IToast.toast("提交成功");
//        finish();
        SpringViewHandler handler = new SpringViewHandler(this);
        Task task = new PostFeedBackTask(this);
        try {
            JSONObject obj = new JSONObject();
            array = getUploadImage();
            obj.put("PATROL_NOTE", array);
            Map<String, Object> params = new HashMap<>();
            obj.put("COUNTENT", et_feed.getText().toString());
            if (StringUtils.hasLength(et_phone.getText().toString())) {
                obj.put("PHONE", et_phone.getText().toString());
            }
            params.put("data", obj.toString());
            handler.request(params, task);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                IToast.toast("反馈成功");
                finish();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
//                IToast.toast(R.string.submit_fail);
            }
        });
    }


    @Override
    public void onDelete(final int position, AttachMent photos) {
        DialogTools.showConfirmDialog(this, "", getString(R.string.dialog_delete_image), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAdapter.remove(position);
                imageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCancel(resultCode)) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    if (data != null) {
                        ArrayList<Photo> listPhotos = data.getParcelableArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        for (Photo photo : listPhotos) {
                            //id为0的话说明已经选择过了
                            if (photo.getId() != 0) {
                                AttachMent newAtt = createNewAttach(photo.getPath());
                                newAtt.setID(photo.getId());
                                imageAdapter.add(0, newAtt);
                            }
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /********转化为jsonarray********/
    private JSONArray getUploadImage() {
        JSONArray jsonArray = new JSONArray();

        if (StringUtils.isNotNullList(imageAdapter.getImageList())) {
            int size = imageAdapter.getImageList().size();
            for (int i = 0; i < size; i++) {
                AttachMent attachMent = imageAdapter.getImageList().get(i);
                File file = new File(attachMent.getATTACH_URL());
                if (file.exists()) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("fileName", StringUtils.getFilename(attachMent.getATTACH_URL()));
                        object.put("fileExt", StringUtils.getFilenameExtension(attachMent.getATTACH_URL()));
                        Bitmap bitmap = BitmapFactory.decodeFile(attachMent.getATTACH_URL());
                        object.put("fileStr", FileUtils.getByteByBitmap(bitmap));
                        jsonArray.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray;
    }

}

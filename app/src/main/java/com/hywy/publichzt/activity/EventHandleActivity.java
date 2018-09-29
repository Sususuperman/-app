package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cs.common.Key;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.FileUtils;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.ImagePagerActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.adapter.SpinnerListAdapter;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.entity.EventSupervise;
import com.hywy.publichzt.entity.User;
import com.hywy.publichzt.task.GetUserListTask;
import com.hywy.publichzt.task.PostCreateEndEventTask;
import com.hywy.publichzt.task.PostCreateEventTask;
import com.hywy.publichzt.view.MyRecycleView;
import com.hywy.publichzt.view.dialog.dialogplus.DialogPlus;
import com.hywy.publichzt.view.dialog.dialogplus.ListHolder;
import com.hywy.publichzt.view.dialog.dialogplus.OnItemClickListener;

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

/**
 * 事件处理
 *
 * @author Superman
 */
public class EventHandleActivity extends BaseToolbarActivity implements OnItemClickListener, AdapterView.OnItemClickListener, ImagesAdapter.onDeleteListener {
    @Bind(R.id.iv_status_r)
    ImageView imageView;
    @Bind(R.id.tv_title_r)
    TextView eventName;
    @Bind(R.id.tv_publisher_r)
    TextView tvPublisher;
    @Bind(R.id.tv_time_r)
    TextView tvTime;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_event)
    TextView tvEvent;
    @Bind(R.id.tv_deadline)
    TextView tvDeadline;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.recyclerview)
    MyRecycleView recycleView;

    @Bind(R.id.toggle)
    ToggleButton toggle;

    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.btn2)
    Button btn2;
    @Bind(R.id.edittext)
    EditText etText;
    @Bind(R.id.tv_handle)
    TextView tvHandle;

    @Bind(R.id.image_grid2)
    GridView imageGrid2;
    private ImagesAdapter imagesAdapter2;
    private JSONArray array;


    private EventSupervise eventSupervise;

    private ImagesAdapter imagesAdapter;
    private SpinnerListAdapter spinnerAdapter1;

    private String isBack = "";
    private List<User> users;

    public static void startAction(Activity activity, EventSupervise eventSupervise) {
        Intent intent = new Intent(activity, EventHandleActivity.class);
        intent.putExtra("eventSupervise", eventSupervise);
        activity.startActivityForResult(intent, 10010);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        init();
        initData();
        initListeners();
    }


    private void initData() {
        setTextView(eventName, eventSupervise.getEVENT_NAME());
        setTextView(tvPublisher, eventSupervise.getPER_NM());
        setTextView(tvTime, eventSupervise.getSTARTTIME());
        setTextView(tvAddress, eventSupervise.getREACH_NAME());
        setTextView(tvEvent, eventSupervise.getEVENT_TYPE_NAME());
        setTextView(tvDeadline, eventSupervise.getENDTIME());

        if (eventSupervise.getSTATE().equals("1")) {//state是1表示已办结，不是1就接着判断deal_state判断已处理和未处理
            findViewById(R.id.input_layout).setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.shiwu_yibanjie);
        } else {
            if (eventSupervise.getDEAL_STATE().equals("1")) {
                findViewById(R.id.input_layout).setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.shiwu_chulizhong);
            } else {
                findViewById(R.id.input_layout).setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.shiwu_weichuli);

            }
        }
//        setTextView(tvUser, waterRain.getPEOPLE());
//        setTextView(tvPhone, waterRain.getTEL());
//
//        setTextView(endTime, DateUtils.GetNowTimeChinesne("yyyy/MM/dd HH:00:00"));
//        setTextView(startTime, DateUtils.GetPevTimeChinesne("yyyy/MM/dd HH:00:00"));
//        setTextView(rainNum, waterRain.getDRP() + "");

    }

    @OnClick(R.id.btn)
    public void onClick() {
        if (!StringUtils.hasLength(etText.getText().toString())) {
            IToast.toast("请输入处理意见或退回说明！");
            etText.setFocusable(true);
            return;
        }
        if (toggle.isChecked()) {
            doNext(null);
        } else
            initUserList();
    }

    @OnClick(R.id.btn2)
    public void onClick2() {
        if (!StringUtils.hasLength(etText.getText().toString())) {
            IToast.toast("请输入处理意见或退回说明！");
            etText.setFocusable(true);
            return;
        }
        endEvent();
    }

    private void endEvent() {
        SpringViewHandler handler = new SpringViewHandler(this);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("DEAL_IDEA", etText.getText().toString());
//            jsonObject.put("EVENT_ID", eventSupervise.getEVENT_ID());
//            jsonObject.put("DEAL_ID", eventSupervise.getDEAL_ID());
//            jsonObject.put("DEAL_STATE", 1);
//            jsonObject.put("STATE", 1);
//            jsonObject.put("DEALTIME", DateUtils.transforMillToDateInfo(System.currentTimeMillis()/1000));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("DEAL_IDEA", etText.getText().toString());
        map.put("EVENT_ID", eventSupervise.getEVENT_ID());
        map.put("DEAL_ID", eventSupervise.getDEAL_ID());
        map.put("DEAL_STATE", 1);
        map.put("STATE", 1);
        map.put("DEALTIME", DateUtils.transforMillToDateInfo(System.currentTimeMillis() / 1000));
        array = getUploadImage();
        map.put("EVENT_SITUATION", array);
        handler.request(map, new PostCreateEndEventTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
                IToast.toast((String) result.get(Key.MSG));
            }
        });
    }


    private void doNext(User user) {
        SpringViewHandler handler = new SpringViewHandler(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DEAL_IDEA", etText.getText().toString());
            jsonObject.put("EVENT_ID", eventSupervise.getEVENT_ID());
            jsonObject.put("SEND_PER_ID", App.getInstance().getAccount().getUserId());
            if (user != null)
                jsonObject.put("DEAL_PER_ID", user.getUSER_ID());
            jsonObject.put("DEAL_ID", eventSupervise.getDEAL_ID());
            array = getUploadImage();
            jsonObject.put("EVENT_SITUATION", array);
            if (!isBack.equals("")) {
                jsonObject.put("ISBACK", 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", jsonObject.toString());
//        map.put("DEAL_IDEA", etText.getText().toString());
//        map.put("EVENT_ID", eventSupervise.getEVENT_ID());
//        map.put("SEND_PER_ID", 1);
//        map.put("DEAL_PER_ID", 1);
//        if (!isBack.equals("")) {
//            map.put("ISBACK", isBack);
//        }
        handler.request(map, new PostCreateEventTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
//                IToast.toast((String) result.get(Key.MSG));
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
                IToast.toast((String) result.get(Key.MSG));
            }
        });
    }

    private void setTextView(TextView textView, String str) {
        if (StringUtils.hasLength(str)) {
            textView.setText(str);
        }
    }

    private void initUserList() {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        handler.request(params, new GetUserListTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                users = (List<User>) result.get(com.hywy.publichzt.key.Key.RESULT);
                showUserList(users);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }

    private void showUserList(List<User> list) {
        spinnerAdapter1.setList(list);
        showRadioDialog();
    }

    /**
     * 显示单选dialog
     */
    private void showRadioDialog() {
        DialogPlus dialogPlus = new DialogPlus.Builder(this)
                .setContentHolder(new ListHolder())
                .setAdapter(spinnerAdapter1)
                .setGravity(DialogPlus.Gravity.CENTER)
                .setCancelable(true)
                .setInAnimation(0)
                .setOutAnimation(0)
                .setOnItemClickListener(this)
                .create();
        View view = dialogPlus.getHolderView();
        view.setBackgroundResource(R.drawable.bg_btn_default);
        dialogPlus.show();
    }


    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
        Object object = spinnerAdapter1.getItem(position);
        if (object instanceof User) {
            User user = (User) object;
            doNext(user);
//            tvAddress.setTag(adnm);
//            tvAddress.setText(adnm.getADNM());
        }
        dialog.dismiss();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title(getString(R.string.text_event_handle)));
        eventSupervise = getIntent().getParcelableExtra("eventSupervise");
        spinnerAdapter1 = new SpinnerListAdapter(this);
        recycleView.setVisibility(View.GONE);
        findViewById(R.id.input_layout).setVisibility(View.VISIBLE);
        btn.setText("下一步");
        btn2.setVisibility(View.VISIBLE);

        imagesAdapter = new ImagesAdapter(this);
        if (StringUtils.isNotNullList(eventSupervise.getDealattch()))
            imagesAdapter.setList(eventSupervise.getDealattch());
        imageGrid.setAdapter(imagesAdapter);

        imagesAdapter2 = new ImagesAdapter(this);
        imageGrid2.setAdapter(imagesAdapter2);
        imagesAdapter2.setOnDeleteListener(this);
        imageGrid2.setOnItemClickListener(this);
        addDefaultImage();
    }

    private void initListeners() {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn.setText("退回上一步");
                    btn2.setVisibility(View.GONE);
                    tvHandle.setText("退回说明：");
                    isBack = "1";
                } else {
                    btn.setText("下一步");
                    btn2.setVisibility(View.VISIBLE);
                    tvHandle.setText("处理意见：");
                    isBack = "";
                }
            }
        });
    }

    private void addDefaultImage() {
        AttachMent attachMent = createNewAttach(null);
        imagesAdapter2.add(imagesAdapter2.getCount(), attachMent);
        imagesAdapter2.notifyDataSetChanged();
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
        if (adapterView.getId() == R.id.image_grid) {
            AttachMent attachMent = imagesAdapter.getItem(i);
            if (attachMent.getID() == 0) {
                chooseMedia(imagesAdapter.getImageList());
            } else {
                ImagePagerActivity.startShowImages(view.getContext(), imagesAdapter.getImagePaths(), i);
            }
        } else {
            AttachMent attachMent = imagesAdapter2.getItem(i);
            if (attachMent.getID() == 0) {
                chooseMedia(imagesAdapter2.getImageList());
            } else {
                ImagePagerActivity.startShowImages(view.getContext(), imagesAdapter2.getImagePaths(), i);
            }
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

    @Override
    public void onDelete(int position, AttachMent photos) {
        DialogTools.showConfirmDialog(this, "", getString(R.string.dialog_delete_image), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesAdapter2.remove(position);
                imagesAdapter2.notifyDataSetChanged();
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
                                imagesAdapter2.add(0, newAtt);
                            }
                        }
                        imagesAdapter2.notifyDataSetChanged();
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /********转化为jsonarray********/
    private JSONArray getUploadImage() {
        JSONArray jsonArray = new JSONArray();

        if (StringUtils.isNotNullList(imagesAdapter2.getImageList())) {
            int size = imagesAdapter2.getImageList().size();
            for (int i = 0; i < size; i++) {
                AttachMent attachMent = imagesAdapter2.getImageList().get(i);
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

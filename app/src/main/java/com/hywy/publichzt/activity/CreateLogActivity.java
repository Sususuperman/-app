package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.cs.android.task.Task;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
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
import com.hywy.publichzt.adapter.item.OptionsItem;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Adnm;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.entity.Plan;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetAddressListTask;
import com.hywy.publichzt.task.GetRiverListInMobileTask;
import com.hywy.publichzt.task.PostCreateLogTask;
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

public class CreateLogActivity extends BaseToolbarActivity implements ImagesAdapter.onDeleteListener, AdapterView.OnItemClickListener, OnItemClickListener {

    @Bind(R.id.log_name)
    TextView logName;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.other_pro)
    EditText otherPro;
    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.handle_sitation)
    EditText tvSit;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private ImagesAdapter imageAdapter;

    @Bind(R.id.tv_address)
    TextView tv_address;

    private Plan plan;
    private String jsonArray;
    private BaseListFlexAdapter mAdapter;

    private JSONArray array3;
    private List<River> rivers;
    private SpinnerListAdapter spinnerAdapter1;
    private List<Adnm> adnms;
    private long logId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);
        init();
        initData();
    }


    public static void startAction(Activity activity, Plan plan, JSONArray array, long logId) {
        Intent intent = new Intent(activity, CreateLogActivity.class);
        intent.putExtra("plan", plan);
        intent.putExtra("array", array.toString());
        intent.putExtra("logId", logId);
        activity.startActivity(intent);
    }

    private void init() {
        plan = getIntent().getParcelableExtra("plan");
        jsonArray = getIntent().getStringExtra("array");
        logId = getIntent().getLongExtra("logId", 0);
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("新建巡河日志"));
        imageAdapter = new ImagesAdapter(this);
        imageGrid.setAdapter(imageAdapter);
        imageAdapter.setOnDeleteListener(this);
        imageGrid.setOnItemClickListener(this);
        spinnerAdapter1 = new SpinnerListAdapter(this);
        addDefaultImage();

        mAdapter = new BaseListFlexAdapter(this);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        if (plan != null) {
            tvName.setText(plan.getREACH_NAME());
            initList();
            findViewById(R.id.river_layout).setClickable(false);
            findViewById(R.id.layout_address).setVisibility(View.GONE);
        } else {
            findViewById(R.id.tv_sit).setVisibility(View.GONE);
            findViewById(R.id.layout_sit).setVisibility(View.GONE);
            findViewById(R.id.down_view).setVisibility(View.VISIBLE);
            initRiverList();
            initAddressList();
        }
        tvUser.setText(App.getInstance().getAccount().getNAME());//当前登录人
        tvTime.setText(DateUtils.transforMillToDate(System.currentTimeMillis()));
    }

    private void initList() {
        for (Plan.OPTIONSBean pob : plan.getOPTIONS()) {
            OptionsItem item = new OptionsItem(pob);
            mAdapter.addItem(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initRiverList() {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        handler.request(params, new GetRiverListInMobileTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                rivers = (List<River>) result.get(Key.RESULT);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }

    private void initAddressList() {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        handler.request(params, new GetAddressListTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                adnms = (List<Adnm>) result.get(Key.RESULT);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }


    @OnClick({R.id.river_layout, R.id.layout_address})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.river_layout:
                showRiverList(rivers);
                break;
            case R.id.layout_address:
                showAddressList(adnms);
                break;
        }
    }

    private void showRiverList(List<River> list) {
        spinnerAdapter1.setList(list);
        showRadioDialog();
    }

    private void showAddressList(List<Adnm> list) {
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
        if (object instanceof Adnm) {
            Adnm adnm = (Adnm) object;
            tv_address.setTag(adnm);
            tv_address.setText(adnm.getADNM());
        } else if (object instanceof River) {
            River river = (River) object;
            tvName.setTag(river);
            tvName.setText(river.getREACH_NAME());
        }
        dialog.dismiss();
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

    @OnClick(R.id.btn)
    public void submit() {
        if ("".equals(logName.getText().toString().trim())) {
            IToast.toast("请输入日志名称");
            return;
        }
        if (plan == null) {
            if (tvName.getTag() == null) {
                IToast.toast("请选择河段名称");
            }
            if (tv_address.getTag() == null) {
                IToast.toast("请选择所属区域");
            }
        }
        SpringViewHandler handler = new SpringViewHandler(this);
        Task task = new PostCreateLogTask(this);
        try {
            handler.request(getParams(), task);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                IToast.toast("成功");
                finish();
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {
//                IToast.toast(R.string.submit_fail);
            }
        });

    }

    /**
     * 请求参数
     */
    private Map<String, Object> getParams() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("PATROL_LOG_NAME", logName.getText().toString());
        if (plan != null) {
            obj.put("PLAN_ID", plan.getPLAN_ID());
            obj.put("REACH_CODE", plan.getREACH_CODE());
            obj.put("ADCD", plan.getADCD());
        } else {
            River r = (River) tvName.getTag();
            obj.put("REACH_CODE", r.getREACH_CODE());
            Adnm a = (Adnm) tv_address.getTag();
            obj.put("ADCD", a.getADCD());
            obj.put("PLAN_ID", "");
        }
        obj.put("PATROL_TM", tvTime.getText().toString());
        obj.put("STARTTIME", DateUtils.transforMillToDateInfo(System.currentTimeMillis() / 1000));
        if (logId != 0) {
            obj.put("LOG_ID", logId);
        }
        Map<String, Object> params = new HashMap<>();
//        params.put("PATROL_LOG_NAME", logName.getText().toString());
//        params.put("PLAN_ID", plan.getPLAN_ID());
//        params.put("REACH_CODE", plan.getREACH_CODE());
//        params.put("PATROL_TM", tvTime.getText().toString());
//        params.put("STARTTIME", "2018-07-09 13:39:40");
//        params.put("ADCD", plan.getADCD());

        if (StringUtils.hasLength(otherPro.getText().toString())) {
            obj.put("OTHER_SITUATION", otherPro.getText().toString());
//            params.put("OTHER_SITUATION", otherPro.getText().toString());
        }
        if (StringUtils.hasLength(tvSit.getText().toString())) {
            obj.put("DISPOSE_SITUATION", tvSit.getText().toString());
//            params.put("DISPOSE_SITUATION", tvSit.getText().toString());
        }

        List<OptionsItem> list = mAdapter.getAllItems();
        JSONArray array = new JSONArray();
        for (OptionsItem item : list) {
            Plan.OPTIONSBean o = item.getData();
            JSONObject object = new JSONObject();
            object.put("OPTIONS_ID", o.getOPTIONS_ID());
            object.put("STATE", o.getStatus());
            array.put(object);
        }

//        JSONArray array2 = new JSONArray();
//        JSONObject object2 = new JSONObject();
//        object2.put("PATROL_LOGN", 40.762672977128865);
//        object2.put("PATROL_LAT", 116.35077);
//        object2.put("TM", "2018-07-09 13:40:01");
//        array2.put(object2);
//        params.put("PATROL_ROUTE", array2.toString());
        if (plan != null) {
            obj.put("PATROL_SITUATION", array);
        } else {
            obj.put("PATROL_SITUATION", null);
        }
        obj.put("PATROL_ROUTE", jsonArray);
        array3 = getUploadImage();
        obj.put("PATROL_NOTE", array3);
        obj.put("USER_ID", App.getInstence().getAccount().getUserId());
        params.put("data", obj.toString());
//        List<String> list = imageAdapter.getImageIds();
//        if (StringUtils.isNullList(list)) {
//            String[] name = list.toArray(new String[list.size()]);
//            params.put("media_attachment_ids", StringUtils.arrayToDelimitedString(name, ","));
//        }
        return params;
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

package com.hywy.publichzt.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.cs.android.task.Task;
import com.cs.common.adapter.BaseListFlexAdapter;
import com.cs.common.adapter.layoutmanager.FullyLinearLayoutManager;
import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.handler.BaseHandler_;
import com.cs.common.handler.SpringViewHandler;
import com.cs.common.handler.WaitDialog;
import com.cs.common.listener.OnPostListenter;
import com.cs.common.utils.DateUtils;
import com.cs.common.utils.DialogTools;
import com.cs.common.utils.FileUtils;
import com.cs.common.utils.IToast;
import com.cs.common.utils.StringUtils;
import com.cs.common.view.ImagePagerActivity;
import com.hywy.amap.Locate;
import com.hywy.amap.Util;
import com.hywy.publichzt.R;
import com.hywy.publichzt.action.RxAction;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.adapter.SpinnerListAdapter;
import com.hywy.publichzt.adapter.item.OptionsItem;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Adnm;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.entity.Plan;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.key.Key;
import com.hywy.publichzt.task.GetAddressListTask;
import com.hywy.publichzt.task.GetAdnmListTask;
import com.hywy.publichzt.task.GetComplainTypesTask;
import com.hywy.publichzt.task.GetRiverListByAdcdTask;
import com.hywy.publichzt.task.GetRiverListInMobileTask;
import com.hywy.publichzt.task.PostCreateComplainTask;
import com.hywy.publichzt.task.PostCreateLogTask;
import com.hywy.publichzt.view.dialog.dialogplus.DialogPlus;
import com.hywy.publichzt.view.dialog.dialogplus.ListHolder;
import com.hywy.publichzt.view.dialog.dialogplus.OnItemClickListener;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

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

/***
 * 投诉上报
 */
public class CreateComplainActivity extends BaseToolbarActivity implements ImagesAdapter.onDeleteListener, AdapterView.OnItemClickListener, OnItemClickListener {

    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.et_other_type)
    EditText etOtype;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_xian)
    TextView etXian;
    @Bind(R.id.tv_zhen)
    TextView etZhen;
    @Bind(R.id.tv_cun)
    TextView etCun;
    @Bind(R.id.tv_river)
    TextView tvRiver;
    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.et_details)
    EditText tvDetails;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.toggle)
    ToggleButton toggle;
    private ImagesAdapter imageAdapter;

    private String jsonArray;

    private JSONArray array3;
    private List<Complain> complains;
    private SpinnerListAdapter spinnerAdapter1;
    private WaitDialog waitDialog;
    private Locate locale;
    private List<Adnm> city_admns;
    private List<Adnm> zhen_admns;
    private List<Adnm> cun_admns;
    public static final int TYPE_XIAN = 1;
    public static final int TYPE_ZHEN = 2;
    public static final int TYPE_CUN = 3;
    private int type_admn;//区分县乡村标识
    private AMapLocation loc;
    private List<River> rivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complain);
        init();
        initData();
    }


    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, CreateComplainActivity.class);
        activity.startActivity(intent);
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("投诉建议"));
        imageAdapter = new ImagesAdapter(this);
        imageGrid.setAdapter(imageAdapter);
        imageAdapter.setOnDeleteListener(this);
        imageGrid.setOnItemClickListener(this);
        spinnerAdapter1 = new SpinnerListAdapter(this);
        addDefaultImage();
        waitDialog = new WaitDialog(this, "定位中...", false, false);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    RequestPermmisons();
                }
            }
        });
    }

    private void initData() {
        initTypes();
        initAdmns(0, null);
    }


    private void initTypes() {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        params.put("ddd", 1);
        handler.request(params, new GetComplainTypesTask(this));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                complains = (List<Complain>) result.get(Key.RESULT);
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }

    private void initAdmns(int type, String adcd) {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        if (type == 0) {//区县
            params.put("ddd", 1);
        } else params.put("ADCD", adcd);
        handler.request(params, new GetAdnmListTask(this, type));
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                if (type == 0) {
                    city_admns = (List<Adnm>) result.get(Key.RESULT);
                } else {
                    switch (type_admn) {
                        case TYPE_XIAN:
                            zhen_admns = (List<Adnm>) result.get(Key.RESULT);
                            break;
                        case TYPE_ZHEN:
                            cun_admns = (List<Adnm>) result.get(Key.RESULT);
                            break;
                    }
                }
            }

            @Override
            public void OnPostFail(Map<String, Object> result) {

            }
        });
    }

    private void initRiverList(String adcd) {
        SpringViewHandler handler = new SpringViewHandler(this);
        handler.setBuilder(new BaseHandler_.Builder().isShowToast(false));
        Map<String, Object> params = new HashMap<>();
        params.put("ADCD", adcd);
//        params.put("ADCD", "341500000000");
        handler.request(params, new GetRiverListByAdcdTask(this));
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

    @OnClick({R.id.type_layout, R.id.xian_layout, R.id.zhen_layout, R.id.cun_layout, R.id.river_layout})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.type_layout:
                showtypes(complains);
                break;
            case R.id.xian_layout:
                showadmns(city_admns);
                type_admn = TYPE_XIAN;
                break;
            case R.id.zhen_layout:
                if (StringUtils.isNotNullList(zhen_admns)) {
                    type_admn = TYPE_ZHEN;
                    showadmns(zhen_admns);
                }
                break;
            case R.id.cun_layout:
                if (StringUtils.isNotNullList(cun_admns)) {
                    type_admn = TYPE_CUN;
                    showadmns(cun_admns);
                }
                break;
            case R.id.river_layout:
                if (StringUtils.isNotNullList(rivers))
                    showRiverList(rivers);
                break;
        }
    }

    private void clearTag(TextView tv) {
        tv.setTag(null);
        tv.setText("");
    }

    private void showRiverList(List<River> list) {
        spinnerAdapter1.setList(list);
        showRadioDialog();
    }

    private void showtypes(List<Complain> list) {
        spinnerAdapter1.setList(list);
        showRadioDialog();
    }

    private void showadmns(List<Adnm> list) {
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
        if (object instanceof Complain) {
            Complain c = (Complain) object;
            tvType.setTag(c);
            tvType.setText(c.getTYPE());
        } else if (object instanceof Adnm) {
            Adnm a = (Adnm) object;
            switch (type_admn) {
                case TYPE_XIAN:
                    etXian.setTag(a);
                    etXian.setText(a.getADNM());
                    initAdmns(1, a.getADCD());
                    clearTag(etZhen);
                    clearTag(etCun);
                    break;
                case TYPE_ZHEN:
                    etZhen.setTag(a);
                    etZhen.setText(a.getADNM());
                    initAdmns(1, a.getADCD());
                    clearTag(etCun);
                    break;
                case TYPE_CUN:
                    etCun.setTag(a);
                    etCun.setText(a.getADNM());
                    initRiverList(a.getADCD());
                    break;
            }
        } else if (object instanceof River) {
            River river = (River) object;
            tvRiver.setTag(river);
            tvRiver.setText(river.getREACH_NAME());
        }
        dialog.dismiss();
    }

    private void RequestPermmisons() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        startLocation(new Locate.OnLocationListener() {
                            @Override
                            public void onReceiveLocation(AMapLocation location) {
                                loc = location;
                                IToast.toast("获取成功！");

                                if (waitDialog != null)
                                    waitDialog.dismiss();
                            }

                            @Override
                            public void onReceiveFail() {
                                if (waitDialog != null)
                                    waitDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
    }

    private void startLocation(Locate.OnLocationListener listener) {
        if (waitDialog != null)
            waitDialog.show();
        //启动定位
        locale = new Locate(this);
        locale.start();
        locale.setOnLocationListener(listener);
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
        if (tvType.getTag() == null) {
            IToast.toast("请选择投诉类型");
            return;
        }
        if (tvRiver.getTag() == null) {
            IToast.toast("请选择投诉河段");
            return;
        }
        SpringViewHandler handler = new SpringViewHandler(this);
        Task task = new PostCreateComplainTask(this);
        try {
            handler.request(getParams(), task);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.setListener(new OnPostListenter() {
            @Override
            public void OnPostSuccess(Map<String, Object> result) {
                IToast.toast("成功");
                mRxManager.post(RxAction.ACTION_CREATE_COMPLAIN, "");
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
//        obj.put("PATROL_LOG_NAME", logName.getText().toString());
//        obj.put("PATROL_TM", tvTime.getText().toString());
//        obj.put("STARTTIME", DateUtils.transforMillToDateInfo(System.currentTimeMillis() / 1000));
//        if (logId != 0) {
//            obj.put("LOG_ID", logId);
//        }
        Map<String, Object> params = new HashMap<>();
        array3 = getUploadImage();
        obj.put("PATROL_NOTE", array3);
        if (tvType.getTag() != null) {
            Complain complain = (Complain) tvType.getTag();
            obj.put("COMPLAIN", complain.getID());
        }
        if (StringUtils.hasLength(etOtype.getText().toString()))
            obj.put("COMPLAINTYPE", etOtype.getText().toString());
        if (StringUtils.hasLength(etName.getText().toString()))
            obj.put("COMPLAINMAN", etName.getText().toString());
        if (etXian.getTag() != null) {
            Adnm adnm = (Adnm) etXian.getTag();
            obj.put("ADCD", adnm.getADCD());
        }
        if (tvRiver.getTag() != null) {
            River river = (River) tvRiver.getTag();
            obj.put("COMPLAINRIVER", river.getREACH_CODE());
        }
        if (loc != null && loc.getLatitude() != 0 && loc.getLongitude() != 0) {
            obj.put("LGTD", loc.getLongitude());
            obj.put("LLTD", loc.getLatitude());
        }
        if (StringUtils.hasLength(etPhone.getText().toString()))
            obj.put("PHONE", etPhone.getText().toString());

        if (StringUtils.hasLength(tvDetails.getText().toString()))
            obj.put("COMPLAINTXT", tvDetails.getText().toString());

        params.put("data", obj.toString());
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

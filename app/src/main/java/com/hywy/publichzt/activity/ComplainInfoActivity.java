package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.view.ImagePagerActivity;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.entity.Complain;

import java.util.List;

import butterknife.Bind;

public class ComplainInfoActivity extends BaseToolbarActivity implements AdapterView.OnItemClickListener{
    private Complain complain;

    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_river)
    TextView tvRiver;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_handleuer)
    TextView tvHandleuser;
    @Bind(R.id.tv_handlephone)
    TextView tvHandlephone;
    @Bind(R.id.tv_replaytime)
    TextView tvReplytime;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.image_grid)
    GridView imageGrid;
    @Bind(R.id.image_grid2)
    GridView imageGrid2;
    private ImagesAdapter imageAdapter;
    private ImagesAdapter imageAdapter2;

    public static void startAction(Activity activity, Complain complain) {
        Intent intent = new Intent(activity, ComplainInfoActivity.class);
        intent.putExtra("complain", complain);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_info);
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("投诉处理单"));
        complain = getIntent().getParcelableExtra("complain");
        initViews();
    }

    private void initViews() {
        tvTime.setText(complain.getCOMPLAINTIME());
        tvUser.setText(complain.getCOMPLAINMAN());
        tvPhone.setText(complain.getPHONE());
        tvRiver.setText(complain.getREACH_NAME());
        tvContent.setText(complain.getCOMPLAINTXT());
        tvStatus.setText(complain.getCOMZT().equals("0") ? "未处理" : "已处理");
        tvHandleuser.setText(complain.getSOLVEMAN());
        tvHandlephone.setText(complain.getSOLVE_PHONE());
        tvResult.setText(complain.getOPINION());
        imageAdapter = new ImagesAdapter(this);
        imageGrid.setAdapter(imageAdapter);
        imageGrid.setOnItemClickListener(this);
        List<AttachMent> attachMents = complain.getCOMPLAIN_ATTACH();
        if (attachMents != null && attachMents.size() > 0) {
            imageAdapter.setList(attachMents);
        }

        imageAdapter2 = new ImagesAdapter(this);
        imageGrid2.setAdapter(imageAdapter2);
        imageGrid2.setOnItemClickListener(this);
        List<AttachMent> attachMents2 = complain.getSOLVE_ATTACH();
        if (attachMents2 != null && attachMents2.size() > 0) {
            imageAdapter2.setList(attachMents);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.image_grid){
            ImagePagerActivity.startShowImages(view.getContext(), imageAdapter.getImagePaths(), i);
        }else  ImagePagerActivity.startShowImages(view.getContext(), imageAdapter2.getImagePaths(), i);
    }
}

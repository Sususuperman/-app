package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.hywy.publichzt.entity.ProblemReport;

import java.util.List;

import butterknife.Bind;

public class ProblemReportDetailsActivity extends BaseToolbarActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.pro_name)
    TextView proName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_river)
    TextView tvRiver;
    @Bind(R.id.tv_event)
    TextView tvEvent;
    @Bind(R.id.pro_content)
    TextView tvContent;
    @Bind(R.id.image_grid)
    GridView imageGrid;

    private ImagesAdapter imageAdapter;
    private ProblemReport problemReport;

    public static void startAction(Activity activity, ProblemReport problemReport) {
        Intent intent = new Intent(activity, ProblemReportDetailsActivity.class);
        intent.putExtra("problemReport", problemReport);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report_details);
        init();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("问题详情"));
        problemReport = getIntent().getParcelableExtra("problemReport");
        imageAdapter = new ImagesAdapter(this);
        imageGrid.setAdapter(imageAdapter);
        imageGrid.setOnItemClickListener(this);
        List<AttachMent> attachMents = problemReport.getPATROL_NOTE();
        if (attachMents != null && attachMents.size() > 0) {
            imageAdapter.setList(attachMents);
        }

        proName.setText(problemReport.getEVENT_NAME());
        tvContent.setText(problemReport.getEVENT_CONT());
        tvAddress.setText(problemReport.getADNM());
        tvRiver.setText(problemReport.getREACH_NAME());
        tvEvent.setText(problemReport.getTYPENAME());

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ImagePagerActivity.startShowImages(view.getContext(), imageAdapter.getImagePaths(), i);
    }
}

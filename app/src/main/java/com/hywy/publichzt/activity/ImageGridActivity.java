package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.ImagesAdapter;
import com.hywy.publichzt.entity.AttachMent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ImageGridActivity extends BaseToolbarActivity {
    @Bind(R.id.image_grid)
    GridView imageGrid;
    private ImagesAdapter imagesAdapter;
    private List<AttachMent> list;

    public static void startAction(Context activity, ArrayList<AttachMent> list) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        intent.putParcelableArrayListExtra("attachments", list);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        init();
    }

    private void init() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("附件查看"));
        list = getIntent().getParcelableArrayListExtra("attachments");
        imagesAdapter = new ImagesAdapter(this);
        if (StringUtils.isNotNullList(list))
            imagesAdapter.setList(list);
        imageGrid.setAdapter(imagesAdapter);
    }
}

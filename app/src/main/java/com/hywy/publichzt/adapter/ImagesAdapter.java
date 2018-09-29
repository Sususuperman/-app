package com.hywy.publichzt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cs.common.adapter.BaseListAdapter;
import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.cs.common.utils.ViewHolder;
import com.cs.common.view.ImagePagerActivity;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.R;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.AttachMent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Superman
 */
public class ImagesAdapter extends BaseListAdapter<AttachMent> {
    private onDeleteListener onDeleteListener;
    private String api;

    public ImagesAdapter(Context context) {
        this(context, "");
    }

    public ImagesAdapter(Context context, String api) {
        super(context);
        this.api = api;
    }

    private String getUrl(String url) {
        return HttpUrl.getUrl("/RMS/" + url);
    }

    @Override
    protected View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent, final int position) {
//        if (convertView == null)
        convertView = layoutInflater.inflate(R.layout.layout_item_image, parent, false);

        ImageView image = ViewHolder.get(convertView, R.id.image);
        ImageView delete = ViewHolder.get(convertView, R.id.delete);
        AttachMent attach = getItem(position);

        if (onDeleteListener != null) {
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
        }

        if (attach.getID() == 0) {
            image.setImageResource(R.drawable.add_picture);
            delete.setVisibility(View.GONE);
        } else {
            image.setImageResource(0);
            if (attach.getATTACH_URL() != null && new File(attach.getATTACH_URL()).exists()) {
                ImageLoaderUtils.display(App.getAppContext(), image, getItem(position).getATTACH_URL());
            } else {
                if (StringUtils.hasLength(api)) {
                    ImageLoaderUtils.display(App.getAppContext(), image, getUrl(getItem(position).getATTACH_URL()));
                } else {
                    ImageLoaderUtils.display(App.getAppContext(), image, getItem(position).getATTACH_URL());
                }
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePagerActivity.startShowImages(v.getContext(), getImageUrls(), position);
                }
            });
        }
        delete.setTag(position);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete(position, getItem(position));
                }
            }
        });
        return convertView;
    }

    public void setOnDeleteListener(ImagesAdapter.onDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface onDeleteListener {
        void onDelete(int position, AttachMent photos);
    }

    /**
     * 判断是否可上传
     *
     * @return
     */
    public boolean isUpoad() {
        return getByStatusImages(AttachMent.UPLOAD_UN).size() > 0;
    }

    /**
     * 获取未上传的图片
     *
     * @return
     */
    public List<AttachMent> getByStatusImages(int status) {
        List<AttachMent> list = new ArrayList<>();
        if (StringUtils.isNotNullList(getList())) {
            int size = getCount();
            for (int i = 0; i < size; i++) {
                AttachMent attach = getItem(i);
                //如果有一个为上传就可以上传
                if (attach.getStatus() == status) {
                    list.add(attach);
                }
            }
        }
        return list;
    }


    public ArrayList<AttachMent> getImageList() {
        ArrayList<AttachMent> list = new ArrayList();
        int size = getCount();
        for (int i = 0; i < size; i++) {
            AttachMent ment = mList.get(i);
            if (ment.getID() != 0) {
                list.add(ment);
            }
        }
        return list;
    }

    public ArrayList<String> getImagePaths() {
        ArrayList<String> list = new ArrayList();
        int size = getCount();
        for (int i = 0; i < size; i++) {
            AttachMent ment = mList.get(i);
            if (ment.getID() != 0) {
                list.add(ment.getATTACH_URL());
            }
        }
        return list;
    }

    public ArrayList<String> getImageUrls() {
        ArrayList<String> list = new ArrayList();
        int size = getCount();
        for (int i = 0; i < size; i++) {
            AttachMent ment = mList.get(i);
            if (ment.getID() != 0) {
                if (StringUtils.hasLength(api)) {
                    list.add(getUrl(ment.getATTACH_URL()));
                } else {
                    list.add(ment.getATTACH_URL());
                }
            }
        }
        return list;
    }

    public ArrayList<Integer> getImageIds() {
        ArrayList<Integer> list = new ArrayList();
        int size = getCount();
        for (int i = 0; i < size; i++) {
            AttachMent ment = mList.get(i);
            if (ment.getID() != 0) {
                list.add(ment.getID());
            }
        }
        return list;
    }
}

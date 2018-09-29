package com.hywy.publichzt.view.dialog.dialogplus;

import android.widget.BaseAdapter;

/**
 * @author Orhan Obut
 */
public interface HolderAdapter extends Holder {

    void setAdapter(BaseAdapter adapter);

    void setOnItemClickListener(OnHolderListener listener);
}

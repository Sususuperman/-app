package com.hywy.publichzt.task;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.cs.common.utils.HttpUtils;
import com.hywy.publichzt.Const;
import com.hywy.publichzt.HttpUrl;
import com.hywy.publichzt.entity.Upgrade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UpgradeTask extends ValidTokenTask {

    public UpgradeTask(Context context, Map<String, Object> params) {
        super(context, params);
    }

    @Override
    protected Map<String, Object> sendData(Map<String, Object> params)
            throws Exception {

        Map<String, Object> result = new HashMap<>();
        String url = HttpUrl.getUpgradeUrl();
        String content = HttpUtils.getString(url, params);

        JSONObject data = new JSONObject(content);

        result.put("ret", Const.ApiSuccess);
        Upgrade upgrade = new Upgrade();
//        UpgradeDao dao = new UpgradeDao(context);
        if (!data.isNull("Data")) {
            JSONArray array = data.getJSONArray("Data");
            if (array.getJSONObject(0) != null) {
                JSONObject obj = array.getJSONObject(1);
                if (!obj.isNull("DOWNURL"))
                    upgrade.setUrl(data.getString("DOWNURL"));
                if (!data.isNull("APPNOTE"))
                    upgrade.setContent(data.getString("APPNOTE"));
//                if (!data.isNull("APPVERSION"))
//                    upgrade.setVersion_code(data.getString("APPVERSION"));
            }
        }

        PackageManager packageManager = context.getPackageManager();
        PackageInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        result.put("ret", Const.ApiSuccess);
        result.put("result", upgrade);
        return result;
    }

}

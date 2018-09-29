package com.cs.upgrade.task;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.cs.common.HttpConst;
import com.cs.common.utils.HttpUtils;
import com.cs.upgrade.entity.Upgrade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpgradeTask extends ValidSourceTask {
    private String url;

    public UpgradeTask(Context context, Map<String, Object> params, String url) {
        super(context, params);
        this.url = url;
    }

    @Override
    protected Map<String, Object> sendData(Map<String, Object> params)
            throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        String content = HttpUtils.getString(url, params);
        JSONObject obj = new JSONObject(content);
        if (!obj.isNull("Data")) {
            JSONArray array = obj.getJSONArray("Data");
            if(array.get(0)!=null){
                JSONObject data = (JSONObject) array.get(0);
                Upgrade upgrade = new Upgrade();

                if (!data.isNull("name"))
                    upgrade.setName(data.getString("name"));
                if (!data.isNull("APPVERSION"))
                    upgrade.setVersion_code(data.getInt("APPVERSION"));
                if (!data.isNull("version_name"))
                    upgrade.setVersion_name(data.getString("version_name"));
                if (!data.isNull("DOWNURL"))
                    upgrade.setUrl(data.getString("DOWNURL"));
                if (!data.isNull("APPNOTE"))
                    upgrade.setContent(data.getString("APPNOTE"));
                upgrade.setId(10000);

                PackageManager packageManager = context.getPackageManager();
                PackageInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                int versionCode = applicationInfo.versionCode;
                if (versionCode < upgrade.getVersion_code()) {
                    result.put("msg", HttpConst.Success);
                    result.put("result", upgrade);
                }

            }
        }


        return result;
    }

}

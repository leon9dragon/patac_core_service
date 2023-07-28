package com.gm.ultifi.service.cabinclimate.manager.propertymanager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class AreaPropertyMapper {

    private static final String TAG = AreaPropertyMapper.class.getSimpleName();

    private static JSONObject areaIDJson;

    private static final HashMap<String, Integer> mAreaIdPropertyMap;

    private static final HashMap<Integer, String> mAreaPropertyMap;

    private static final HashMap<String, Integer[]> mZonePropertyMap;

    static {
        mAreaPropertyMap = new HashMap<>();
        mAreaIdPropertyMap = new HashMap<>();
        mZonePropertyMap = new HashMap<>();
    }

    public static Set<String> getZonePropertyKeys() {
        return mZonePropertyMap.keySet();
    }

    public static Integer getZoneByZoneName(String zoneName, String featureName) {
        Log.d(TAG, "getZoneByZoneName: zoneName=" + zoneName + " featureName=" +  featureName);
        try {
            String area = areaIDJson.getJSONObject("areaID").getString(String.join("#", zoneName, featureName));
            return mAreaIdPropertyMap.get(area.replace("\"", ""));
        } catch (JSONException jSONException) {
            Log.e(TAG, "JSONException " + jSONException.getMessage());
            return 0;
        }
    }

    public static JSONArray getZoneNamesByAreaID(Integer areaId, String featureName) {
        Log.d(TAG, "getZoneNamesByAreaID: areaId=" + areaId + "featureName=" + featureName);
        try {
            return areaIDJson.getJSONObject("zone_name")
                    .getJSONArray(String.join("#", areaId.toString(), featureName));
        } catch (JSONException jSONException) {
            Log.e(TAG, "JSONException " + jSONException.getMessage());
            return null;
        }
    }
}
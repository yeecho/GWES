package com.yuanye.gwes.callback;

import org.json.JSONObject;

public interface RegisterCallback {
    void onResponse(JSONObject jo);
    void onFail(int code, String msg);
}

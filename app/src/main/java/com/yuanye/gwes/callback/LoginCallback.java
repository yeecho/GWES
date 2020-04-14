package com.yuanye.gwes.callback;

import org.json.JSONObject;

public interface LoginCallback {
    void onSuccess(JSONObject jo);
    void onFail(int i, String msg);
}

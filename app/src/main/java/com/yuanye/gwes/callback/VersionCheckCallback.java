package com.yuanye.gwes.callback;

public interface VersionCheckCallback {
    void onSuccess(int codeRemote);
    void onFail(String msg);
}

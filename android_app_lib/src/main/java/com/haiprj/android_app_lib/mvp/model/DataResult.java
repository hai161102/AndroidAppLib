package com.haiprj.android_app_lib.mvp.model;

public interface DataResult {
    void onDataResultSuccess(String key, Object... objects);
    void onDataResultFailed(String mess);
}

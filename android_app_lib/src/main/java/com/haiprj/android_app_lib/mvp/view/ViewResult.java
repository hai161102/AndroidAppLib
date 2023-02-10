package com.haiprj.android_app_lib.mvp.view;

public interface ViewResult {

    void onViewAvailable(String key, Object... objects);
    void onViewNotAvailable(String mess);
}

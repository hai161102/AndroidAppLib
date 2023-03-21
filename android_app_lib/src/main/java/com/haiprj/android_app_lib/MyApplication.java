package com.haiprj.android_app_lib;

import android.app.Application;

import com.haiprj.android_app_lib.admobs.AdmobManager;
import com.haiprj.android_app_lib.admobs.AppOpenManager;
import com.haiprj.android_app_lib.admobs.PurchaseManager;

public abstract class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AdmobManager.getInstance().init(this, isShowAdsTest() ? AdmobManager.getInstance().getDeviceId(this) : "");
        AdmobManager.getInstance().isPurchased(isPurchased());
        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }
        PurchaseManager.getInstance().initBilling(this);
    }

    protected abstract boolean isPurchased();

    protected abstract boolean isShowAdsTest();

    public abstract boolean enableAdsResume();

    public abstract String getOpenAppAdId();
}

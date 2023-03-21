package com.haiprj.android_app_lib.interfaces;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

abstract public class AdCallback {
    public AdCallback() {
    }

    public void onAdClosed() {
    }

    public void onAdFailedToLoad(int i) {
    }

    public void onAdFailedToLoad(LoadAdError i) {
    }

    public void onAdLeftApplication() {
    }

    public void onAdOpened() {
    }

    public void onAdLoaded() {
    }

    public void onAdClicked() {
    }

    public void onAdImpression() {
    }

    public void interCallback(InterstitialAd interstitialAd) {
    }

    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

    }

    public void onNativeAds(NativeAd nativeAd) {

    }
}

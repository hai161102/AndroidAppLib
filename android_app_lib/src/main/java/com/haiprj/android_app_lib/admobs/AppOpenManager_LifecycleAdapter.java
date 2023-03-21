//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.haiprj.android_app_lib.admobs;

import android.annotation.SuppressLint;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

@SuppressLint("RestrictedApi")
public class AppOpenManager_LifecycleAdapter implements GeneratedAdapter {
    final AppOpenManager mReceiver;

    AppOpenManager_LifecycleAdapter(AppOpenManager receiver) {
        this.mReceiver = receiver;
    }

    public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny, MethodCallsLogger logger) {
        boolean hasLogger = logger != null;
        if (!onAny) {
            if (event == Event.ON_START) {
                if (!hasLogger || logger.approveCall("onResume", 1)) {
                    this.mReceiver.onResume();
                }

            }
        }
    }
}

package com.haiprj.android_app_lib.ui;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public abstract class BaseActivity<T> extends AppCompatActivity {

    protected T binding;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = (T) DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        addEvent();
    }

    protected abstract void initView();
    protected abstract void addEvent();
    protected abstract int getLayoutId();
}

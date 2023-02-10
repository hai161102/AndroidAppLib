package com.haiprj.android_app_lib.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseDialog<T> extends AppCompatDialog {

    protected T binding;

    protected Activity activity;

    protected OnActionDialogCallback onActionDialogCallback;

    public BaseDialog(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context);
        this.activity = activity;
        this.onActionDialogCallback = onActionDialogCallback;
    }

    public BaseDialog(@NonNull Context context, int themeResId, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context, themeResId);
        this.activity = activity;
        this.onActionDialogCallback = onActionDialogCallback;
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context, cancelable, cancelListener);
        this.activity = activity;
        this.onActionDialogCallback = onActionDialogCallback;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = (T) DataBindingUtil.inflate(LayoutInflater.from(getContext()),getLayoutId(), null, false);
        setContentView(((ViewDataBinding)binding).getRoot());
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnDismissListener(dialogInterface -> BaseDialog.this.onDismiss());
        setOnCancelListener(dialogInterface -> BaseDialog.this.onCancel());
        initView();
        addEvent();
    }

    protected abstract void addEvent();

    protected abstract void initView();

    protected abstract int getLayoutId();



    protected void onDismiss() {

    }

    protected void onCancel() {

    }

    public void showDialog(){
        try {
            show();
        } catch (Exception e){

        }
    }

    public interface OnActionDialogCallback{
        void callback(String key, Object... objects);
    }


}

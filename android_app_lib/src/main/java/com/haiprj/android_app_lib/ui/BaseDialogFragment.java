package com.haiprj.android_app_lib.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment<T> extends DialogFragment {

    protected T binding;
    protected OnDialogFragmentCallback callback;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (T) DataBindingUtil.setContentView(requireActivity(), getLayoutId());
        initView();
        addEvent();
    }


    protected abstract void addEvent();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface OnDialogFragmentCallback {
        void callback(String key, Object... objects);
    }

}

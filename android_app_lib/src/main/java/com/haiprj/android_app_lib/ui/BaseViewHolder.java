package com.haiprj.android_app_lib.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T binding;

    @SuppressWarnings("unchecked")
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.binding = (T) DataBindingUtil.bind(itemView);
    }

    public abstract void load(Object object);
}

package com.haiprj.android_app_lib.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, T2> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    protected Context context;
    protected List<T2> list = new ArrayList<>();
    protected OnViewItemClickListener onViewItemClickListener;

    protected ViewGroup parent;

    protected int viewType;
    protected BaseViewHolder<T> viewHolder;
    public BaseAdapter(Context context, List<T2> list) {
        this.context = context;
        this.list = list;
    }

    public BaseAdapter() {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        this.viewType = viewType;
        this.viewHolder = getViewHolder();
        return this.viewHolder;
    }

    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        holder.load(list.get(position));
    }

    protected abstract BaseViewHolder<T> getViewHolder();
    @SuppressLint("NotifyDataSetChanged")
    public void update(List<T2> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnViewItemClickListener {
        void onClick(int position);
    }
}

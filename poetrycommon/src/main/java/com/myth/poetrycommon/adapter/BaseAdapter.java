package com.myth.poetrycommon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myth.poetrycommon.R;

import java.util.List;


/**
 * Created by AndyMao on 17-4-18.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> implements View.OnClickListener {

    public List<T> list;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public BaseAdapter.BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),
                parent, false);
        return getHolder(view);
    }

    protected abstract int getLayoutId();

    protected abstract BaseHolder getHolder(View view);

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BaseHolder holder = (BaseHolder) v.getTag(R.id.item_root);
        int position = holder.getPosition();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position);
        }
    }

    public static abstract class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View view) {
            super(view);
            itemView.setTag(R.id.item_root, this);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public T getItemData(int pos) {
        return list.get(pos);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}


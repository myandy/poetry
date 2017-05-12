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

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> implements View.OnClickListener, View.OnLongClickListener {

    public List<T> list;

    protected int mSelectedItemPosition = 0;
    protected int mLastSelectedItemPosition = -1;

    public void setSelectedItemPosition(int position) {
        mLastSelectedItemPosition = mSelectedItemPosition;
        mSelectedItemPosition = position;
        notifyItemChanged(mLastSelectedItemPosition, true);
        notifyItemChanged(mSelectedItemPosition, true);
    }

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
        holder.itemView.setOnLongClickListener(this);
        setSelected(holder, position == mSelectedItemPosition);
    }

    protected void setSelected(BaseHolder holder, boolean selected) {
        holder.itemView.setSelected(selected);
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position, List<Object> payloads) {
        if (payloads != null && payloads.size() > 0) {
            setSelected(holder, position == mSelectedItemPosition);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onClick(View v) {
        BaseHolder holder = (BaseHolder) v.getTag(R.id.item_root);
        int position = holder.getPosition();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position);
        }
        setSelectedItemPosition(position);
    }

    @Override
    public boolean onLongClick(View v) {
        BaseHolder holder = (BaseHolder) v.getTag(R.id.item_root);
        int position = holder.getPosition();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(position);
            return true;
        }
        return false;
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

        void onItemLongClick(int position);
    }
}


package com.myth.poetrycommon.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.entity.ISearchAble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyMao on 17-5-8.
 */

public abstract class NormalSearchListActivity<T extends ISearchAble> extends BaseSearchListActivity<T> {

    @Override
    public List<T> searchList(String word) {
        ArrayList<T> list = new ArrayList<>();
        for (T data : originList) {
            if (data.getSearchText().contains(word)) {
                list.add(data);
            }
        }
        return list;
    }


    @Override
    public BaseAdapter getSearchListAdapter() {
        return new NormalSearchAdapter();
    }


    public static class NormalSearchAdapter extends BaseAdapter<ISearchAble> {

        public static class ViewHolder extends BaseHolder {
            public TextView name;
            public TextView tag;
            public TextView desc;

            public ViewHolder(View arg0) {
                super(arg0);
                name = (TextView) arg0.findViewById(R.id.title);
                tag = (TextView) arg0.findViewById(R.id.tag);
                desc = (TextView) arg0.findViewById(R.id.desc);
            }

        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ISearchAble data = list.get(position);
            NormalSearchAdapter.ViewHolder viewHolder = (NormalSearchAdapter.ViewHolder) holder;
            viewHolder.name.setText(data.getShowTitle());
            if (!TextUtils.isEmpty(data.getShowDesc())) {
                viewHolder.desc.setVisibility(View.VISIBLE);
                viewHolder.desc.setText(data.getShowDesc());
            } else {
                viewHolder.desc.setVisibility(View.GONE);
            }
            viewHolder.tag.setText(data.getShowTag());
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_normal_seach;
        }

        @Override
        protected BaseHolder getHolder(View view) {
            return new NormalSearchAdapter.ViewHolder(view);
        }

    }
}

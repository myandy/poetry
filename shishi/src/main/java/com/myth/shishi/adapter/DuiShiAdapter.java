package com.myth.shishi.adapter;

import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.R;

public class DuiShiAdapter extends BaseAdapter<String> {

    public static class ViewHolder extends BaseHolder {

        public TextView name;
        public ViewHolder(View arg0) {
            super(arg0);
            name = (TextView) arg0.findViewById(R.id.name);
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(list.get(position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_duishi;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new ViewHolder(view);
    }
}

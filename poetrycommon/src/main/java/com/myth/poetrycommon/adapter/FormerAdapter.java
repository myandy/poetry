package com.myth.poetrycommon.adapter;

import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.entity.Former;

public class FormerAdapter extends BaseAdapter<Former> {
    public static class ViewHolder extends BaseHolder {

        public ViewHolder(View arg0) {
            super(arg0);
            name = (TextView) arg0.findViewById(R.id.name);
            tag = (TextView) arg0.findViewById(R.id.tag);
        }
        TextView name;

        TextView tag;

    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(getItemData(position).name);
        viewHolder.name.setTypeface(BaseApplication.instance.getTypeface());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.adapter_cipai;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new ViewHolder(view);
    }

}

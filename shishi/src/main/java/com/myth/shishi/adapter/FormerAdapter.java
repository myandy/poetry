package com.myth.shishi.adapter;

import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.MyApplication;
import com.myth.shishi.R;
import com.myth.shishi.entity.Former;

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
        viewHolder.name.setText(getItemData(position).getName());
        viewHolder.name.setTypeface(MyApplication.instance.getTypeface());
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

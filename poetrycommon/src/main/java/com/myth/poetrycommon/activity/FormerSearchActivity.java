package com.myth.poetrycommon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.entity.Former;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyMao on 17-5-8.
 */

public class FormerSearchActivity extends SearchListActivity<Former> {

    private static final int FORMER_EDIT = 0x01;

    @Override
    public List<Former> getData() {
        if (BaseApplication.instance.isCiApp()) {
            return FormerDatabaseHelper.getAllStartByCi();
        }
        return FormerDatabaseHelper.getAll();
    }

    @Override
    public List<Former> searchList(String word) {
        ArrayList<Former> list = new ArrayList<>();
        for (Former data : originList) {
            if (data.name.contains(word)) {
                list.add(data);
            }
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FORMER_EDIT && resultCode == RESULT_OK) {
            originList = FormerDatabaseHelper.getAll();
            refreshData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public int getSearchHint() {
        return R.string.search_former_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("former", list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final int position) {
                final Former former = list.get(position);
                final AlertDialog dialog = new AlertDialog.Builder(mActivity).setItems(new String[]{former.isManual() ? "编辑" : "查看", "添加"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(mActivity, FormerEditActivity.class);
                                    intent.putExtra("former", former);
                                    startActivityForResult(intent, FORMER_EDIT);
                                } else {
                                    Intent intent = new Intent(mActivity, FormerEditActivity.class);
                                    startActivityForResult(intent, FORMER_EDIT);
                                }
                            }
                        }).create();
                dialog.show();
            }
        };
    }

    @Override
    public BaseAdapter getSearchListAdapter() {
        return new FormerSearchAdapter();
    }


    public static class FormerSearchAdapter extends BaseAdapter<Former> {

        public static class ViewHolder extends BaseHolder {
            public TextView name;
            public TextView tag;

            public ViewHolder(View arg0) {
                super(arg0);
                name = (TextView) arg0.findViewById(com.myth.poetrycommon.R.id.name);
                tag = (TextView) arg0.findViewById(com.myth.poetrycommon.R.id.tag);
            }

        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            super.onBindViewHolder(holder, position);

            Former former = list.get(position);
            FormerSearchActivity.FormerSearchAdapter.ViewHolder viewHolder = (FormerSearchActivity.FormerSearchAdapter.ViewHolder) holder;
            viewHolder.name.setText(former.name);

            if (former.type == Former.TYPE_MANUAL) {
                viewHolder.tag.setText(R.string.manual);
            } else {
                if (former.wordcount == 0) {
                    viewHolder.tag.setText(R.string.no_limit);
                } else {
                    viewHolder.tag.setText(String.format("%d字", former.wordcount));
                }
            }

            viewHolder.name.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder.tag.setTypeface(BaseApplication.instance.getTypeface());
        }

        @Override
        protected int getLayoutId() {
            return com.myth.poetrycommon.R.layout.adapter_cipai;
        }

        @Override
        protected BaseHolder getHolder(View view) {
            return new FormerSearchActivity.FormerSearchAdapter.ViewHolder(view);
        }

    }
}

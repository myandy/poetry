package com.myth.poetrycommon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.view.GCDialog;

import java.util.ArrayList;
import java.util.List;


public class WritingSearchActivity extends SearchListActivity<Writing> {
    @Override
    public List<Writing> getData() {
        return WritingDatabaseHelper.getAllWriting();
    }

    @Override
    public List<Writing> searchList(String word) {
        ArrayList<Writing> list = new ArrayList<>();
        for (Writing data : originList) {
            if (data.text.contains(word)) {
                list.add(data);
            }
        }
        return list;
    }

    @Override
    public int getSearchHint() {
        return R.string.search_writing_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final int position) {
                new AlertDialog.Builder(mActivity).setItems(new String[]{"分享", "编辑", "删除"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Writing writing = list.get(position);
                                if (which == 0) {
                                    Intent intent = new Intent(mActivity, ShareActivity.class);
                                    intent.putExtra("writing", writing);
                                    mActivity.startActivity(intent);
                                } else if (which == 1) {
                                    Intent intent = new Intent(mActivity, EditActivity.class);
                                    if (writing.former == null) {
                                        writing.former = (FormerDatabaseHelper.getFormerById(writing.formerId));
                                    }
                                    intent.putExtra("writing", writing);
                                    mActivity.startActivity(intent);
                                } else if (which == 2) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(GCDialog.DATA_TITLE, mActivity.getString(R.string.delete_title));
                                    bundle.putString(GCDialog.DATA_CONTENT, mActivity.getString(R.string.delete_content));
                                    bundle.putString(GCDialog.CONFIRM_TEXT, mActivity.getString(R.string.delete));
                                    new GCDialog(mActivity, new GCDialog.OnCustomDialogListener() {

                                        @Override
                                        public void onConfirm() {
                                            WritingDatabaseHelper.deleteWriting(list.get(position));
                                            originList = WritingDatabaseHelper.getAllWriting();
                                            refreshData();
                                        }

                                        @Override
                                        public void onCancel() {
                                        }
                                    }, bundle).show();
                                }

                                dialog.dismiss();

                            }
                        }).create().show();
            }
        };
    }


    @Override
    public BaseAdapter getSearchListAdapter() {
        return new WritingSearchAdapter();
    }


    public static class WritingSearchAdapter extends BaseAdapter<Writing> {

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
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.name.setText(list.get(position).title);
            viewHolder.tag.setText(list.get(position).content);

            viewHolder.name.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder.tag.setTypeface(BaseApplication.instance.getTypeface());
        }

        @Override
        protected int getLayoutId() {
            return com.myth.poetrycommon.R.layout.adapter_cipai;
        }

        @Override
        protected BaseHolder getHolder(View view) {
            return new ViewHolder(view);
        }

    }
}

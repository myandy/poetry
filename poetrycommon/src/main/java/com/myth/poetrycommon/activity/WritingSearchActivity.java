package com.myth.poetrycommon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.view.GCDialog;

import java.util.List;


public class WritingSearchActivity extends NormalSearchListActivity<Writing> {
    @Override
    public List<Writing> getData() {
        return WritingDatabaseHelper.getAllWriting();
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

            @Override
            public void onItemLongClick(int position) {

            }
        };
    }
}


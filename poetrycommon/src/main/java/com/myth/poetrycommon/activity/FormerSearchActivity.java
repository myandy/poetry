package com.myth.poetrycommon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.entity.Former;

import java.util.List;

/**
 * Created by AndyMao on 17-5-8.
 */

public class FormerSearchActivity extends NormalSearchListActivity<Former> {

    private static final int FORMER_EDIT = 0x01;

    @Override
    public List<Former> getData() {
        if (BaseApplication.instance.isCiApp()) {
            return FormerDatabaseHelper.getAllStartByCi();
        }
        return FormerDatabaseHelper.getAll();
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



}

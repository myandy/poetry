package com.myth.cici.activity;

import android.content.Intent;

import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.poetrycommon.activity.NormalSearchListActivity;
import com.myth.poetrycommon.adapter.BaseAdapter;


import java.util.List;

public class CiSearchActivity extends NormalSearchListActivity<Ci> {


    private boolean isCollect = false;

    @Override
    public List<Ci> getData() {
        if (getIntent().hasExtra("collect")) {
            isCollect = true;
            return CiDatabaseHelper.getAllCollect();
        } else {
            return CiDatabaseHelper.getAllCi();
        }

    }

    @Override
    public int getSearchHint() {
        return isCollect ? R.string.search_collect_hint : R.string.search_ci_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, CiActivity.class);
                intent.putExtra("id", list.get(position).id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        };
    }

}

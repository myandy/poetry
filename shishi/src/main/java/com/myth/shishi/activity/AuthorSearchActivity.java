package com.myth.shishi.activity;

import android.content.Intent;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.NormalSearchListActivity;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.R;
import com.myth.shishi.db.AuthorDatabaseHelper;
import com.myth.shishi.entity.Author;

import java.util.List;

public class AuthorSearchActivity extends NormalSearchListActivity<Author> {
    @Override
    public List<Author> getData() {
        return AuthorDatabaseHelper.getAll();
    }


    @Override
    public int getSearchHint() {
        return R.string.search_author_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, AuthorPageActivity.class);
                list.get(position).color = BaseApplication.instance.getRandomColor();
                intent.putExtra("author", list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        };
    }

}

package com.myth.shishi.activity;

import android.content.Intent;

import com.myth.poetrycommon.activity.NormalSearchListActivity;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.R;
import com.myth.shishi.db.AuthorDatabaseHelper;
import com.myth.shishi.db.PoetryDatabaseHelper;
import com.myth.shishi.entity.Author;
import com.myth.shishi.entity.Poetry;

import java.util.List;

public class PoetrySearchActivity extends NormalSearchListActivity<Poetry> {

    private Author author;

    private boolean isCollect = false;

    @Override
    public List<Poetry> getData() {
        if (getIntent().hasExtra("author")) {
            author = (Author) getIntent().getSerializableExtra("author");
            return PoetryDatabaseHelper.getAllByAuthor(author.author);
        } else if (getIntent().hasExtra("collect")) {
            isCollect = true;
            return PoetryDatabaseHelper.getAllCollect();
        } else {
            return PoetryDatabaseHelper.getAll();
        }

    }

    @Override
    public int getSearchHint() {
        return isCollect ? R.string.search_collect_hint : R.string.search_poetry_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, AuthorPageActivity.class);
                intent.putExtra("index", list.get(position).id);
                if (author == null) {
                    intent.putExtra("author", AuthorDatabaseHelper
                            .getAuthorByName(list.get(position)
                                    .author));
                } else {
                    intent.putExtra("author", author);
                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        };
    }

}

package com.myth.shishi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.SearchListActivity;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.R;
import com.myth.shishi.db.AuthorDatabaseHelper;
import com.myth.shishi.db.PoetryDatabaseHelper;
import com.myth.shishi.entity.Author;
import com.myth.shishi.entity.Poetry;

import java.util.ArrayList;
import java.util.List;

public class PoetrySearchActivity extends SearchListActivity<Poetry> {

    private Author author;

    @Override
    public List<Poetry> getData() {
        if (getIntent().hasExtra("author")) {
            author = (Author) getIntent().getSerializableExtra("author");
            return PoetryDatabaseHelper.getAllByAuthor(author.getAuthor());
        } else if (getIntent().hasExtra("collect")) {
            return PoetryDatabaseHelper.getAllCollect();
        } else {
            return PoetryDatabaseHelper.getAll();
        }

    }

    @Override
    public List<Poetry> searchList(String word) {
        ArrayList<Poetry> list = new ArrayList<>();
        for (Poetry author : originList) {
            if (author.getTitle().contains(word) || author.getPoetry().contains(word)) {
                list.add(author);
            }
        }
        return list;
    }

    @Override
    public int getSearchHint() {
        return R.string.search_poetry_hint;
    }

    @Override
    public BaseAdapter.OnItemClickListener getItemClickListener() {
        return new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, AuthorPageActivity.class);
                intent.putExtra("index", list.get(position).getId());
                if (author == null) {
                    intent.putExtra("author", AuthorDatabaseHelper
                            .getAuthorByName(list.get(position)
                                    .getAuthor()));
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

    @Override
    public BaseAdapter getSearchListAdapter() {
        return new SearchListAdapter();
    }


    public static class SearchListAdapter extends BaseAdapter<com.myth.shishi.entity.Poetry> {

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
            viewHolder.name.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder. tag.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder.name.setText(list.get(position).getTitle());
            viewHolder.tag.setText(list.get(position).getPoetry());
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

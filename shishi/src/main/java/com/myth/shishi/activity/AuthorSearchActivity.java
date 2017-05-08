package com.myth.shishi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.SearchListActivity;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.shishi.R;
import com.myth.shishi.db.AuthorDatabaseHelper;
import com.myth.shishi.entity.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorSearchActivity extends SearchListActivity<Author> {
    @Override
    public List<Author> getData() {
        return AuthorDatabaseHelper.getAll();
    }

    @Override
    public List<Author> searchList(String word) {
        ArrayList<Author> list = new ArrayList<Author>();
        for (Author author : originList) {
            if (author.getAuthor().contains(word)) {
                list.add(author);
            }
        }
        return list;
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


    @Override
    public BaseAdapter getSearchListAdapter() {
        return new AuthorSearchAdapter();
    }


    public static class AuthorSearchAdapter extends BaseAdapter<Author> {

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
            PoetrySearchActivity.SearchListAdapter.ViewHolder viewHolder = (PoetrySearchActivity.SearchListAdapter.ViewHolder) holder;
            viewHolder.name.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder.tag.setTypeface(BaseApplication.instance.getTypeface());
            viewHolder.name.setText(list.get(position).getAuthor());
            viewHolder.tag.setText(list.get(position).getDynasty() + " ● " + list.get(position).getP_num());
        }

        @Override
        protected int getLayoutId() {
            return com.myth.poetrycommon.R.layout.adapter_cipai;
        }

        @Override
        protected BaseHolder getHolder(View view) {
            return new PoetrySearchActivity.SearchListAdapter.ViewHolder(view);
        }

    }
}

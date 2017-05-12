package com.myth.poetrycommon.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.view.MyDecoration;

import java.util.List;



/**
 * Created by AndyMao on 17-4-18.
 */

public abstract class BaseSearchListActivity<T> extends BaseActivity {

    private View clear;

    protected RecyclerView mRecyclerView;

    public BaseAdapter adapter;

    public List<T> originList;

    public List<T> list;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cipai);
        setBottomGone();
        originList = getData();
        initView();
        refreshData();
    }

    public abstract List<T> getData();

    public abstract List<T> searchList(String word);

    public abstract int getSearchHint();

    public abstract BaseAdapter.OnItemClickListener getItemClickListener();

    public abstract BaseAdapter getSearchListAdapter();


    protected void refreshData() {
        String word = search.getText().toString().trim();
        if (TextUtils.isEmpty(word)) {
            list = originList;
        } else {
            list = searchList(word);
        }
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));


        adapter = getSearchListAdapter();
        adapter.setOnItemClickListener(getItemClickListener());
        mRecyclerView.setAdapter(adapter);
        search = (EditText) findViewById(R.id.search);
        search.clearFocus();
        search.setHint(getSearchHint());
        search.setHintTextColor(getResources().getColor(R.color.black_hint));
        search.setTextColor(getResources().getColor(R.color.black));
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                search.setText("");
                search.requestFocus();
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    clear.setVisibility(View.GONE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
                refreshData();
            }
        });

    }


}

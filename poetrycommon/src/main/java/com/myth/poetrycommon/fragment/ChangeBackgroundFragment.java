package com.myth.poetrycommon.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.BaseAdapter;
import com.myth.poetrycommon.adapter.ImageAdapter;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.MyDecoration;
import com.myth.poetrycommon.view.ShareView;

public class ChangeBackgroundFragment extends Fragment {

    private Context mContext;

    private Writing writing;

    private int bg_index = 0;

    private ShareView shareView;

    private RecyclerView mRecyclerView;

    public ChangeBackgroundFragment() {
    }

    public static ChangeBackgroundFragment getInstance(Writing writing) {
        ChangeBackgroundFragment fileViewFragment = new ChangeBackgroundFragment();
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        save();
    }

    public void save() {
        writing.bgimg = (bg_index + "");
    }

    private void refresh() {
        shareView.setWriting(writing);
    }

    private void initViews(View view) {


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDecoration(mContext, MyDecoration.VERTICAL_LIST));

        ImageAdapter adapter = new ImageAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bg_index = position;
                shareView.getBackgroundView().setDrawableId(BaseApplication.bgimgList[position]);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
        shareView = (ShareView) view.findViewById(R.id.share_view);
        shareView.setType(ShareView.TYPE_BACKGROUND);
        ResizeUtils.getInstance().layoutSquareView(shareView);
    }


}

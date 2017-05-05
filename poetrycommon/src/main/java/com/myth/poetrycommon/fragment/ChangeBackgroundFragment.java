package com.myth.poetrycommon.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.ImageAdapter;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.HorizontalListView;
import com.myth.poetrycommon.view.ShareView;

public class ChangeBackgroundFragment extends Fragment {

    private Context mContext;

    private Writing writing;

    private int bg_index = 0;

    private ShareView shareView;
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
        View view = inflater.inflate(R.layout.fragment_background,container, false);
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
        writing.bgimg=(bg_index + "");
    }

    private void refresh() {
        shareView.setWriting(writing);
    }

    private void initViews(View view) {

        HorizontalListView imgs = (HorizontalListView) view.findViewById(R.id.imgs);

        ImageAdapter adapter = new ImageAdapter(mContext);
        imgs.setAdapter(adapter);
        imgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bg_index = position;
                shareView.getBackgroundView().setDrawableId(BaseApplication.bgimgList[position]);
            }
        });

        shareView = (ShareView) view.findViewById(R.id.share_view);
        shareView.setType(ShareView.TYPE_BACKGROUND);
        ResizeUtils.getInstance().layoutSquareView(shareView);
    }


}

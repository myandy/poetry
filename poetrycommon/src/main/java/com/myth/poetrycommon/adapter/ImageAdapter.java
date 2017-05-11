package com.myth.poetrycommon.adapter;

import android.view.View;
import android.widget.ImageView;

import com.myth.poetrycommon.R;


public class ImageAdapter extends BaseAdapter<Integer> {

    @Override
    protected int getLayoutId() {
        return R.layout.item_image;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ImageHolder imageHolder = (ImageHolder) holder;
        imageHolder.mImageView.setImageResource(getItemData(position));
    }

    private static class ImageHolder extends BaseHolder {

        ImageView mImageView;

        public ImageHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv);
        }
    }
}

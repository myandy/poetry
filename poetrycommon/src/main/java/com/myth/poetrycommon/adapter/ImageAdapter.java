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

    @Override
    protected void setSelected(BaseHolder holder, boolean selected) {
        super.setSelected(holder, selected);
        ImageHolder imageHolder = (ImageHolder) holder;
        imageHolder.mSelected.setVisibility(selected ? View.VISIBLE : View.GONE);
    }

    private static class ImageHolder extends BaseHolder {

        ImageView mImageView;
        View mSelected;

        public ImageHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv);
            mSelected = view.findViewById(R.id.selected);
        }
    }
}

package com.myth.poetrycommon.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.StackView;
import android.widget.TextView;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.adapter.IntroAdapter;
import com.myth.poetrycommon.utils.ResizeUtils;

public class IntroductionView extends RelativeLayout {

    private Context mContext;

    private int[] mImages;

    private String mIntro;

    public IntroductionView(Context context, int[] images, String intro) {
        super(context);
        mContext = context;
        mImages = images;
        mIntro = intro;
        initView();
    }


    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_intro, this);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mIntro);
        final StackView stackView = (StackView) findViewById(R.id.stack_view);
        ResizeUtils.getInstance().layoutSquareView(stackView);

        IntroAdapter colorAdapter = new IntroAdapter(mContext, mImages);
        stackView.setAdapter(colorAdapter);
        stackView.getLayoutParams().height = ResizeUtils.getInstance().resize(600);
    }

}

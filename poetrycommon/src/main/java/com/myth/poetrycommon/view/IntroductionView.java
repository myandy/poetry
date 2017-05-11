package com.myth.poetrycommon.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
        View root = inflater.inflate(R.layout.layout_intro, null);

        TextView title = (TextView) root.findViewById(R.id.title);
        title.setText(mIntro);
        final StackView stackView = (StackView) root.findViewById(R.id.stack_view);
        ResizeUtils.getInstance().layoutSquareView(stackView);

        IntroAdapter colorAdapter = new IntroAdapter(mContext, mImages);
        stackView.setAdapter(colorAdapter);
        stackView.getLayoutParams().height = ResizeUtils.getInstance().resize(600);

        addView(root, new LayoutParams(-1, -1));
    }

}

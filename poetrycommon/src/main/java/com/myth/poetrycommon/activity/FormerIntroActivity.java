package com.myth.poetrycommon.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.entity.Former;

/**
 * Created by AndyMao on 17-5-3.
 */

public class FormerIntroActivity extends BaseActivity {

    private Former former;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        former = (Former) getIntent().getSerializableExtra("former");
        setContentView(R.layout.activity_former_intro);
        initView();
    }

    private void initView() {
        TextView content = (TextView) findViewById(R.id.content);
        content.setText(former.source);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(former.name);

        WebView intro = (WebView) findViewById(R.id.intro);
        intro.setBackgroundColor(0);
        intro.setVisibility(View.VISIBLE);
        WebSettings settings = intro.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        intro.loadUrl("file:///android_asset/intro.html");

    }
}

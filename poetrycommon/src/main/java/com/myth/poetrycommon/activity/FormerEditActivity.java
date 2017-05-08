package com.myth.poetrycommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.entity.Former;

/**
 * Created by AndyMao on 17-5-8.
 */

public class FormerEditActivity extends BaseActivity {
    private Former former;

    private boolean isEdit;

    private EditText etName;

    private EditText etIntro;
    private EditText etPingze;

    private boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_former_edit);

        if (getIntent().hasExtra("former")) {
            former = (Former) getIntent().getSerializableExtra("former");
        } else {
            former = new Former();
            former.type = Former.TYPE_MANUAL;
            former.id = (int) System.currentTimeMillis();
            isNew = true;
        }
        isEdit = former.isManual();

        initView();
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        etIntro = (EditText) findViewById(R.id.et_intro);
        etPingze = (EditText) findViewById(R.id.et_pingze);

        etName.setText(former.name);
        etIntro.setText(former.source);
        etPingze.setText(former.pingze);

        if (!isEdit) {
            etName.setEnabled(false);
            etIntro.setEnabled(false);
            etPingze.setEnabled(false);
            findViewById(R.id.button).setVisibility(View.GONE);
        } else {
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(etName.getText().toString().trim()) || TextUtils.isEmpty(etPingze.getText().toString().trim())) {
                        Toast.makeText(mActivity, R.string.former_add_value_null, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    former.name = etName.getText().toString().trim();
                    former.source = etIntro.getText().toString().trim();
                    former.pingze = etPingze.getText().toString().trim();
                    if (!isNew) {
                        FormerDatabaseHelper.update(former);
                        Toast.makeText(mActivity, R.string.former_save, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        FormerDatabaseHelper.add(former);
                        Toast.makeText(mActivity,R.string.former_add, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        }
    }
}

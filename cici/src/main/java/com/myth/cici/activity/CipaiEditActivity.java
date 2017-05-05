package com.myth.cici.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.poetrycommon.BaseActivity;

/**
 * Created by AndyMao on 17-4-25.
 */

public class CipaiEditActivity extends BaseActivity {
    private Cipai cipai;

    private boolean isEdit;

    private EditText etName;

    private EditText etIntro;
    private EditText etYunType;
    private EditText etYun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipai_edit);

        if (getIntent().hasExtra("cipai")) {
            isEdit = true;
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
        } else {
            cipai = new Cipai();
        }

        initView();
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        etIntro = (EditText) findViewById(R.id.et_intro);
        etYun = (EditText) findViewById(R.id.et_yun);

        if (isEdit) {
            etName.setText(cipai.name);
            etIntro.setText(cipai.source);
            etYun.setText(cipai.pingze);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                cipai.setName(name.getText().toString().trim());
//                cipai.setCipai(cipai.getText().toString().trim());
//                if (isEdit) {
//                    CipaiDatabaseHelper.update(cipai);
//                    Toast.makeText(mActivity, "已保存", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    CipaiDatabaseHelper.add(cipai);
//                    Toast.makeText(mActivity, "已添加", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            }
        });
    }
}

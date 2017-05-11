package com.myth.cici.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.myth.cici.R;
import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.BackupActivity;
import com.myth.poetrycommon.db.YunDatabaseHelper;
import com.myth.poetrycommon.utils.OthersUtils;

public class SettingActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setBottomVisible();
    }

    private void initView() {

        refreshYun();
        refreshTypeface();
        refreshCheck();

        findViewById(R.id.item_yun).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(YunDatabaseHelper.YUNString,
                        YunDatabaseHelper.getDefaultYunShu(), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                YunDatabaseHelper.setDefaultYunShu(mActivity, which);
                                refreshYun();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_typeface).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(BaseApplication.TypefaceString,
                        BaseApplication.getDefaultTypeface(mActivity), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseApplication.setDefaultTypeface(mActivity, which);
                                BaseApplication.instance.setTypeface(mActivity, BaseApplication.getDefaultTypeface(mActivity));
                                refreshTypeface();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_check).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] s = {mActivity.getString(R.string.check_true), mActivity.getString(R.string.check_false)};
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(s,
                        BaseApplication.getCheckAble(mActivity) ? 0 : 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseApplication.setCheckAble(mActivity, which == 0);
                                refreshCheck();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_about).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AboutActivity.class));
            }
        });

        final TextView username = (TextView) findViewById(R.id.username_value);
        String name = BaseApplication.getDefaultUserName();
        if (!TextUtils.isEmpty(name)) {
            username.setText(name);
        }

        findViewById(R.id.item_username).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText et = new EditText(mActivity);
                et.setText(username.getText());
                new AlertDialog.Builder(mActivity).setTitle(R.string.username_input).setIcon(android.R.drawable.ic_dialog_info).setView(
                        et).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        username.setText(et.getText().toString().trim());
                        BaseApplication.setDefaultUserName(et.getText().toString().trim());
                    }
                }).setNegativeButton(R.string.cancel, null).show();
            }
        });

        findViewById(R.id.item_congratuate_us).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (OthersUtils.isValidIntent(mActivity, intent)) {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.item_weibo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.weibo.com/anddymao"));
                startActivity(intent);
            }
        });

        findViewById(R.id.item_backup).setOnClickListener(this);

    }

    private void refreshYun() {
        ((TextView) findViewById(R.id.yun_value)).setText(YunDatabaseHelper.YUNString[YunDatabaseHelper.getDefaultYunShu()]);
    }

    private void refreshTypeface() {
        ((TextView) findViewById(R.id.typeface_value)).setText(BaseApplication.TypefaceString[BaseApplication.getDefaultTypeface(mActivity)]);
        ((TextView) findViewById(R.id.yun_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.yun_value)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.typeface_value)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.typeface_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.check_value)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.check_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.about_title)).setTypeface(BaseApplication.instance.getTypeface());

        ((TextView) findViewById(R.id.username_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.username_value)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.weibo_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.weibo_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.backup_title)).setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) findViewById(R.id.congratuate_us_title)).setTypeface(BaseApplication.instance.getTypeface());
    }

    private void refreshCheck() {
        if (BaseApplication.getCheckAble(mActivity)) {
            ((TextView) findViewById(R.id.check_value)).setText(R.string.check_true);
        } else {
            ((TextView) findViewById(R.id.check_value)).setText(R.string.check_false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_backup:
                Intent backupIntent = new Intent(mActivity, BackupActivity.class);
                startActivity(backupIntent);
                break;
        }
    }
}

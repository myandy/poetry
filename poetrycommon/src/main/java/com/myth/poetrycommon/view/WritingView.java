package com.myth.poetrycommon.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.activity.EditActivity;
import com.myth.poetrycommon.activity.ShareActivity;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.DateUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.GCDialog.OnCustomDialogListener;

public class WritingView extends LinearLayout {

    private Context mContext;

    private Writing writing;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    private OnDeleteListener mOnDeleteListener;

    public WritingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WritingView(Context context, Writing writing) {
        super(context);
        this.writing = writing;
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_writing, this);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        ShareView shareView = (ShareView) findViewById(R.id.share_view);
        ResizeUtils.getInstance().layoutSquareView(shareView);

        shareView.setWriting(writing);

        TextView time = (TextView) findViewById(R.id.time);
        time.setText(DateUtils.longToFormat(writing.update_dt, DateUtils.YMD_HM_FORMAT));


        final AlertDialog dialog = new AlertDialog.Builder(mContext).setItems(new String[]{"分享", "编辑", "删除"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(mContext, ShareActivity.class);
                            intent.putExtra("writing", writing);
                            mContext.startActivity(intent);
                        } else if (which == 1) {
                            Intent intent = new Intent(mContext, EditActivity.class);
                            intent.putExtra("writing", writing);
                            mContext.startActivity(intent);
                        } else if (which == 2) {
                            Bundle bundle = new Bundle();
                            bundle.putString(GCDialog.DATA_TITLE, mContext.getString(R.string.delete_title));
                            bundle.putString(GCDialog.DATA_CONTENT, mContext.getString(R.string.delete_content));
                            bundle.putString(GCDialog.CONFIRM_TEXT, mContext.getString(R.string.delete));
                            new GCDialog(mContext, new OnCustomDialogListener() {

                                @Override
                                public void onConfirm() {
                                    WritingDatabaseHelper.deleteWriting(writing);
                                    if (mOnDeleteListener != null) {
                                        mOnDeleteListener.onDelete();
                                    }
                                }

                                @Override
                                public void onCancel() {
                                }
                            }, bundle).show();
                        }

                        dialog.dismiss();

                    }
                }).create();

        findViewById(R.id.content_linear).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }
            }
        });

    }

    public interface OnDeleteListener {
        void onDelete();
    }

}

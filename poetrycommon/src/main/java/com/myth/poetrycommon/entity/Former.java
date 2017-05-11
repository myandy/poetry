package com.myth.poetrycommon.entity;

import android.text.TextUtils;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;

import java.io.Serializable;

public class Former implements Serializable, ISearchAble {

    /**
     * 注释内容
     */
    public static final long serialVersionUID = 1L;


    public int id;

    public String name;

    public String pingze;

    public String source;

    public int wordcount;

    public int type;

    public static int TYPE_MANUAL = 4;

    public boolean isManual() {
        return type == TYPE_MANUAL;
    }

    @Override
    public String getSearchText() {
        return name;
    }

    @Override
    public String getShowTitle() {
        return name;
    }

    @Override
    public String getShowDesc() {
        if (TextUtils.isEmpty(pingze)) {
            return "";
        }
        return pingze.replaceAll("\\n", " ");
    }

    @Override
    public String getShowTag() {
        String tag;
        if (type == Former.TYPE_MANUAL) {
            tag = BaseApplication.instance.getString(R.string.manual);
        } else {
            if (wordcount == 0) {
                tag = BaseApplication.instance.getString(R.string.no_limit);
            } else {
                tag = String.format("%d字", wordcount);
            }
        }
        return tag;
    }
}

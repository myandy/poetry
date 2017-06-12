package com.myth.poetrycommon.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Writing implements Serializable,ISearchAble
{

    /**
     * 注释内容
     */
    public static final long serialVersionUID = 1L;

    public int id;

    public String text;

    public String title;

    public long create_dt;

    public long update_dt;

    public String bgimg;

    public int layout;

    public transient Bitmap bitmap;

    public Former former;
    
    public String author;

    public String content;

    public String desc;

    public int formerId;

    @Override
    public String getSearchText() {
        return title+text;
    }

    @Override
    public String getShowTitle() {
        return title;
    }

    @Override
    public String getShowDesc() {
        return content;
    }

    @Override
    public String getShowTag() {
        return desc;
    }


}

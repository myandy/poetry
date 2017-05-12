package com.myth.shishi.entity;

import com.myth.poetrycommon.entity.ISearchAble;

import java.io.Serializable;

public class Author implements Serializable, ISearchAble {

    public static final long serialVersionUID = 1L;


    public String author;

    public String intro;

    public String dynasty;

    public int p_num;

    public String en_name;

    public int color;


    @Override
    public String getSearchText() {
        return author;
    }

    @Override
    public String getShowTitle() {
        return author;
    }

    @Override
    public String getShowDesc() {
        return "";
    }

    @Override
    public String getShowTag() {
        return dynasty + " ● " + p_num;
    }


}

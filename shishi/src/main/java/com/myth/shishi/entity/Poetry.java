package com.myth.shishi.entity;

import com.myth.poetrycommon.entity.ISearchAble;
import com.myth.poetrycommon.entity.Writing;

import java.io.Serializable;

public class Poetry implements Serializable, ISearchAble {

    /**
     * 注释内容
     */
    public static final long serialVersionUID = 1L;

    public String author;

    public String intro;

    public String title;

    public String poetry;

    public int collect;

    public int id;

    @Override
    public String getSearchText() {
        return title + poetry + author;
    }

    public String getShowTitle() {
        return title;
    }

    @Override
    public String getShowDesc() {
        return poetry.replaceAll("\\n", " ");
    }

    @Override
    public String getShowTag() {
        return author;
    }


    public Writing toWriting() {
        Writing writing = new Writing();
        writing.content = poetry.replaceAll("[\\[\\]0-9]", "");
        writing.author = author;
        writing.title = title;
        writing.formerId = -1;
        return writing;
    }

}

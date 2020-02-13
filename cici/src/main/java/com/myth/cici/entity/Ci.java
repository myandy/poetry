package com.myth.cici.entity;

import com.myth.poetrycommon.entity.ISearchAble;
import com.myth.poetrycommon.entity.Writing;

import java.io.Serializable;

public class Ci implements Serializable, ISearchAble {

    public static final long serialVersionUID = 1L;

    public int id;

    public String author;

    public String text;

    public String note;

    public int ci_id;

    public Cipai cipai;

    public int collect;

    public String cipai_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCi_id() {
        return ci_id;
    }

    public void setCi_id(int ci_id) {
        this.ci_id = ci_id;
    }

    public Writing toWriting() {
        Writing writing = new Writing();
        if (cipai != null) {
            writing.text = cipai.name;
        }
        writing.content = text;
        writing.author = author;
        writing.former = cipai;
        return writing;
    }

    @Override
    public String getSearchText() {
        return text + cipai_name;
    }

    @Override
    public String getShowTitle() {
        return cipai_name;
    }

    @Override
    public String getShowDesc() {
        return text;
    }

    @Override
    public String getShowTag() {
        return author;
    }
}

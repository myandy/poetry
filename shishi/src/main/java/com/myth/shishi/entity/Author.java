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

//    public String getIntro() {
//        return intro;
//    }
//
//    public void setIntro(String intro) {
//        this.intro = intro;
//    }
//
//    public int getP_num() {
//        return p_num;
//    }
//
//    public void setP_num(int p_num) {
//        this.p_num = p_num;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getDynasty() {
//        return dynasty;
//    }
//
//    public void setDynasty(String dynasty) {
//        this.dynasty = dynasty;
//    }
//
//    public String getEn_name() {
//        return en_name;
//    }


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
        return intro;
    }

    @Override
    public String getShowTag() {
        return dynasty + " ● " + p_num;
    }


}

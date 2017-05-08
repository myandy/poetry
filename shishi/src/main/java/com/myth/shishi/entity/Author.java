package com.myth.shishi.entity;

import java.io.Serializable;

public class Author implements Serializable
{

    public static final long serialVersionUID = 1L;


    public String author;
    
    public String intro;

    public String dynasty;
    
    public int p_num;
    
    public String en_name;

    public int color;

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public int getP_num()
    {
        return p_num;
    }

    public void setP_num(int p_num)
    {
        this.p_num = p_num;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDynasty()
    {
        return dynasty;
    }

    public void setDynasty(String dynasty)
    {
        this.dynasty = dynasty;
    }

    public String getEn_name()
    {
        return en_name;
    }

}

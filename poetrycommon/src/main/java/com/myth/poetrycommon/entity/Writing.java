package com.myth.poetrycommon.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Writing implements Serializable
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

    public Bitmap bitmap;

    public Former former;
    
    public String author;

    public String content;

    public String desc;

    public int formerId;

//    public int getId()
//    {
//        return id;
//    }
//
//    public void setId(int id)
//    {
//        this.id = id;
//    }
//
//
//    public String getText()
//    {
//        return text;
//    }
//
//    public void setText(String text)
//    {
//        this.text = text;
//    }
//
//
//    public long getCreate_dt()
//    {
//        return create_dt;
//    }
//
//    public void setCreate_dt(long create_dt)
//    {
//        this.create_dt = create_dt;
//    }
//
//    public long getUpdate_dt()
//    {
//        return update_dt;
//    }
//
//    public void setUpdate_dt(long update_dt)
//    {
//        this.update_dt = update_dt;
//    }
//
//    public String getBgimg()
//    {
//        return bgimg;
//    }
//
//    public void setBgimg(String bgimg)
//    {
//        this.bgimg = bgimg;
//    }
//
//    public int getLayout()
//    {
//        return layout;
//    }
//
//    public void setLayout(int layout)
//    {
//        this.layout = layout;
//    }
//
//
//    public Bitmap getBitmap()
//    {
//        return bitmap;
//    }
//
//    public void setBitmap(Bitmap bitmap)
//    {
//        this.bitmap = bitmap;
//    }
//
//    public Former getFormer()
//    {
//        return former;
//    }
//
//    public void setFormer(Former former)
//    {
//        this.former = former;
//    }
//
//    public String getTitle()
//    {
//        return title;
//    }
//
//    public void setTitle(String title)
//    {
//        this.title = title;
//    }
//
//    public String getAuthor()
//    {
//        return author;
//    }
//
//    public void setAuthor(String author)
//    {
//        this.author = author;
//    }
    
//    public Poetry toPoetry(){
//        Poetry poetry =new Poetry();
//        poetry.setPoetry(text);
//        poetry.setTitle(title);
//
//        return poetry;
//
//    }
//
//    public static ArrayList<Poetry> getPoetryList(List<Writing> writings){
//        ArrayList<Poetry> list=new ArrayList<Poetry>();
//
//        for(Writing writing:writings){
//            list.add(writing.toPoetry());
//        }
//        return list;
//
//
//    }


}

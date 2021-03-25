package com.example.eleme.wjl.bean;

public class BeanGoods {

    private  String name;
    private  String titlename;
    private  String tag;
    private  String num;
    private  String vipprice;
    private  String price;
    private  String des;
    private  int img;
    private String goodsid;
    private  String classid;

    public String getClassid()
    {
        return classid;
    }

    public void setClassid(String classid)
    {
        this.classid = classid;
    }

    public String getGoodsid()
    {
        return goodsid;
    }

    public void setGoodsid(String goodsid)
    {
        this.goodsid = goodsid;
    }

    public  boolean isSelected;

    public String getTitlename()
    {
        return titlename;
    }

    public void setTitlename(String titlename)
    {
        this.titlename = titlename;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getVipprice() {
        return vipprice;
    }

    public void setVipprice(String vipprice) {
        this.vipprice = vipprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

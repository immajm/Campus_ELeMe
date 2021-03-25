package com.example.eleme.data;

public class OneChange {
//    public int goodsId;
    public int goodsImg;
    public String goodsName;
    public double price;
    public double price_d;
    public String goodsmsg;
//    public int class_id;


    public OneChange(int goodsImg, String goodsName, double price, double price_d, String goodsmsg) {
        this.goodsImg = goodsImg;
        this.goodsName = goodsName;
        this.price = price;
        this.price_d = price_d;
        this.goodsmsg = goodsmsg;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice_d() {
        return price_d;
    }

    public void setPrice_d(double price_d) {
        this.price_d = price_d;
    }

    public String getGoodsmsg() {
        return goodsmsg;
    }

    public void setGoodsmsg(String goodsmsg) {
        this.goodsmsg = goodsmsg;
    }
}

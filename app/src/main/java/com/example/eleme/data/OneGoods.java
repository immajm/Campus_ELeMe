package com.example.eleme.data;

public class OneGoods {
    public int goodsId;
    public int goodsImg;
    public String goodsName;
    public float price;
    public float price_d;
    public int goodsmsg;
    public int class_id;

    public OneGoods(int goodsId, int goodsImg, String goodsName, float price, float price_d, int goodsmsg, int class_id) {
        this.goodsId = goodsId;
        this.goodsImg = goodsImg;
        this.goodsName = goodsName;
        this.price = price;
        this.price_d = price_d;
        this.goodsmsg = goodsmsg;
        this.class_id = class_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice_d() {
        return price_d;
    }

    public void setPrice_d(float price_d) {
        this.price_d = price_d;
    }

    public int getGoodsmsg() {
        return goodsmsg;
    }

    public void setGoodsmsg(int goodsmsg) {
        this.goodsmsg = goodsmsg;
    }
}

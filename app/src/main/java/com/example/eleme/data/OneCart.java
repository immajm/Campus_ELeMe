package com.example.eleme.data;

public class OneCart {
    int goodsImg;
    String goodName;
    int count;
    double price_d;
    double price;

    public OneCart(int goodsImg, String goodName, int count, double price_d, double price) {
        this.goodsImg = goodsImg;
        this.goodName = goodName;
        this.count = count;
        this.price_d = price_d;
        this.price = price;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice_d() {
        return price_d;
    }

    public void setPrice_d(double price_d) {
        this.price_d = price_d;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

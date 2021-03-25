package com.example.eleme.data;

import java.util.List;

public class OneShop {
    public int shopId;
    public int shopImg;
    public String shopName;
    public Double shopStart;
    public List<FullRedution> fr;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getShopImg() {
        return shopImg;
    }

    public void setShopImg(int shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public OneShop(int shopId, int shopImg, String shopName, Double shopStart, List<FullRedution> fr) {
        this.shopId = shopId;
        this.shopImg = shopImg;
        this.shopName = shopName;
        this.shopStart = shopStart;
        this.fr = fr;
    }

    public Double getShopStart() {
        return shopStart;
    }

    public void setShopStart(Double shopStart) {
        this.shopStart = shopStart;
    }

    public List<FullRedution> getFr() {
        return fr;
    }

    public void setFr(List<FullRedution> fr) {
        this.fr = fr;
    }


}

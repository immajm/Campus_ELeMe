package com.example.eleme.data;

public class OneshopCart {
    int shopId;
    String shopname;

    public OneshopCart(int shopId, String shopname) {
        this.shopId = shopId;
        this.shopname = shopname;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}

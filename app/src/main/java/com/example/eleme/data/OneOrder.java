package com.example.eleme.data;

import java.util.List;

public class OneOrder {
    int orderId;
    String shopName;
    int shopImg;
    List<String> goods;
    int goodsCount;//一个订单中 商品有几种
    float finalPrice;
    String condition;
    String ifComment;
    int addressId;
    int ticketId;
    int fullRedutionId;
    int riderId;

    public OneOrder(int orderId, String shopName, int shopImg, List<String> goods, int goodsCount, float finalPrice, String condition, String ifComment, int addressId, int ticketId, int fullRedutionId, int riderId) {
        this.orderId = orderId;
        this.shopName = shopName;
        this.shopImg = shopImg;
        this.goods = goods;
        this.goodsCount = goodsCount;
        this.finalPrice = finalPrice;
        this.condition = condition;
        this.ifComment = ifComment;
        this.addressId = addressId;
        this.ticketId = ticketId;
        this.fullRedutionId = fullRedutionId;
        this.riderId = riderId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getFullRedutionId() {
        return fullRedutionId;
    }

    public void setFullRedutionId(int fullRedutionId) {
        this.fullRedutionId = fullRedutionId;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIfComment() {
        return ifComment;
    }

    public void setIfComment(String ifComment) {
        this.ifComment = ifComment;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopImg() {
        return shopImg;
    }

    public void setShopImg(int shopImg) {
        this.shopImg = shopImg;
    }

    public List<String> getGoods() {
        return goods;
    }

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPricel) {
        this.finalPrice = finalPricel;
    }
}

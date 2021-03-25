package com.example.eleme.data;

public class OneTicket {
    String tName;
    float  tMoney;
    String shopname;
    int ticketCount;

    public OneTicket(String tName, float tMoney, String shopname, int ticketCount) {
        this.tName = tName;
        this.tMoney = tMoney;
        this.shopname = shopname;
        this.ticketCount = ticketCount;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public float gettMoney() {
        return tMoney;
    }

    public void settMoney(float tMoney) {
        this.tMoney = tMoney;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}

package com.example.eleme.data;

public class OneAddress {
    int addressId;
    String address;
    String name;
    String tel;

    public OneAddress(int id,String address, String name, String tel) {
        this.addressId=id;
        this.address = address;
        this.name = name;
        this.tel = tel;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

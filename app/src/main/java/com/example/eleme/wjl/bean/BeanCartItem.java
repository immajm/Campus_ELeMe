package com.example.eleme.wjl.bean;

/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/15 - 12:55
 */
public class BeanCartItem
{
    private int id;
    private  BeanGoods beanGoods;
    private  int num;
    private  double price;

    public double getPrice()
    {
        return price;
    }

    public BeanCartItem(BeanGoods beanGoods, int num)
    {
        this.beanGoods = beanGoods;
        this.num = num;
        this.price=Double.parseDouble(this.beanGoods.getPrice())*this.num;
    }

    public BeanCartItem()
    {
    }

    public BeanGoods getBeanGoods()
    {
        return beanGoods;
    }

    public void setBeanGoods(BeanGoods beanGoods)
    {
        this.beanGoods = beanGoods;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }
}

package com.example.eleme.wjl.bean;

import java.util.List;

/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/10 - 13:42
 */
public class BeanMenu
{
    private  int menuid;
    private  String menuname;
    public   boolean isSelected;
    private  List<BeanGoods>cata;

    public int getMenuid()
    {
        return menuid;
    }

    public void setMenuid(int menuid)
    {
        this.menuid = menuid;
    }

    public void setCata(List<BeanGoods> cata)
    {
        this.cata = cata;
    }
public  int getCataSize()
{
    return cata.size();
}
    public String getMenuname()
    {
        return menuname;
    }

    public void setMenuname(String menuname)
    {
        this.menuname = menuname;
    }


}

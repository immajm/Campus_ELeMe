package com.example.eleme.HomeFragment1;

/**
 * 朋友圈数据对象，一项一个实例;
 */
public class BeanStore {
    private int Home_ShopIcon;
    private String Home_ShopId;
    private String Home_ShopName;
    private String Home_Level;
    private String Home_agvCost;
    private String Home_Introduce;

    public BeanStore(int Home_ShopIcon,String Home_ShopId, String Home_ShopName, String Home_Level, String Home_agvCost, String Home_Introduce) {
        this.Home_ShopIcon = Home_ShopIcon;
        this.Home_ShopId = Home_ShopId;
        this.Home_ShopName = Home_ShopName;
        this.Home_Level = Home_Level;
        this.Home_agvCost = Home_agvCost;
        this.Home_Introduce = Home_Introduce;
    }

    public String getHome_ShopId() {
        return Home_ShopId;
    }

    public void setHome_ShopId(String home_ShopId) {
        Home_ShopId = home_ShopId;
    }

    public int getHome_ShopIcon() {
        return Home_ShopIcon;
    }

    public void setHome_ShopIcon(int home_ShopIcon) {
        Home_ShopIcon = home_ShopIcon;
    }

    public String getHome_ShopName() {
        return Home_ShopName;
    }

    public void setHome_ShopName(String home_ShopName) {
        Home_ShopName = home_ShopName;
    }

    public String getHome_Level() {
        return Home_Level;
    }

    public void setHome_Level(String home_Level) {
        Home_Level = home_Level;
    }

    public String getHome_agvCost() {
        return Home_agvCost;
    }

    public void setHome_agvCost(String home_agvCost) {
        Home_agvCost = home_agvCost;
    }

    public String getHome_Introduce() {
        return Home_Introduce;
    }

    public void setHome_Introduce(String home_Introduce) {
        Home_Introduce = home_Introduce;
    }
}

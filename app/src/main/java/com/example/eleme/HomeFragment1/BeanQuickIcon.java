package com.example.eleme.HomeFragment1;

/**
 * 朋友圈数据对象，一项一个实例;
 */
public class BeanQuickIcon {
    private int home_quickicon_icon;
    private String home_quickicon_name;

    public BeanQuickIcon(int home_quickicon_icon, String home_quickicon_name) {
        this.home_quickicon_icon = home_quickicon_icon;
        this.home_quickicon_name = home_quickicon_name;
    }

    public int getHome_quickicon_icon() {
        return home_quickicon_icon;
    }

    public void setHome_quickicon_icon(int home_quickicon_icon) {
        this.home_quickicon_icon = home_quickicon_icon;
    }

    public String getHome_quickicon_name() {
        return home_quickicon_name;
    }

    public void setHome_quickicon_name(String home_quickicon_name) {
        this.home_quickicon_name = home_quickicon_name;
    }
}

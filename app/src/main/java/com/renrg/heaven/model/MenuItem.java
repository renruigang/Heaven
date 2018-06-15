package com.renrg.heaven.model;

/**
 * Created by Hello on 2015/8/5.
 */
public class MenuItem {
    public MenuItem(String text, boolean isSelected, int icon, int iconSelected) {
        this.text = text;
        this.isSelected = isSelected;
        this.icon = icon;
        this.iconSelected = iconSelected;
    }

    public boolean isSelected;
    public String text;
    public int icon;
    public int iconSelected;
}

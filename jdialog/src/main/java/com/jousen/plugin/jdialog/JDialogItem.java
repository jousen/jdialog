package com.jousen.plugin.jdialog;


/**
 * @author 李易航
 * @date 2021/3/6
 */
public class JDialogItem {
    public int icon;
    public String text;

    public JDialogItem() {
        this.icon = 0;
        this.text = "";
    }

    public JDialogItem(String text) {
        this.icon = 0;
        this.text = text;
    }

    public JDialogItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }
}
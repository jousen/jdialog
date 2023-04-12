package com.jousen.plugin.jdialog.adapter;

public class JDialogItem {
    public int icon;
    public String text;

    public JDialogItem(String text) {
        this.icon = 0;
        this.text = text;
    }

    public JDialogItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }
}
package com.jousen.plugin.jdialog;

public class StrSub {
    public static String limit(String string, int limit) {
        if (string == null) {
            return "";
        }
        if (limit == 0 || string.length() < limit) {
            return string;
        }
        return string.substring(0, limit) + "…";
    }
}

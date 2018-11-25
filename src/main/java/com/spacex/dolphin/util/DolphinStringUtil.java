package com.spacex.dolphin.util;

public class DolphinStringUtil {
    public static String lowerFirstChar(String text) {
        char[] chars = text.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

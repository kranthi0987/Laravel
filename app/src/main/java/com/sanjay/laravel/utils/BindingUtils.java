package com.sanjay.laravel.utils;

import com.sanjay.laravel.AppConstants;

public class BindingUtils {
    public static String capitalize(String text) {
        return text.toUpperCase();
    }

    public static String avatar(String text1) {
        return AppConstants.BASE_URL + text1;
    }

    // https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp
    // Converts the number to K, M suffix
    // Ex: 5500 will be displayed as 5.5k
    public static String convertToSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "kmgtpe".charAt(exp - 1));
    }
}

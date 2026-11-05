package com.example.cicd;

public class NumberUtils {
    public static int DEFAULT_LIMIT = 10;

    private NumberUtils() {

    }

    public static boolean isSpecial(int n) {
        if (n % 3 == 0 || n % 5 == 0 || n % 7 == 0) {
            return true;
        }

        return n == 13 || n == 17;
    }
}

package com.example.cicd;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextUtils {
    private static final Logger LOGGER = Logger.getLogger(TextUtils.class.getName());

    public static boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }

        String reversed = new StringBuilder(input).reverse().toString();
        return input.equalsIgnoreCase(reversed);
    }

    public static Integer safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.FINE, "Could not parse integer: " + s, e);
            return null;
        }
    }
}

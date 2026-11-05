package com.example.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberUtilsTest {
    @Test
    void isSpecial_divisibleByThreeFiveOrSeven_returnsTrue() {
        assertTrue(NumberUtils.isSpecial(3));
        assertTrue(NumberUtils.isSpecial(5));
        assertTrue(NumberUtils.isSpecial(7));
        assertTrue(NumberUtils.isSpecial(15));
    }

    @Test
    void isSpecial_thirteenOrSeventeen_returnsTrue() {
        assertTrue(NumberUtils.isSpecial(13));
        assertTrue(NumberUtils.isSpecial(17));
    }

    @Test
    void isSpecial_otherNumbers_returnsFalse() {
        assertFalse(NumberUtils.isSpecial(1));
        assertFalse(NumberUtils.isSpecial(2));
        assertFalse(NumberUtils.isSpecial(4));
    }
}

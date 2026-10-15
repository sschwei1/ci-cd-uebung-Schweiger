package com.example.cicd;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Hinweise:
 * - Ergänze weitere Testfälle (z. B. negative Zahlen, große Werte)
 * - Diskutiere das Verhalten von divide(… , 0) und entscheide in Ü2, ob Exception sinnvoller ist
 */
class CalculatorTest {

    @Test
    void add_shouldSumTwoIntegers() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
        // TODO: weitere Grenzfälle (z. B. negative Zahlen)
    }

    @Test
    void divide_byZero_shouldReturnZero_currentBehavior() {
        Calculator c = new Calculator();
        // Aktuelles (schlechtes) Verhalten absichtlich bestätigt – Refactoring in Ü2
        assertEquals(0, c.divide(10, 0));
    }

    @Test
    void sum_methods_shouldProduceSameResult() {
        Calculator c = new Calculator();
        List<Integer> nums = Arrays.asList(1, 2, 3, null, 4);
        assertEquals(c.sumUp(nums), c.addAll(nums));
    }

    @Test
    void add_shouldSumNegativeNumbers() {
        Calculator c = new Calculator();
        assertEquals(-5, c.add(-2, -3));
    }

    @Test
    void subtract_shouldReturnDifference() {
        Calculator c = new Calculator();
        assertEquals(2, c.subtract(5, 3));
        assertEquals(-8, c.subtract(0, 8));
    }

    @Test
    void divide_shouldReturnIntegerQuotient() {
        Calculator c = new Calculator();
        assertEquals(2, c.divide(5, 2));
        assertEquals(-2, c.divide(-6, 3));
    }

    @Test
    void sumUp_shouldIgnoreNullValues() {
        Calculator c = new Calculator();
        assertEquals(6, c.sumUp(Arrays.asList(1, null, 2, null, 3)));
    }

    @Test
    void addAll_emptyList_returnsZero() {
        Calculator c = new Calculator();
        assertEquals(0, c.addAll(List.of()));
    }
}

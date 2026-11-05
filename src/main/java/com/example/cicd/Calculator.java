package com.example.cicd;

import java.util.List;

public class Calculator {
    public static int MAX_OPERANDS = 100;

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero");
        }
        return a / b;
    }

    public int sumUp(List<Integer> nums) {
        return addAll(nums);
    }

    public int addAll(List<Integer> nums) {
        int sum = 0;
        for (Integer n : nums) {
            if (n != null) {
                sum += n;
            }
        }
        return sum;
    }
}

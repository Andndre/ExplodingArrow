package me.Andre.ExplodingArrow;

import java.util.Random;

public class Math {
	static Random rand = new Random();

    // https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
    public static String intToRoman(int number) {
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 3999;
        final String[] RN_M = {"", "M", "MM", "MMM"};
        final String[] RN_C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        final String[] RN_X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        final String[] RN_I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                    String.format(
                            "The number must be in the range [%d, %d]",
                            MIN_VALUE,
                            MAX_VALUE
                    )
            );
        }

        return RN_M[number / 1000] +
                RN_C[number % 1000 / 100] +
                RN_X[number % 100 / 10] +
                RN_I[number % 10];
    }
	
	public static int random(int min, int max) {
		return rand.nextInt(max + 1 - min) + min;
	}
}

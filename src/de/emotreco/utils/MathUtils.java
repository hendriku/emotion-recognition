package de.emotreco.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {

    /**
     * Rounds a number to 4 digits after the decimal separator for printing purposes.
     * This function is using float values.
     *
     * @param number the number to round
     * @return the string of the rounded number
     */
    public static String round(float number) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number);
    }

    /**
     * Rounds a number to 4 digits after the decimal separator for printing purposes.
     * This function is using double values.
     *
     * @param number the number to round
     * @return the string of the rounded number
     */
    public static String round(double number) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number);
    }

}

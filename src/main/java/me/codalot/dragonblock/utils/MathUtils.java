package me.codalot.dragonblock.utils;

public class MathUtils {

    public static <T extends Number> T clamp(T number, T min, T max) {
        if (number.doubleValue() < min.doubleValue())
            return min;
        else if (number.doubleValue() > max.doubleValue())
            return max;
        return number;
    }

}

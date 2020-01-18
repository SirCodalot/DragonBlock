package me.codalot.dragonblock.utils;

import org.bukkit.Color;

public class ColorUtils {

    public static Color lerp(Color from, Color to, double progress) {
        int red = (int) MathUtils.lerp(from.getRed(), to.getRed(), progress);
        int green = (int) MathUtils.lerp(from.getGreen(), to.getGreen(), progress);
        int blue = (int) MathUtils.lerp(from.getBlue(), to.getBlue(), progress);

        return Color.fromRGB(red, green, blue);
    }

}

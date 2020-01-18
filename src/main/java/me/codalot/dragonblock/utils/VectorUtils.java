package me.codalot.dragonblock.utils;

import org.bukkit.util.Vector;

public class VectorUtils {

    public static double getMagnitude(Vector vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2));
    }

}

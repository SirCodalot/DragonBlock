package me.codalot.dragonblock.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {

    public static double getMagnitude(Vector vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2));
    }

    public static Vector lerp(Vector from, Vector to, double progress) {
        double x = MathUtils.lerp(from.getX(), to.getX(), progress);
        double y = MathUtils.lerp(from.getY(), to.getY(), progress);
        double z = MathUtils.lerp(from.getZ(), to.getZ(), progress);

        return new Vector(x, y, z);
    }

    public static Vector lerp(Vector direction, Location location, Location target, double progress) {
        return lerp(direction, target.toVector().subtract(location.toVector()).normalize(), progress);
    }

}

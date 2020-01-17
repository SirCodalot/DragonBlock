package me.codalot.dragonblock.utils;

import net.minecraft.server.v1_15_R1.MinecraftServer;

public class TimeUtils {

    public static boolean every(int count) {
        return MinecraftServer.currentTick % count == 0;
    }

    public static boolean hasTimePassed(int since, int count) {
        return since + 20 < MinecraftServer.currentTick;
    }

}

package me.codalot.dragonblock.utils;

import me.codalot.dragonblock.DragonBlock;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EventUtils {

    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(DragonBlock.getInstance(), () -> Bukkit.getServer().getPluginManager().callEvent(event));
    }

}

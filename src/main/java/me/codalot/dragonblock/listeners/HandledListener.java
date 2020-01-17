package me.codalot.dragonblock.listeners;

import me.codalot.dragonblock.DragonBlock;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class HandledListener implements Listener {

    protected DragonBlock plugin;

    public HandledListener(DragonBlock plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

}

package me.codalot.dragonblock.listeners.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.listeners.HandledListener;
import me.codalot.dragonblock.players.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class DataListener extends HandledListener {

    public DataListener(DragonBlock plugin) {
        super(plugin);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        plugin.getPlayers().getData(event.getUniqueId());
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> plugin.getPlayers().getData(event).onJoin(), 10);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayers().getData(event).onQuit();
    }

}

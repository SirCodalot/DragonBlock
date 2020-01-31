package me.codalot.dragonblock.managers.types;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class FighterManager implements Manager {

    @Getter private static Map<UUID, Fighter> fighters;

    private static BukkitTask updateTask;

    public FighterManager() {
        fighters = new HashMap<UUID, Fighter>();
    }

    @Override
    public void load() {
        fighters = new HashMap<UUID, Fighter>();
        updateTask = Bukkit.getScheduler().runTaskTimer(DragonBlock.getInstance(),
                () -> fighters.values().forEach(Fighter::update), 10, 1);
    }

    @Override
    public void save() {
        new HashSet<>(fighters.values()).forEach(Fighter::unload);
        updateTask.cancel();
    }

    public Fighter getFighter(UUID uuid) {
        return fighters.get(uuid);
    }

    public Fighter getFighter(Entity entity) {
        return getFighter(entity.getUniqueId());
    }

    public Fighter getFighter(PlayerEvent event) {
        return getFighter(event.getPlayer());
    }

    public Fighter getFighter(int id) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (((CraftPlayer) player).getHandle().getId() == id)
                return getFighter(player);
        }
        return null;
    }
}

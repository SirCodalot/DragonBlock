package me.codalot.dragonblock.managers.types;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.models.ModelStand;
import me.codalot.dragonblock.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ModelManager implements Manager {

    private static Set<ModelStand> models;
    private static BukkitTask task;

    public ModelManager() {
        models = new HashSet<>();
    }

    @Override
    public void load() {
        task = Bukkit.getScheduler().runTaskTimer(DragonBlock.getInstance(),
                () -> models.forEach(ModelStand::update), 10, 1);
    }

    @Override
    public void save() {
        models.clear();
        task.cancel();
    }
}

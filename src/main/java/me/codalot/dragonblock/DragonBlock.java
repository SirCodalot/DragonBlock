package me.codalot.dragonblock;

import lombok.Getter;
import me.codalot.dragonblock.managers.Manager;
import me.codalot.dragonblock.managers.types.*;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DragonBlock extends JavaPlugin implements Manager {

    @Getter private static DragonBlock instance;

    private ListenerManager listeners;
    private ModelManager models;
    private FighterManager fighters;
    private PlayerManager players;
    private CommandManager commands;

    @Override
    public void onEnable() {
        instance = this;

        listeners = new ListenerManager();
        models = new ModelManager();
        players = new PlayerManager();
        fighters = new FighterManager();
        commands = new CommandManager();

        load();
    }

    @Override
    public void onDisable() {
        save();
    }

    @Override
    public void load() {
        models.load();
        players.load();
        fighters.load();
    }

    @Override
    public void save() {
        fighters.save();
        models.save();
        players.save();
    }
}

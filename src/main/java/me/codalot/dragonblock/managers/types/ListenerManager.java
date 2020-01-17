package me.codalot.dragonblock.managers.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.listeners.types.DataListener;
import me.codalot.dragonblock.listeners.types.FighterListener;
import me.codalot.dragonblock.managers.Manager;

public class ListenerManager implements Manager {

    public ListenerManager() {
        DragonBlock plugin = DragonBlock.getInstance();

        new DataListener(plugin);
        new FighterListener(plugin);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}

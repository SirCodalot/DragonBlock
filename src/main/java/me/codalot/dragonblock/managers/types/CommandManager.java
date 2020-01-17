package me.codalot.dragonblock.managers.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.commands.CmdHandler;
import me.codalot.dragonblock.commands.CmdNode;
import me.codalot.dragonblock.commands.types.debug.DebugCmd;
import me.codalot.dragonblock.managers.Manager;

public class CommandManager implements Manager {

    private DragonBlock plugin;

    public CommandManager() {
        plugin = DragonBlock.getInstance();

        register(new DebugCmd(), "debug");
    }

    private void register(CmdNode node, String... names) {
        new CmdHandler(node, names).register(plugin);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}

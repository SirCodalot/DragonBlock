package me.codalot.dragonblock.commands.types.debug;

import me.codalot.dragonblock.commands.CmdNode;
import org.bukkit.entity.Player;

public class DebugCmd extends CmdNode {

    public DebugCmd() {
        super();

        subNodes.put("transform", new TransformCmd());
    }

    @Override
    public void execute(Player executor, String[] args) {

    }

}

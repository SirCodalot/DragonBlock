package me.codalot.dragonblock.commands.types.debug;

import me.codalot.dragonblock.commands.CmdNode;
import org.bukkit.entity.Player;

public class DebugCmd extends CmdNode {

    public DebugCmd() {
        super();

        subNodes.put("transform", new TransformCmd());
        subNodes.put("possess", new PossessCmd());
        subNodes.put("eject", new EjectCmd());
    }

    @Override
    public void execute(Player executor, String[] args) {

    }

}

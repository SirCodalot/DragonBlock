package me.codalot.dragonblock.commands.types.debug;

import me.codalot.dragonblock.commands.CmdNode;
import me.codalot.dragonblock.players.PlayerData;
import org.bukkit.entity.Player;

public class EjectCmd extends CmdNode {

    @Override
    public void execute(Player executor, String[] args) {
        PlayerData data = getData(executor);
        if (data == null)
            return;

        data.ejectFighter();
    }

}

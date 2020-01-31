package me.codalot.dragonblock.commands.types.debug;

import me.codalot.dragonblock.commands.CmdNode;
import me.codalot.dragonblock.game.fighters.FighterData;
import me.codalot.dragonblock.game.fighters.FighterPreset;
import me.codalot.dragonblock.players.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;

public class PossessCmd extends CmdNode {

    @Override
    public void execute(Player executor, String[] args) {
        PlayerData data = getData(executor);
        if (data == null)
            return;

        if (args.length != 1)
            return;

        FighterData fighter;
        try {
            if (args[0].equalsIgnoreCase("cac"))
                fighter = data.getData();
            else
                fighter = FighterPreset.valueOf(args[0].toUpperCase()).getData();
        } catch (Exception e) {
            executor.sendMessage("invalid preset");
            return;
        }

        data.possessFighter(fighter);
    }

    @Override
    public List<String> getCompletionOptions(String[] args, Player player) {
        List<String> options = super.getCompletionOptions(args, player);

        if (isPermitted(player)) {
            for (FighterPreset preset : FighterPreset.values())
                options.add(preset.toString().toLowerCase());
            options.add("cac");
        }

        return options;
    }
}

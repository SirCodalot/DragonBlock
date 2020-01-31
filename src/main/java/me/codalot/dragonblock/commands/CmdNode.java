package me.codalot.dragonblock.commands;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.players.PlayerData;
import me.codalot.dragonblock.utils.CollectionUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class CmdNode {

    @Getter protected Map<String, CmdNode> subNodes;

    public CmdNode() {
        subNodes = new HashMap<>();
    }

    public List<String> getCompletionOptions(String[] args, Player player) {
        List<String> options = new ArrayList<>();

        for (String key : subNodes.keySet())
            if (subNodes.get(key).isPermitted(player))
                options.add(key);

        return options;
    }

    public boolean isPermitted(Player executor) {
        return true;
    }

    public void unpermittedExecution(Player executor) {
        // TODO send no perm message
    }

    public void run(Player executor, String[] args) {
        if (!isPermitted(executor)) {
            unpermittedExecution(executor);
            return;
        }

        if (args.length != 0) {
            CmdNode subNode = subNodes.get(args[0].toLowerCase());

            if (subNode != null) {
                subNode.run(executor, CollectionUtils.removeFirst(args));
                return;
            }
        }

        execute(executor, args);
    }

    public abstract void execute(Player executor, String[] args);

    protected static Fighter getFighter(Player player) {
        return DragonBlock.getInstance().getFighters().getFighter(player);
    }

    protected static PlayerData getData(Player player) {
        return DragonBlock.getInstance().getPlayers().getData(player);
    }

}

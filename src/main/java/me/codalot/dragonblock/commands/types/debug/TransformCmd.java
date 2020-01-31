package me.codalot.dragonblock.commands.types.debug;

import me.codalot.dragonblock.commands.CmdNode;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.components.Form;
import org.bukkit.entity.Player;

import java.util.List;

public class TransformCmd extends CmdNode {

    @Override
    public void execute(Player executor, String[] args) {
        Fighter fighter = getFighter(executor);
        if (fighter == null)
            return;

        if (args.length != 1)
            return;

        Form form;
        try {
            form = Form.valueOf(args[0].toUpperCase());
        } catch (Exception e) {
            executor.sendMessage("invalid form");
            return;
        }

        fighter.transform(form);
        executor.sendMessage("transforming...");
    }

    @Override
    public List<String> getCompletionOptions(String[] args, Player player) {
        List<String> options = super.getCompletionOptions(args, player);

        if (isPermitted(player)) {
            for (Form form : Form.values())
                options.add(form.toString().toLowerCase());
        }

        return options;
    }
}

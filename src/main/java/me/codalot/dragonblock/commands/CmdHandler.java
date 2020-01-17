package me.codalot.dragonblock.commands;

import lombok.Getter;
import me.codalot.dragonblock.utils.CollectionUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public class CmdHandler implements CommandExecutor, TabCompleter {

    private CmdNode node;
    private String[] names;

    public CmdHandler(CmdNode node, String... names) {
        this.node = node;
        this.names = names;
    }

    public void register(JavaPlugin plugin) {
        List<String> aliases = new ArrayList<>();
        for (int i = 1; i < names.length; i++)
            aliases.add(names[i]);

        plugin.getCommand(names[0]).setExecutor(this);
        plugin.getCommand(names[0]).setTabCompleter(this);
        plugin.getCommand(names[0]).setAliases(aliases);
    }

    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player)
            node.run((Player) sender, args);

        return true;
    }

    @Override
    @SuppressWarnings("all")
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return new ArrayList<>();

        CmdNode currentNode = node;

        int count;
        for (count = 0; count < args.length - 1; count++) {
            CmdNode nextNode = currentNode.getSubNodes().get(args[count]);

            if (nextNode == null)
                break;

            currentNode = nextNode;
        }

        return CollectionUtils.getMatches(args[args.length - 1],
                currentNode.getCompletionOptions(CollectionUtils.removeFirst(count, args), (Player) sender));
    }

}

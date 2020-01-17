package me.codalot.dragonblock.managers.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.files.YamlFile;
import me.codalot.dragonblock.managers.Manager;
import me.codalot.dragonblock.players.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Manager {

    private final static File FOLDER = new File(DragonBlock.getInstance().getDataFolder(), "players");

    private Map<UUID, YamlFile> files;
    private Map<UUID, PlayerData> players;

    public PlayerManager() {
        files = new HashMap<>();
        players = new HashMap<>();
    }

    @Override
    public void load() {
        Bukkit.getOnlinePlayers().forEach(this::getData);
    }

    @Override
    public void save() {
        players.values().forEach(PlayerData::save);

        files.clear();
        players.clear();
    }

    public YamlFile getFile(UUID uuid) {
        if (files.containsKey(uuid))
            return files.get(uuid);

        YamlFile file = new YamlFile(DragonBlock.getInstance(), uuid.toString(), FOLDER);
        files.put(uuid, file);
        return file;
    }

    public PlayerData getData(UUID uuid) {
        if (players.containsKey(uuid))
            return players.get(uuid);

        PlayerData data = new PlayerData(getFile(uuid), uuid);
        players.put(uuid, data);
        return data;
    }

    public PlayerData getData(OfflinePlayer player) {
        return getData(player.getUniqueId());
    }

    public PlayerData getData(PlayerEvent event) {
        return getData(event.getPlayer());
    }

}

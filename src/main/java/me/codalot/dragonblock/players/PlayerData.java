package me.codalot.dragonblock.players;

import lombok.Getter;
import me.codalot.dragonblock.files.YamlFile;
import me.codalot.dragonblock.game.players.Fighter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerData implements ConfigurationSerializable {

    private YamlFile file;

    private UUID uuid;
    private String name;

    private PlayerConnection connection;

    private Fighter fighter;

    public PlayerData(YamlFile file, UUID uuid) {
        this.file = file;
        this.uuid = uuid;
        OfflinePlayer player = getOfflinePlayer();

        name = player.getName();

        connection = new PlayerConnection(this);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        if (fighter != null)
            serialized.put("fighter", fighter.serialize());

        return serialized;
    }

    public void save() {
        file.set(serialize());
        file.save();
    }

    public void onJoin() {
        connection.addToPipeline();
        fighter = new Fighter(uuid, file.getMap("fighter", new HashMap<>()));
    }

    public void onQuit() {
        save();

        connection.removeFromPipeline();

        if (fighter != null) {
            fighter.unload();
            fighter = null;
        }
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }
}
